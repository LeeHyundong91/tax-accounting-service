package net.dv.tax.app.employee

import net.dv.tax.domain.employee.EmployeeAttachFileEntity
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeHistoryEntity
import net.dv.tax.app.employee.support.EmployeeSupport
import net.dv.tax.domain.employee.EmployeeJobEntity
import net.dv.tax.domain.view.VHospital
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

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

    fun findByAccountIdAndHospitalId(
        accountId: String,
        hospitalId: String
    ): EmployeeEntity?

    fun findByNameAndEmail(
        name: String,
        email: String
    ): EmployeeEntity?
}

interface EmployeeAttachFileRepository: JpaRepository<EmployeeAttachFileEntity?, Int>


interface EmployeeHistoryRepository : JpaRepository<EmployeeHistoryEntity?, Int>


interface EmployeeJobRepository: JpaRepository<EmployeeJobEntity, Int> {
    @Query(value = """
        (SELECT * FROM EMPLOYEE_JOB WHERE COMMON = 'Y')
        UNION
        (SELECT * FROM EMPLOYEE_JOB WHERE SUBJECT = :subject)
        ORDER BY SUBJECT DESC, ID ASC
    """, nativeQuery = true)
    fun findBySubject(subject: Int): List<EmployeeJobEntity>
}
