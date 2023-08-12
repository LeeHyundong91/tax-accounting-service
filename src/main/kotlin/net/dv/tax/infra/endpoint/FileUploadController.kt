package net.dv.tax.infra.endpoint

import mu.KotlinLogging
import net.dv.tax.app.dto.ResponseFileUploadDto
import net.dv.tax.app.common.AccountingDataService
import net.dv.tax.app.dto.ResponseEmployeeFileUploadDto
import net.dv.tax.utils.AwsS3Service
import org.hibernate.boot.model.naming.IllegalIdentifierException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/v1/upload")
class FileUploadController(
    private val accountingDataService: AccountingDataService,
    private val awsS3Service: AwsS3Service
) {
    private val log = KotlinLogging.logger {}

    @PostMapping("/employee/file")
    fun employeeFileUpload(
        files: List<MultipartFile>,
    ): ResponseEntity<List<ResponseEmployeeFileUploadDto>> {

        val reportType = "employeeUpload"
        var fileUrlList = mutableListOf<ResponseEmployeeFileUploadDto>()
        files.forEach {file ->
            //파일을 s3에 업로드 한다.
            val returnMap = awsS3Service.upload(reportType, file)
            val filePath = returnMap["filePath"]?: throw IllegalIdentifierException("filepath is empty")

            fileUrlList.add(ResponseEmployeeFileUploadDto(
                fileName = returnMap["fileName"].toString(),
                filePath = returnMap["filePath"].toString()
            ))
        }

        return ResponseEntity.ok(fileUrlList)
    }

    //파일 업로드
    // TODO 수정이 필요
    @PostMapping("/tax/file/{hospitalId}")
    fun taxFileUpload(
        files: List<MultipartFile>,
        @PathVariable hospitalId: String,
    ): ResponseEntity<ResponseFileUploadDto> {

        val writer = "tester"
        val dataCategory = "" // TODO data category 값이 정의가 안됨

        return accountingDataService.saveOriginData(hospitalId, writer, dataCategory, files)
    }

}