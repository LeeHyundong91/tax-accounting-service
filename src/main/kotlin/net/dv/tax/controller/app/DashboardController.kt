package net.dv.tax.controller.app

import mu.KotlinLogging
import net.dv.tax.dto.app.ConsultingReportDto
import net.dv.tax.dto.app.MonthlyReportDto
import net.dv.tax.service.app.ConsultingReportService
import net.dv.tax.service.app.SalesManagementService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/dashboard/report")
class DashboardController(
    private val salesManagementService: SalesManagementService,
    private val consultingReportService: ConsultingReportService,
) {
    private val log = KotlinLogging.logger {}


    /*Auth 붙으면 Path 지우고 Auth 에서 꺼내와야함 */
    @GetMapping("/monthly/{yearMonth}/{hospitalId}")
    fun monthlySalesReport(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
    ): MonthlyReportDto {
        return salesManagementService.getSalesManagementList(hospitalId, yearMonth)
    }


    @GetMapping("/consulting/{yearMonth}/{hospitalId}")
    fun mainConsulting(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        ): ConsultingReportDto {
        return consultingReportService.mainReport(hospitalId, yearMonth)
    }


}