package net.dv.tax.utils

import jakarta.servlet.http.HttpServletResponse
import org.dhatim.fastexcel.reader.ReadableWorkbook
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.net.URLEncoder
import java.util.stream.Collectors


@Component
class ExcelComponent {

    fun downloadExcel(response: HttpServletResponse, fileName: String) : HttpServletResponse {
        response.contentType = "application/vnd.ms-excel"
        response.characterEncoding = "utf-8"
        val fileNameUtf8: String = URLEncoder.encode(fileName, "UTF-8")
        response.setHeader("Content-Disposition", "attachment; filename=$fileNameUtf8.xls")
        return response
    }

    fun readExcel(multipartFile: MultipartFile?): MutableList<Row> {
        try {
            var setValues: MutableList<Row>

            val inputStream = multipartFile?.inputStream
            inputStream.use { fis ->
                val wb = ReadableWorkbook(fis)
                setValues = wb.firstSheet.openStream()
                    .parallel()
                    .collect(Collectors.toList())
            }

            return setValues

        } catch (e: Exception) {
            throw Exception()
        }

    }


}