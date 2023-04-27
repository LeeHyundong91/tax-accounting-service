package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.HealthCareEntity
import net.dv.tax.dto.sales.HealthCareListDto
import net.dv.tax.service.sales.HealthCareService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/health-care")
class HealthCareController(private val healthCareService: HealthCareService) {

    @GetMapping("list/{year}/{hospitalId}")
    fun getList(@PathVariable hospitalId: String, @PathVariable year: String): HealthCareListDto {
        return healthCareService.getList(hospitalId, year)
    }

    @GetMapping("/{yearMonth}/{hospitalId}")
    fun getDetailList(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        @PageableDefault(size = 30) page: Pageable,
    ): Page<HealthCareEntity> {
        return healthCareService.getDetailList(hospitalId, yearMonth, page)

    }


}