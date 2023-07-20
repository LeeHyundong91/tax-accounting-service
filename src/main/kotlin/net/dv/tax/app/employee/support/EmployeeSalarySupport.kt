package net.dv.tax.app.employee.support

import net.dv.tax.domain.employee.EmployeeSalaryEntity

interface EmployeeSalarySupport {

    fun getSalaryDeleteList( hospitalId: String, paymentAt: String): List<EmployeeSalaryEntity>
}