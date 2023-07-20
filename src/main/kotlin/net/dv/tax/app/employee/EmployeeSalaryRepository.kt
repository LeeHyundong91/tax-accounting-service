package net.dv.tax.app.employee
import net.dv.tax.domain.employee.EmployeeSalaryEntity
import net.dv.tax.domain.employee.EmployeeSalaryMngEntity
import net.dv.tax.app.employee.support.EmployeeSalaryMngSupport
import net.dv.tax.app.employee.support.EmployeeSalarySupport

import org.springframework.data.jpa.repository.JpaRepository


interface EmployeeSalaryRepository : JpaRepository<EmployeeSalaryEntity?, Int>,
    EmployeeSalarySupport {
    fun findByHospitalIdAndEmployeeCode(
        hospitalId: String,
        employeeCode: String
    ): List<EmployeeSalaryEntity>

    fun findByEmployeeSalaryMng(
        employeeSalaryMng: EmployeeSalaryMngEntity
    ): List<EmployeeSalaryEntity>

}

interface EmployeeSalaryMngRepository : JpaRepository<EmployeeSalaryMngEntity?, Int>,
    EmployeeSalaryMngSupport{
}





