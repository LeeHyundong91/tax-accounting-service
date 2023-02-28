package net.dv.tax.service.common

import mu.KotlinLogging
import net.dv.tax.domain.common.AccountingDataEntity
import net.dv.tax.dto.MenuCategoryCode
import net.dv.tax.repository.common.AccountingDataRepository
import net.dv.tax.utils.AwsS3Service
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Component
class AccountingDataService(
    private val accountingDataRepository: AccountingDataRepository,
    private val awsS3Service: AwsS3Service,
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

    fun deleteOriginData(id: Long): ResponseEntity<HttpStatus> {
        accountingDataRepository.findById(id).get().also {
            it.isDelete = true

            accountingDataRepository.save(it)
        }
        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun updateOriginData(id: Long): ResponseEntity<HttpStatus> {
        accountingDataRepository.findById(id).get().also {
            it.isApply = true

            accountingDataRepository.save(it)
        }
        return ResponseEntity.ok(HttpStatus.OK)
    }


}