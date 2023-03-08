package net.dv.tax.service.common

import mu.KotlinLogging
import net.dv.tax.domain.common.AccountingDataEntity
import net.dv.tax.dto.MenuCategoryCode
import net.dv.tax.dto.purchase.ExcelRequiredDto
import net.dv.tax.repository.common.AccountingDataRepository
import net.dv.tax.service.purchase.PurchaseCashReceiptService
import net.dv.tax.service.purchase.PurchaseCreditCardService
import net.dv.tax.service.purchase.PurchaseElecInvoiceService
import net.dv.tax.service.purchase.PurchasePassbookService
import net.dv.tax.utils.AwsS3Service
import net.dv.tax.utils.ExcelComponent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Component
class AccountingDataService(
    private val accountingDataRepository: AccountingDataRepository,
    private val awsS3Service: AwsS3Service,
    private val excelComponent: ExcelComponent,

    private val purchaseCreditCardService: PurchaseCreditCardService,
    private val purchaseCashReceiptService: PurchaseCashReceiptService,
    private val purchaseElecInvoiceService: PurchaseElecInvoiceService,
    private val purchasePassbookService: PurchasePassbookService,
) {

    private val log = KotlinLogging.logger {}

    @Throws
    @Transactional
    fun saveOriginData(
        hospitalId: String,
        writer: String,
        dataCategory: MenuCategoryCode,
        multipartFiles: List<MultipartFile>,
    ): ResponseEntity<HttpStatus> {
        multipartFiles.forEach {

            val accountingDataEntity = AccountingDataEntity()

            var tempMap = awsS3Service.upload(dataCategory.name, it)

            accountingDataEntity.also { data ->
                data.hospitalId = hospitalId
                data.dataCategory = tempMap["category"]!!
                data.uploadFileName = tempMap["fileName"]!!
                data.uploadFilePath = tempMap["filePath"]!!
                data.writer = writer
            }

            log.error { accountingDataEntity }

            accountingDataRepository.save(accountingDataEntity)
        }

        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun getOriginDataList(hospitalId: String, dataCategory: MenuCategoryCode): List<AccountingDataEntity> {
        return accountingDataRepository.findAllByHospitalIdAndDataCategoryAndIsDeleteFalse(
            hospitalId,
            dataCategory.name
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

    fun updateOriginData(id: Long, writer: String): ResponseEntity<HttpStatus> {


        accountingDataRepository.findById(id).get().also {

            val excelDto = ExcelRequiredDto(
                writer = it.writer!!,
                hospitalId = it.hospitalId!!,
                year = it.uploadFileName?.trimStart()?.substring(0, 4)!!,
                it.id!!
            )

            it.isApply = true
            it.writer = writer

            val rows = excelComponent.readXlsx(awsS3Service.getFileFromBucket(it.uploadFilePath!!))

            when (MenuCategoryCode.valueOf(it.dataCategory!!)) {
                MenuCategoryCode.CREDIT_CARD -> purchaseCreditCardService.excelToEntitySave(excelDto, rows)
                MenuCategoryCode.CASH_RECEIPT -> purchaseCashReceiptService.excelToEntitySave(excelDto, rows)
                MenuCategoryCode.ELEC_INVOICE -> purchaseElecInvoiceService.excelToEntitySave(excelDto, rows)
                MenuCategoryCode.PASSBOOK -> purchasePassbookService.excelToEntitySave(excelDto, rows)
                MenuCategoryCode.ELEC_TAX_INVOICE -> purchaseElecInvoiceService.excelToEntitySave(
                    excelDto
                        .also { dto ->
                            dto.isTax = true
                        }, rows
                )

                else -> null
            }

//            accountingDataRepository.save(it)
        }
        return ResponseEntity.ok(HttpStatus.OK)
    }


}