package net.dv.tax.app.sales

import net.dv.tax.domain.sales.EmployeeIndustryEntity
import net.dv.tax.app.dto.sales.EmployeeIndustryListDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EmployeeIndustryService(private val employeeIndustryRepository: EmployeeIndustryRepository) {

    fun getList(hospitalId: String, year: String): EmployeeIndustryListDto {
        return EmployeeIndustryListDto(
            employeeIndustryRepository.dataList(hospitalId, year),
            employeeIndustryRepository.dataTotalList(hospitalId, year)
        )
    }

    fun getDetail(hospitalId: String, yearMonth: String, page: Pageable): Page<EmployeeIndustryEntity> {
        return employeeIndustryRepository.findAllByHospitalIdAndTreatmentYearMonth(
            hospitalId,
            yearMonth,
            page
        )
    }

}