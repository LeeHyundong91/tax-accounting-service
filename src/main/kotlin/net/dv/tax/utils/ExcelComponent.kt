package net.dv.tax.utils

import com.amazonaws.AmazonServiceException
import jakarta.servlet.http.HttpServletResponse
import org.dhatim.fastexcel.Workbook
import org.dhatim.fastexcel.reader.ReadableWorkbook
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.OutputStream
import java.net.URLEncoder
import java.util.*
import java.util.stream.Collectors


@Component
class ExcelComponent {

    fun downloadExcel(response: HttpServletResponse, fileName: String, list: List<Map<String, Any>>) {
        response.contentType = "application/vnd.ms-excel"
        response.characterEncoding = "utf-8"
        val fileNameUtf8: String = URLEncoder.encode(fileName, "UTF-8")
        response.setHeader("Content-Disposition", "attachment; filename=$fileNameUtf8.xlsx")
        response.outputStream.use { os -> createExcel(os, list) }
    }

    /*TODO FileName 받아야됨*/
    @Throws(IOException::class)
    fun createExcel(os: OutputStream?, list: List<Map<String, Any>>) {
        val wb = Workbook(os, "ExcelApplication", "1.0")
        val ws = wb.newWorksheet("Sheet1")

        var idx = 0

        list.forEachIndexed { row, item ->
            if (row == 0) {
                item.keys.forEach { title ->
                    ws.value(0, idx, title)
                    idx++
                }
            }
            idx = 0
            item.values.forEach { content ->
                ws.value(row + 1, idx, content.toString())
                idx++
            }
        }

        ws.finish()

        wb.finish()
    }

    fun readExcel(multipartFile: MultipartFile): MutableList<Row> {
        try {
            var setValues: MutableList<Row>

            val inputStream = multipartFile.inputStream
            inputStream.use { fis ->
                val wb = ReadableWorkbook(fis)
                setValues = wb.firstSheet.openStream()
                    .parallel()
                    .collect(Collectors.toList())
            }

            return setValues

        } catch (e: AmazonServiceException) {
            throw Exception()
        }

    }


}