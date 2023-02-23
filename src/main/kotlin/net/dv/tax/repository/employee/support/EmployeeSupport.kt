package net.dv.tax.repository.employee.support

import net.dv.tax.domain.employee.EmployeeEntity

interface EmployeeSupport {
    fun workEmployeeRequestList(hospitalId: String, offset: Long, size: Long, type: String?, keyword: String?): List<EmployeeEntity>
}