package net.dv.tax.infra.endpoint.purchase

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.Application
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.app.purchase.PurchaseExcelDownloadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/${Application.VERSION}/purchase/download")
class PurchaseExcelDownloadController(private val purchaseExcelDownloadService: PurchaseExcelDownloadService) {

    private val log = KotlinLogging.logger {}

    @GetMapping("/{categoryName}/{hospitalId}")
    fun excelDownload(
        @PathVariable categoryName: String,
        @PathVariable hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        response: HttpServletResponse,
    ) {

        log.info { categoryName }
        purchaseExcelDownloadService.makeExcel(purchaseQueryDto, hospitalId, categoryName, response)
    }


}