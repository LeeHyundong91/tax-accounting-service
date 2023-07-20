package net.dv.tax.infra.endpoint

import mu.KotlinLogging
import net.dv.tax.app.dto.ResponseFileUploadDto
import net.dv.tax.app.common.AccountingDataService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/v1/upload")
class FileUploadController(
    private val accountingDataService: AccountingDataService,
) {
    private val log = KotlinLogging.logger {}

    //파일 업로드
    @PostMapping("/tax/file/{hospitalId}")
    fun taxFileUpload(
        files: List<MultipartFile>,
        @PathVariable hospitalId: String,
    ): ResponseEntity<ResponseFileUploadDto> {

        val writer = "tester"

        return accountingDataService.saveOriginData(hospitalId, writer, files)
    }

}