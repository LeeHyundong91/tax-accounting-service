package net.dv.tax.repository.employee

import net.dv.tax.domain.employee.EmployeeAttachFileEntity
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeHistoryEntity
import net.dv.tax.repository.employee.support.EmployeeSupport
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository: JpaRepository<EmployeeEntity?, Int>,
    EmployeeSupport {

    fun findByHospitalIdAndResidentNumber(
        hospitalId: String,
        residentNumber: String
    ): EmployeeEntity?

    fun findByHospitalIdAndResidentNumberAndJobClass(
        hospitalId: String,
        residentNumber: String,
        jobClass: String
    ): EmployeeEntity?
}

interface EmployeeAttachFileRepository: JpaRepository<EmployeeAttachFileEntity?, Int>


interface EmployeeHistoryRepository : JpaRepository<EmployeeHistoryEntity?, Int>