package net.dv.tax.controller.app

import mu.KotlinLogging
import net.dv.tax.dto.app.MonthlyReportDto
import net.dv.tax.service.app.SalesManagementService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth

@RestController
@RequestMapping("/v1/dashboard/report")
class DashboardController(private val salesManagementService: SalesManagementService) {
    private val log = KotlinLogging.logger {}


    /*Auth 붙으면 Path 지우고 Auth 에서 꺼내와야함 */
    @GetMapping("/monthly/{yearMonth}/{hospitalId}")
    fun monthlySalesReport(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: YearMonth,
    ): MonthlyReportDto {

        return salesManagementService.getSalesManagementList(hospitalId, yearMonth)

    }

}