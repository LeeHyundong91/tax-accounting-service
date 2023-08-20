package net.dv.tax.infra.endpoint.statistics

import net.dv.tax.Application
import net.dv.tax.app.dashboard.SalesManagementService
import net.dv.tax.app.dto.app.MonthlyReportDto
import net.dv.tax.app.statistics.types.Criteria
import net.dv.tax.app.statistics.types.SalesStatistics
import net.dv.tax.app.statistics.StatisticsCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/${Application.VERSION}/statistics")
class StatisticsReportEndpoint(
    private val command: StatisticsCommand,
    private val salesManagementService: SalesManagementService,
) {

    @GetMapping("/sales/{hospitalId}/annual/{year}")
    fun annualSalesStatistics(@PathVariable("hospitalId") hospitalId: String,
                              @PathVariable("year") year: String): ResponseEntity<SalesStatistics> {
        val res = command.salesStatistics(hospitalId) {
            term = Criteria.Term.ANNUAL
            this.year = year
        }

        return ResponseEntity.ok(res)
    }

    @GetMapping("/sales/{hospitalId}/monthly/{yearMonth}")
    fun monthlySalesReport(@PathVariable hospitalId: String, @PathVariable yearMonth: String): MonthlyReportDto {
        return salesManagementService.getSalesManagementList(hospitalId, yearMonth)
    }
}