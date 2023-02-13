package net.dv.tax.service.common

import mu.KotlinLogging
import net.dv.tax.domain.common.AccountingDataEntity
import net.dv.tax.repository.common.AccountingDataRepository
import net.dv.tax.utils.AwsS3Service
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class AccountingDataService(
    private val accountingDataRepository: AccountingDataRepository,
    private val awsS3Service: AwsS3Service
) {

    private val log = KotlinLogging.logger {}

    fun saveOriginData(hospitalId: String, writer: String, dataCategory: String, multipartFile: MultipartFile) {
        val accountingDataEntity = AccountingDataEntity()

        var tempMap = awsS3Service.upload(dataCategory, multipartFile)

        accountingDataEntity.also {
            it.dataCategory = dataCategory
            it.hospitalId = hospitalId
            it.uploadFileName = tempMap["fileName"]!!
            it.uploadFilePath = tempMap["filePath"]!!
            it.writer = writer
        }

        log.error { accountingDataEntity }


        accountingDataRepository.save(accountingDataEntity)

    }


}