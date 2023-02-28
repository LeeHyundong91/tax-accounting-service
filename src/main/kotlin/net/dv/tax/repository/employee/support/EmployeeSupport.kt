package net.dv.tax.repository.employee.support

import net.dv.tax.domain.employee.EmployeeAttachFileEntity
import net.dv.tax.domain.employee.EmployeeEntity


interface EmployeeSupport {

    // 요청 리스트
    fun employeeList(hospitalId: String, offset: Long, size: Long, type: String?, keyword: String?): List<EmployeeEntity>

    fun employeeFileList( employeeId: Long): List<EmployeeAttachFileEntity>;

}