package net.dv.tax.utils

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.amazonaws.services.s3.transfer.Upload
import com.amazonaws.util.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class AwsS3Service(private val resourceLoader: ResourceLoader) {


    /*TODO 버킷 추후 yml로 */
    companion object {
        private val log = LoggerFactory.getLogger(AwsS3Service::class.java)
        const val BUCKET_PATH = "origin/"
    }

//    val bucket: String = "dr.village.tax.data"
//    val tempKey: String = "origin/2023/01/25/credit-card_17:27_90e636c7d68247559ffe0b6dfd586533.xlsx"
    val bucket: String = "sugarbricks.static"
    val tempKey: String = "www/html/index.html"

    fun uploadOnS3(findName: String, file: File) {
        val transferManager: TransferManager = TransferManagerBuilder.standard().build()

        val request = PutObjectRequest(bucket, findName, file)
        // 업로드 시도
        try {
            val upload: Upload = transferManager.upload(request)
            upload.waitForCompletion()
        } catch (e: Exception) {
            log.error("e: ${e.printStackTrace()}")
            throw e
        }
    }


    fun readObj() {

/*https://s3.ap-northeast-2.amazonaws.com/sugarbricks.static/www/index.html*/

//        var file: File? = null
//        file = File("/tmp/$tempKey")

//        val s3Client: AmazonS3 = AmazonS3ClientBuilder
//            .standard()
//            .build()


        var leader  = resourceLoader.getResource("s3://$bucket/$tempKey")

        var inputStream: InputStream = leader.inputStream

        log.error(inputStream.readBytes().toString())


//        val o: S3Object = s3Client.getObject(bucket, tempKey)
//        val s3is: S3ObjectInputStream = o.objectContent
//        val fos = FileOutputStream(File(tempKey))
//        val readNuf = ByteArray(1024)
//        var readLen = 0
//        while (s3is.read(readNuf).also { readLen = it } > 0) {
//            fos.write(readNuf, 0, readLen)
//        }
//        s3is.close()
//        fos.close()

    }

    fun getFileFromBucket(): File? {


//        val objectRequest: GetObjectRequest = GetObjectRequest.builder()
//            .key(path)
//            .bucket(bucketName)
//            .build()

        val s3Client: AmazonS3 = AmazonS3ClientBuilder
            .standard()
            .build()

        log.info(
            "test :" + s3Client.getUrl(
                bucket,
                "origin/2023/01/25/credit-card_17:27_90e636c7d68247559ffe0b6dfd586533.xlsx"
            )
        )


        var fileName = "origin/2023/01/25/credit-card_17:27_90e636c7d68247559ffe0b6dfd586533.xlsx"
        val getObjectRequest = GetObjectRequest(bucket, fileName)
        val s3Object: S3Object = s3Client.getObject(getObjectRequest)
        val s3File = File(fileName)


        try {
            FileOutputStream(s3File).use { fos ->  //throws Exception
                log.error(s3Object.objectContent.readBytes().size.toString())
                IOUtils.copy(s3Object.objectContent, fos)
            }
        } catch (e: IOException) {
            log.debug("IOException Occurred while fetching file {}", fileName)
        }
        return s3File
    }

    @Throws(Exception::class)
    fun upload(reportType: String, multipartFile: MultipartFile): Map<String, String> {

        var file: File? = null

        val nameMap = mutableMapOf<String, String>()

        try {
            val pattern = dateString(LocalDate.now())
            val timeString = timeString()
            val ext: String? = multipartFile.originalFilename?.substringAfterLast(".")
            val underBar = "_"
            val saveFileName: String = UUID.randomUUID().toString().replace("-".toRegex(), "")
            val originFileName = multipartFile.originalFilename
            file = File("/tmp/$saveFileName.$ext")


            multipartFile.transferTo(file)
            val fullPath = "$BUCKET_PATH$pattern/$reportType$underBar$timeString$underBar$saveFileName.$ext"

            uploadOnS3(fullPath, file)
            file.delete()

            nameMap["filePath"] = fullPath
            nameMap["fileName"] = originFileName.toString()

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