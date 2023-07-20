package net.dv.tax.app.employee

import net.dv.tax.domain.employee.EmployeeAttachFileEntity
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeHistoryEntity
import net.dv.tax.app.employee.support.EmployeeSupport
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

    fun findByHospitalIdAndEmployeeCode(
        hospitalId: String,
        employeeCode: String
    ): List<EmployeeEntity>

}

interface EmployeeAttachFileRepository: JpaRepository<EmployeeAttachFileEntity?, Int>


interface EmployeeHistoryRepository : JpaRepository<EmployeeHistoryEntity?, Int>