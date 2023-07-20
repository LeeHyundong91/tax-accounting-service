package net.dv.tax.app.employee.support

import net.dv.tax.domain.employee.EmployeeAttachFileEntity
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.app.dto.employee.EmployeeQueryDto


interface EmployeeSupport {

    // 요청 리스트
    fun employeeList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): List<EmployeeEntity>

    fun employeeListCnt(hospitalId: String, employeeQueryDto: EmployeeQueryDto): Long

    fun employeeFileList( employeeId: Long): List<EmployeeAttachFileEntity>;

}