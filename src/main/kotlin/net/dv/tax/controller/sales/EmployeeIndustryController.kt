package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.EmployeeIndustryEntity
import net.dv.tax.dto.sales.EmployeeIndustryListDto
import net.dv.tax.service.sales.EmployeeIndustryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/employee-industry")
class EmployeeIndustryController(private val employeeIndustryService: EmployeeIndustryService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun getEmployeeIndustryList(
        @PathVariable year: String,
        @PathVariable hospitalId: String,
    ): EmployeeIndustryListDto {
        return employeeIndustryService.getList(hospitalId, year)
    }

    @GetMapping("/{yearMonth}/{hospitalId}")
    fun getEmployeeIndustryDetail(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        @PageableDefault(size = 30) page: Pageable,
    ): Page<EmployeeIndustryEntity> {
        return employeeIndustryService.getDetail(hospitalId, yearMonth, page)
    }
}