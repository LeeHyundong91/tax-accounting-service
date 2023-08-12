package net.dv.tax.app.dto.employee

import net.dv.tax.app.employee.parseLocalDate
import net.dv.tax.app.enums.employee.RequestState
import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.utils.Encrypt
import java.time.LocalDate
import java.time.LocalDateTime

data class EmployeeRequestDto(
    var createdAt: LocalDateTime? = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null,
    var requestStateCode: String? = RequestState.RequestState_P.requestStateCode,
    var requestStateName: String? = null,
) : EmployeeBaseDto()

fun EmployeeRequestDto.toEmployeeRequestEntity(): EmployeeRequestEntity {
    val encrypt = Encrypt()
    val entity = EmployeeRequestEntity()
    entity.id = this.id
    entity.encryptResidentNumber = encrypt.encodeToBase64(this.residentNumber.toString())
    entity.residentNumber = this.residentNumber
    entity.hospitalId = this.hospitalId
    entity.hospitalName = this.hospitalName
    entity.employeeCode = this.employeeCode
    entity.name = this.name
    entity.careerBreakYn = this.careerBreakYn
    entity.spec = this.spec
    entity.academicHistory = this.academicHistory
    entity.contractDuration = this.contractDuration
    entity.employment = this.employment
    entity.annualType = this.annualType
    entity.annualIncome = this.annualIncome
    entity.position = this.position
    entity.joinAt = parseLocalDate(this.joinAt)
    entity.email = this.email
    entity.jobClass = this.jobClass
    entity.reason = this.reason
    entity.enlistmentAt = parseLocalDate(this.enlistmentAt)
    entity.dischargeAt = parseLocalDate(this.dischargeAt)
    entity.workRenewalAt = parseLocalDate(this.workRenewalAt)
    entity.resignationAt = parseLocalDate(this.resignationAt)
    entity.resignationContents = this.resignationContents
    entity.mobilePhoneNumber = this.mobilePhoneNumber
    entity.office = this.office
    entity.job = this.job
    entity.careerNumber = this.careerNumber
    entity.dependentCnt = this.dependentCnt
    entity.address = this.address
    entity.attachFileYn = this.attachFileYn
    entity.writerId = this.writerId
    entity.writerName = this.writerName
    entity.createdAt = this.createdAt
    entity.updatedAt = this.updatedAt
    entity.apprAt = LocalDateTime.parse(this.apprAt)
    entity.requestState = this.requestStateCode

    return entity
}