package net.dv.tax.utils

import net.dv.tax.repository.VaccineSalesRepository
import org.dhatim.fastexcel.Position
import org.dhatim.fastexcel.Workbook
import org.dhatim.fastexcel.Worksheet
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.OutputStream
import java.util.*


@Component
class ExcelWriterComponent(
    private val vaccineSalesRepository: VaccineSalesRepository
) {

    /*TODO FileName 받아야됨*/
    @Throws(IOException::class)
    fun createFastExcel(os: OutputStream?) {
        val wb = Workbook(os, "ExcelApplication", "1.0")
        val ws = wb.newWorksheet("fucking test")

        createWorksheet(ws, getListForFastExcel())

        ws.footer("test",Position.CENTER)

        wb.finish()
    }

    @Throws(IOException::class)
    private fun createWorksheet(ws: Worksheet, list: List<Map<String, Any>>? ) {
        var idx = 0

        list?.forEachIndexed { row, item ->
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
    }


    /*TODO 해당 Method 여기저기서 쓸꺼임.*/
    private fun getListForFastExcel(): List<Map<String, Any>>? {
        val list: MutableList<Map<String, Any>> = LinkedList()

        vaccineSalesRepository.findAllByHospitalIdAndYearOrderByMonthAsc(1, "2022")?.forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["기간"] = it.year.toString() + " ." + it.month.toString()
            tempMap["지급완료 건수"] = it.payCount!!
            tempMap["지급금액"] = it.payAmount!!
            tempMap["작성자"] = it.writer
            tempMap["작성일"] = it.createdAt
            list.add(tempMap)
        }
        return list
    }
}