package net.dv.tax.app.employee

import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.app.employee.support.EmployeeRequestSupport

import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRequestRepository : JpaRepository<EmployeeRequestEntity?, Int>,
    EmployeeRequestSupport {
    fun findAllByHospitalIdAndRequestState(
        hospitalId: String,
        requestState: String,
    ): List<EmployeeRequestEntity>

}
