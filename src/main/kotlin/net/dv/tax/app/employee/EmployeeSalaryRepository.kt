package net.dv.tax.app.employee

import net.dv.tax.app.employee.support.EmployeeSalaryMngSupport
import net.dv.tax.app.employee.support.EmployeeSalarySupport
import net.dv.tax.domain.employee.EmployeeSalaryEntity
import net.dv.tax.domain.employee.EmployeeSalaryMngEntity
import net.dv.tax.domain.view.VHospitalMember
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

interface VHospitalMemberRepository: JpaRepository<VHospitalMember, String> {

    fun findByAccountId(accountId: String): VHospitalMember

    fun findByAccountIdAndRole(id: String, role: String): VHospitalMember

}



