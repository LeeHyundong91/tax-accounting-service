package net.dv.tax.utils

import jakarta.servlet.http.HttpServletResponse
import net.dv.tax.repository.VaccineSalesRepository
import org.dhatim.fastexcel.Workbook
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.OutputStream
import java.net.URLEncoder
import java.util.*


@Component
class ExcelWriterService(
    private val vaccineSalesRepository: VaccineSalesRepository
) {

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

}