package net.dv.tax.repository.employee.support

import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.domain.employee.EmployeeSalaryMngEntity
import net.dv.tax.dto.employee.EmployeeQueryDto

interface EmployeeSalaryMngSupport {

    fun getSalaryMngList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): List<EmployeeSalaryMngEntity>

    fun getSalaryMngListCnt(hospitalId: String, employeeQueryDto: EmployeeQueryDto): Long

    fun getSalaryMngDeleteList( hospitalId: String, paymentAt: String): List<EmployeeSalaryMngEntity>
}