package net.dv.tax.service.employee

import net.dv.tax.dto.employee.EmployeeRequestDto
import net.dv.tax.enum.employee.*
import net.dv.tax.repository.employee.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(private val workEmployeeRepository: EmployeeRepository) {

    fun getEmployeeRequestList(hospitalId: String, offset: Long, size:Long, searchType: String?, keyword: String?): List<EmployeeRequestDto> {

        val realOffset = offset * size;

        return workEmployeeRepository.workEmployeeRequestList(hospitalId, realOffset, size, searchType, keyword).map{
            EmployeeRequestDto(
                id = it.id,
                residentNumber = it.residentNumber,
                name =  it.name,
                employment =  getEmployeementName(it.employmentType),
                annualType = it.annualType,
                annualIncome = it.annualIncome,
                position = getPositionName(it.position),
                joinAt = it.joinAt,
                email = it.email,
                jobClass = getJobClassName(it.jobClass),
                reason = it.reason,
                createdAt = it.createdAt,
                requestStateCode = it.requestState,
                requestStateName = getRequestStateName(it.requestState)
            )
        }
    }

}