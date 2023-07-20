package net.dv.tax.app.employee.support

import net.dv.tax.domain.employee.EmployeeSalaryMngEntity
import net.dv.tax.app.dto.employee.EmployeeQueryDto

interface EmployeeSalaryMngSupport {

    fun getSalaryMngList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): List<EmployeeSalaryMngEntity>

    fun getSalaryMngListCnt(hospitalId: String, employeeQueryDto: EmployeeQueryDto): Long

    fun getSalaryMngDeleteList( hospitalId: String, paymentAt: String): List<EmployeeSalaryMngEntity>
}