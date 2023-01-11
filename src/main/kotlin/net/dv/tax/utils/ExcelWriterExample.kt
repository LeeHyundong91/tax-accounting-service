package net.dv.tax.utils

import org.dhatim.fastexcel.Workbook
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.function.Consumer

class ExcelWriterExample {

    @Throws(IOException::class)
    fun writeWorkbook(consumer: Consumer<Workbook?>) {
        val os = ByteArrayOutputStream()
        val wb = Workbook(os, "Test", "1.0")
        consumer.accept(wb)
        wb.finish()
        os.writeTo(FileOutputStream(File("excel-write-sample.xlsx")))
    }

    fun parallelStreams() {
        writeWorkbook { wb ->
            val ws = wb!!.newWorksheet("Sheet 1")
            getData().withIndex().map {
                ws.value(it.index, 0, it.value.firstName)
                ws.value(it.index, 1, it.value.secondName)
                ws.value(it.index, 2, it.value.address)
                ws.value(it.index, 3, it.value.message)
            }
        }
    }

    private fun getData(): List<WriteCellValue> {
        val writeCellValues: MutableList<WriteCellValue> = mutableListOf()
        writeCellValues.add(WriteCellValue("1", "2", "a1", "h1"))
        writeCellValues.add(WriteCellValue("2", "2", "a2", "h2"))
        return writeCellValues
    }

    data class WriteCellValue(var firstName: String, var secondName: String, var address: String, var message: String)
}
