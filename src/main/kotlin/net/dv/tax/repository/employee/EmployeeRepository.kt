package net.dv.tax.repository.employee

import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeHistoryEntity
import net.dv.tax.domain.employee.EmployeeSalaryEntity
import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.enum.employee.JobClass
import net.dv.tax.enum.employee.RequestState
import net.dv.tax.repository.employee.support.EmployeeSupport
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<EmployeeEntity?, Int>,
    EmployeeSupport {
    fun findAllByHospitalIdAndRequestState(
        hospitalId: String,
        requestState: String,
    ): List<EmployeeEntity>

    fun findByHospitalIdAndResidentNumberAndRequestStateNot(
        hospitalId: String,
        residentNumber: String,
        requestState: String
    ): EmployeeEntity?

    fun findByHospitalIdAndResidentNumberAndJobClass(
        hospitalId: String,
        residentNumber: String,
        jobClass: String
    ): EmployeeEntity?

}
