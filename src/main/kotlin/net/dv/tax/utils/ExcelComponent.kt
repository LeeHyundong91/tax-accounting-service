package net.dv.tax.utils

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.dhatim.fastexcel.Workbook
import org.dhatim.fastexcel.reader.ReadableWorkbook
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Component
import java.io.*
import java.net.URLEncoder
import java.util.*
import java.util.stream.Collectors


@Component
class ExcelComponent {
    private val log = KotlinLogging.logger {}

    fun downloadExcel(response: HttpServletResponse, fileName: String): HttpServletResponse {
        response.contentType = "application/vnd.ms-excel"
        response.characterEncoding = "utf-8"
        val fileNameUtf8: String = URLEncoder.encode(fileName, "UTF-8")
        response.setHeader("Content-Disposition", "attachment; filename=$fileNameUtf8.xls")
        return response
    }

    /*TODO FileName 받아야됨*/
    @Throws(IOException::class)
    fun createXlsx(os: OutputStream?, list: List<Map<String, Any>>) {
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

    fun readXlsx(file: File): MutableList<Row> {

        try {
            var setValues: MutableList<Row>

            val inputStream = file.inputStream()
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