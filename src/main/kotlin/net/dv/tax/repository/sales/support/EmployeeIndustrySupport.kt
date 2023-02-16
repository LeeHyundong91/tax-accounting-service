package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.EmployeeIndustryDto

interface EmployeeIndustrySupport {
    fun groupingList(hospitalId: String, dataPeriod: String): List<EmployeeIndustryDto>
}