package net.dv.tax.utils

import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicSessionCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.amazonaws.services.securitytoken.AWSSecurityTokenService
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest
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
        const val BUCKET_PATH = "tax-accounting/"
    }

    val bucket: String = "dr.village.data"
    val tempKey: String = "origin/2023/01/25/credit-card_17:27_90e636c7d68247559ffe0b6dfd586533.xlsx"
    val region: String = "ap-northeast-2"
    val roleARN: String = "arn:aws:iam::526459572421:role/role-s3-access"

    private val stsClient: AWSSecurityTokenService
        get() = AWSSecurityTokenServiceClientBuilder.standard()
            .withCredentials(ProfileCredentialsProvider())
            .withRegion(region)
            .build()

    private fun createSession(name: String): AWSCredentials = AssumeRoleRequest()
        .withRoleArn(roleARN)
        .withRoleSessionName(name)
        .let { stsClient.assumeRole(it) }
        .credentials
        .run {
            BasicSessionCredentials(
                accessKeyId,
                secretAccessKey,
                sessionToken
            )
        }

    private fun s3Client(credentials: AWSCredentials): AmazonS3 = AmazonS3ClientBuilder
        .standard()
        .withCredentials(AWSStaticCredentialsProvider(credentials))
        .withRegion(region)
        .build()

    fun uploadOnS3(findName: String, file: File) {
        val client = s3Client(createSession("tax-accounting-service"))
        val transferManager = TransferManagerBuilder.standard()
            .withS3Client(client)
            .build()

        try {
            val putObjectRequest = PutObjectRequest(bucket, findName, file)
            val upload = transferManager.upload(putObjectRequest)
            upload.waitForCompletion()
        } catch (e: Exception) {
            log.error("Error uploading file to S3: ${e.message}")
            throw e
        } finally {
            transferManager.shutdownNow(false)
        }
    }

    fun getFileFromBucket(filePath: String): File {
        val transferManager = TransferManagerBuilder.standard().build()
        val file = File.createTempFile("s3-", null)

        try {
            transferManager.download(GetObjectRequest(bucket, filePath), file).waitForCompletion()
        } catch (e: Exception) {
            file.delete()
            throw e
        } finally {
            transferManager.shutdownNow(false)
        }

        return file
    }

    @Throws(Exception::class)
    fun upload(reportType: String, multipartFile: MultipartFile): Map<String, String> {
        val pattern = dateString(LocalDate.now())
        val timeString = timeString()
        val ext = multipartFile.originalFilename!!.substringAfterLast(".")
        val saveFileName = UUID.randomUUID().toString().replace("-", "")
        val originFileName = multipartFile.originalFilename!!
        val underBar = "_"
        val fullPath = "$BUCKET_PATH$pattern/$reportType$underBar$timeString$underBar$saveFileName.$ext"
        val file = File.createTempFile("upload-", ".$ext")

        try {
            multipartFile.transferTo(file)
            uploadOnS3(fullPath, file)

            val nameMap = mapOf(
                "filePath" to fullPath,
                "fileName" to originFileName,
                "category" to reportType
            )

            log.info(fullPath)
            return nameMap
        } catch (e: AmazonServiceException) {
            file.delete()
            throw Exception()
        } finally {
            file.delete()
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