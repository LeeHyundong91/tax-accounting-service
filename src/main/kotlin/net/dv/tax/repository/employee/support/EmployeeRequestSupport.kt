package net.dv.tax.repository.employee.support

import net.dv.tax.domain.employee.EmployeeRequestEntity

interface EmployeeRequestSupport {

    // 요청 리스트
    fun employeeRequestList(hospitalId: String, offset: Long, size: Long, type: String?, keyword: String?): List<EmployeeRequestEntity>

}