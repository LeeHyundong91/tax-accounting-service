package net.dv.tax.controller.sales

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.service.sales.ExcelDownloadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/download")
class SalesExcelDownloadController(private val excelDownloadService: ExcelDownloadService) {

    private val log = KotlinLogging.logger {}

    @GetMapping("/{categoryName}/{year}/{hospitalId}")
    fun excelDownload(
        @PathVariable categoryName: String,
        @PathVariable year: String,
        @PathVariable hospitalId: String,
        response: HttpServletResponse,
    ) {

        log.info { categoryName }
        excelDownloadService.makeExcel(year, hospitalId, categoryName, response)
    }


}