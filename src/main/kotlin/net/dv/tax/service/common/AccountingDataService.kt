package net.dv.tax.service.common

import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import net.dv.tax.domain.common.AccountingDataEntity
import net.dv.tax.dto.MenuCategoryCode
import net.dv.tax.dto.QueueDto
import net.dv.tax.dto.ResponseFileUploadDto
import net.dv.tax.dto.purchase.ExcelRequiredDto
import net.dv.tax.repository.common.AccountingDataRepository
import net.dv.tax.repository.purchase.PurchaseCreditCardRepository
import net.dv.tax.service.purchase.PurchaseCashReceiptService
import net.dv.tax.service.purchase.PurchaseCreditCardService
import net.dv.tax.service.purchase.PurchaseElecInvoiceService
import net.dv.tax.service.purchase.PurchasePassbookService
import net.dv.tax.utils.AwsS3Service
import net.dv.tax.utils.ExcelComponent
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.text.Normalizer


@Component
class AccountingDataService(
    private val accountingDataRepository: AccountingDataRepository,
    private val awsS3Service: AwsS3Service,
    private val excelComponent: ExcelComponent,


    private val purchaseCreditCardService: PurchaseCreditCardService,
    private val purchaseCreditCardRepository: PurchaseCreditCardRepository,
    private val purchaseCashReceiptService: PurchaseCashReceiptService,
    private val purchaseElecInvoiceService: PurchaseElecInvoiceService,
    private val purchasePassbookService: PurchasePassbookService,
) {

    private val log = KotlinLogging.logger {}


    /*PROTO TYPE*/
    @RabbitListener(queues = ["tax.excel.queue"])
    fun getMessage(@Payload result: QueueDto) {
        log.error { "upload before !!" }
        purchaseCreditCardRepository.saveAll(result.creditCard!!)
        log.error { "upload after !!" }
    }

    @Transactional
    fun saveOriginData(
        hospitalId: String,
        writer: String,
        multipartFiles: List<MultipartFile>,
    ): ResponseEntity<ResponseFileUploadDto> {


        val responseFiles = mutableListOf<ResponseFileUploadDto>()

        for (file in multipartFiles) {
            try {
                /*Mac Korean break situation defence*/
                val partOfName = Normalizer.normalize(file.originalFilename, Normalizer.Form.NFC)!!.split("_", ".")

                log.info { partOfName }

                if (partOfName.size > 4) {
                    responseFiles.add(ResponseFileUploadDto("잘못된 형태의 파일명 입니다.", file.originalFilename))
                    continue
                }

                val dataPeriod = partOfName[0].trim()

                val dataCategoryKor = partOfName[1].trim()

                val dataCategory = MenuCategoryCode.values().find { it.purchaseFileName == dataCategoryKor }?.name
                    ?: throw RuntimeException("Invalid data category: $dataCategoryKor")

                val companyName = partOfName[2]

                val tempMap = awsS3Service.upload(dataCategory, file)
                val accountingDataEntity = AccountingDataEntity(
                    hospitalId = hospitalId,
                    dataCategory = tempMap["category"]!!,
                    dataCategoryKor = dataCategoryKor,
                    companyName = companyName,
                    dataPeriod = dataPeriod,
                    uploadFileName = tempMap["fileName"]!!,
                    uploadFilePath = tempMap["filePath"]!!,
                    writer = writer,
                    dataType = "세무",
                )

                log.error { accountingDataEntity }

                accountingDataRepository.save(accountingDataEntity).apply {
                    updateOriginData(id!!)
                }

            } catch (e: Exception) {
                responseFiles.add(ResponseFileUploadDto("파일 처리 중 오류가 발생했습니다.", file.originalFilename))
                log.error { e.message }
            }
        }

        return if (responseFiles.isEmpty()) {
            ResponseEntity.ok(ResponseFileUploadDto("업로드 성공"))
        } else {
            ResponseEntity.ok(ResponseFileUploadDto("일부 파일 업로드 실패", responseFiles))
        }
    }


    fun getOriginDataList(hospitalId: String, dataCategory: String): List<AccountingDataEntity> {
        return accountingDataRepository.findAllByHospitalIdAndDataCategoryAndIsDeleteFalse(
            hospitalId,
            dataCategory
        )
    }

    fun deleteOriginData(id: Long, writer: String): ResponseEntity<HttpStatus> {
        accountingDataRepository.findById(id).get().also {
            it.isDelete = true
            it.writer = writer

            accountingDataRepository.save(it)
        }
        return ResponseEntity.ok(HttpStatus.OK)
    }

    @Transactional(rollbackFor = [Exception::class], timeout = -1)
    fun updateOriginData(id: Long) {
        val data = accountingDataRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Accounting data with ID $id not found") }

        val excelDto = ExcelRequiredDto(
            writer = data!!.writer ?: error("Accounting data has no writer"),
            hospitalId = data.hospitalId ?: error("Accounting data has no hospital ID"),
            year = data.uploadFileName?.substring(0, 4) ?: error("Accounting data has no upload file name"),
            fileDataId = id
        )


        val rows =
            awsS3Service.getFileFromBucket(data.uploadFilePath ?: error("Accounting data has no upload file path"))
                .let { excelComponent.readXlsx(it) }

        when (MenuCategoryCode.valueOf(data.dataCategory!!)) {
            MenuCategoryCode.CREDIT_CARD -> purchaseCreditCardService.excelToEntitySave(excelDto, rows)
            MenuCategoryCode.CASH_RECEIPT -> purchaseCashReceiptService.excelToEntitySave(excelDto, rows)
            MenuCategoryCode.ELEC_INVOICE -> purchaseElecInvoiceService.excelToEntitySave(excelDto, rows)
            MenuCategoryCode.PASSBOOK -> purchasePassbookService.excelToEntitySave(excelDto, rows)
            MenuCategoryCode.ELEC_TAX_INVOICE -> purchaseElecInvoiceService.excelToEntitySave(
                excelDto.copy(isTax = true),
                rows
            )

            else -> {}
        }
        data.isApply = true
        accountingDataRepository.save(data)
    }


}
