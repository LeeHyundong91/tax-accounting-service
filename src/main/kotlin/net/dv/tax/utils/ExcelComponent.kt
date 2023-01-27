package net.dv.tax.utils

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.dhatim.fastexcel.reader.ReadableWorkbook
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Component
import java.io.File
import java.net.URLEncoder
import java.util.stream.Collectors


@Component
class ExcelComponent {
    private val log = KotlinLogging.logger {}

    fun downloadExcel(response: HttpServletResponse, fileName: String) : HttpServletResponse {
        response.contentType = "application/vnd.ms-excel"
        response.characterEncoding = "utf-8"
        val fileNameUtf8: String = URLEncoder.encode(fileName, "UTF-8")
        response.setHeader("Content-Disposition", "attachment; filename=$fileNameUtf8.xls")
        return response
    }

    fun readExcel(multipartFile: File?): MutableList<Row> {
        try {
            var setValues: MutableList<Row>

            val inputStream = multipartFile?.inputStream()
            inputStream.use { fis ->
                val wb = ReadableWorkbook(fis)
                setValues = wb.firstSheet.openStream()
                    .parallel()
                    .collect(Collectors.toList())
            }
            log.error { setValues }


            return setValues

        } catch (e: Exception) {
            throw Exception()
        }

    }


}