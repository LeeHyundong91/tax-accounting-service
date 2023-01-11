package net.dv.tax.controller

import jakarta.servlet.http.HttpServletResponse
import net.dv.tax.utils.ExcelWriterService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

@Controller
@RequestMapping("/download/excel")
class ExcelDownloadController(
    private val fastExcelService: ExcelWriterService,
) {
    /*TODO ENUM 필요 PathVariable 별로 파일이름 및 서비스 호출 분기가 필요 할듯 함*/
    @GetMapping("/{serviceType}")
    @Throws(UnsupportedEncodingException::class)
    fun downloadExcel(response: HttpServletResponse, @PathVariable serviceType: String) {
        response.contentType = "application/vnd.ms-excel"
        response.characterEncoding = "utf-8"
        /*TODO 이름 받게끔 수정 필요 */
        /*TODO ENUM Class 추가*/
        val fileNameUtf8: String = URLEncoder.encode("FAST_EXCEL", "UTF-8")
        response.setHeader("Content-Disposition", "attachment; filename=$fileNameUtf8.xlsx")

        response.outputStream.use { os -> fastExcelService.createFastExcel(os, serviceType) }
    }

}