package net.dv.tax.repository.employee.support

import net.dv.tax.domain.employee.EmployeeSalaryEntity

interface EmployeeSalarySupport {

    fun getSalaryDeleteList( hospitalId: String, paymentAt: String): List<EmployeeSalaryEntity>
}