package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.EmployeeIndustryDto

interface EmployeeIndustrySupport {
    fun dataList(hospitalId: String, yearMonth: String): List<EmployeeIndustryDto>

    fun dataTotalList(hospitalId: String, yearMonth: String): EmployeeIndustryDto
}