package net.dv.tax.repository.employee.support

import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.dto.employee.EmployeeQueryDto

interface EmployeeRequestSupport {

    // 요청 리스트
    fun employeeRequestList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): List<EmployeeRequestEntity>

    fun employeeRequestListCnt(hospitalId: String, employeeQueryDto: EmployeeQueryDto): Long
}