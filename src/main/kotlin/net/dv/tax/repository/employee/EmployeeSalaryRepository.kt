package net.dv.tax.repository.employee
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeSalaryEntity
import net.dv.tax.domain.employee.EmployeeSalaryMngEntity
import net.dv.tax.repository.employee.support.EmployeeSalaryMngSupport
import net.dv.tax.repository.employee.support.EmployeeSalarySupport

import org.springframework.data.jpa.repository.JpaRepository


interface EmployeeSalaryRepository : JpaRepository<EmployeeSalaryEntity?, Int>,
    EmployeeSalarySupport {
    fun findByHospitalIdAndEmployee(
        hospitalId: String,
        employee: EmployeeEntity
    ): List<EmployeeSalaryEntity>

    fun findByEmployeeSalaryMng(
        employeeSalaryMng: EmployeeSalaryMngEntity
    ): List<EmployeeSalaryEntity>

}

interface EmployeeSalaryMngRepository : JpaRepository<EmployeeSalaryMngEntity?, Int>,
    EmployeeSalaryMngSupport{
}





