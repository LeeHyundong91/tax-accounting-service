package net.dv.tax.utils

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
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

    fun readXlsx(inputFile: File): MutableList<Row> {

        var innerFile = inputFile

        if (inputFile.extension == "xls") {
            innerFile = convertXlsToXlsx(inputFile)
        }

        try {
            var setValues: MutableList<Row>

            val inputStream = innerFile.inputStream()
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

    fun convertXlsToXlsx(xlsFile: File): File {
        val xlsxFile = File("${xlsFile.parent}/${xlsFile.nameWithoutExtension}.xlsx")

        // Read XLS file and write to XLSX file
        val workbookXls = HSSFWorkbook(FileInputStream(xlsFile))
        val workbookXlsx = XSSFWorkbook()

        val sheetXls = workbookXls.getSheetAt(0)
        val sheetXlsx = workbookXlsx.createSheet("Sheet1")

        for (row in sheetXls) {
            val newRow = sheetXlsx.createRow(row.rowNum)
            for (cell in row) {
                val newCell = newRow.createCell(cell.columnIndex)
                when (cell.cellType) {
                    CellType.STRING -> newCell.setCellValue(cell.stringCellValue)
                    CellType.NUMERIC -> newCell.setCellValue(cell.numericCellValue)
                    CellType.BOOLEAN -> newCell.setCellValue(cell.booleanCellValue)
                    else -> {}
                }
            }
        }

        workbookXls.close()

        // Write XLSX file
        val fos = FileOutputStream(xlsxFile)
        workbookXlsx.write(fos)
        fos.close()
        workbookXlsx.close()

        return xlsxFile
    }


    fun readXls(file: File) {

        val oldWorkBook: org.apache.poi.ss.usermodel.Workbook = WorkbookFactory.create(file)

//        val ins: InputStream = FileInputStream(file)

//        val oldWorkbook: org.apache.poi.ss.usermodel.Workbook = HSSFWorkbook(file.inputStream())

        /*
                var excellist: List<PurchaseCreditCardExcelDto> = LinkedList<PurchaseCreditCardExcelDto>()

                val inputStream = file.inputStream()
                inputStream.use { fis ->
                    val wb = oldWorkBook
                    excellist = Javaxcel.newInstance()
                        .reader(oldWorkBook, PurchaseCreditCardExcelDto::class.java)
                        .read()
                }
                log.error { excellist }
        */


    }


}