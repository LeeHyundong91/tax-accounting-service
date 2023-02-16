package net.dv.tax.service.sales

import net.dv.tax.dto.sales.EmployeeIndustryDto
import net.dv.tax.repository.sales.EmployeeIndustryRepository
import org.springframework.stereotype.Service

@Service
class EmployeeIndustryService(private val employeeIndustryRepository: EmployeeIndustryRepository) {

    fun getListData(hospitalId: String, year: String): List<EmployeeIndustryDto> {
        return employeeIndustryRepository.groupingList(hospitalId, year)
    }

}