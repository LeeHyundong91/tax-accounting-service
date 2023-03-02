package net.dv.tax.utils

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.amazonaws.services.s3.transfer.Upload
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class AwsS3Service(private val excelComponent: ExcelComponent) {


    /*TODO 버킷 추후 yml로 */
    companion object {
        private val log = LoggerFactory.getLogger(AwsS3Service::class.java)
        const val BUCKET_PATH = "origin/"
    }

    val bucket: String = "dr.village.tax.data"
    val tempKey: String = "origin/2023/01/25/credit-card_17:27_90e636c7d68247559ffe0b6dfd586533.xlsx"

    fun uploadOnS3(findName: String, file: File) {
        val transferManager: TransferManager = TransferManagerBuilder.standard().build()

        // 업로드 시도
        try {
            val upload: Upload = transferManager.upload(PutObjectRequest(bucket, findName, file))
            upload.waitForCompletion()
        } catch (e: Exception) {
            log.error("e: ${e.printStackTrace()}")
            throw e
        }
    }


    fun getFileFromBucket(filePath: String): File {
        val transferManager: TransferManager = TransferManagerBuilder.standard().build()
        log.error("filePath : $filePath")
        val file = File("/tmp/$filePath")

        transferManager.download(GetObjectRequest(bucket, filePath), file)

        return file
    }

    @Throws(Exception::class)
    fun upload(reportType: String, multipartFile: MultipartFile): Map<String, String> {

        var file: File? = null

        val nameMap = mutableMapOf<String, String>()

        try {
            val pattern = dateString(LocalDate.now())
            val timeString = timeString()
            val ext: String = multipartFile.originalFilename!!.substringAfterLast(".")
            val underBar = "_"
            val saveFileName: String = UUID.randomUUID().toString().replace("-".toRegex(), "")
            val originFileName: String = multipartFile.originalFilename!!
            file = File("/tmp/$saveFileName.$ext")


            multipartFile.transferTo(file)
            val fullPath = "$BUCKET_PATH$pattern/$reportType$underBar$timeString$underBar$saveFileName.$ext"

            uploadOnS3(fullPath, file)
            file.delete()

            log.info(fullPath)

            nameMap["filePath"] = fullPath
            nameMap["fileName"] = originFileName
            nameMap["category"] = reportType

            return nameMap
        } catch (e: AmazonServiceException) {
            file!!.delete()
            throw Exception()
        }
    }


    fun dateString(localDate: LocalDate): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        return localDate.format(formatter)
    }

    fun timeString(): String {
        val time: LocalTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return time.format(formatter).toString()
    }


}