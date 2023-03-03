package net.dv.tax.controller

import mu.KotlinLogging
import net.dv.tax.utils.AwsS3Service
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/v1/upload")
class FileUploadController(
    private val awsS3Service: AwsS3Service
) {
    private val log = KotlinLogging.logger {}

    //파일 업로드
    @PostMapping("{reportType}/file")
    fun fileUpload(@PathVariable reportType: String, @RequestPart files: List<MultipartFile>): ResponseEntity<MutableList<Map<String, String>>> {

        val fileList: MutableList<Map<String, String>> = LinkedList();

        files.map {
            val fileMap = awsS3Service.upload(reportType, it);
            fileList.add(fileMap);
        }

        return ResponseEntity.ok(fileList)
    }
}