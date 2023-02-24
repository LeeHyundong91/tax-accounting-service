package net.dv.tax.repository.employee

import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.repository.employee.support.EmployeeRequestSupport

import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRequestRepository : JpaRepository<EmployeeRequestEntity?, Int>,
    EmployeeRequestSupport {
    fun findAllByHospitalIdAndRequestState(
        hospitalId: String,
        requestState: String,
    ): List<EmployeeRequestEntity>

}
