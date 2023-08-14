package net.dv.tax.app.dto.employee

import net.dv.tax.app.employee.parseLocalDate
import net.dv.tax.app.enums.employee.RequestState
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeRequestEntity
import java.time.LocalDateTime

data class EmployeeRequestDto(
    var createdAt: LocalDateTime? = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null,
    var requestState: String? = RequestState.RequestState_P.requestStateCode,
    var requestStateName: String? = null,
    var requestReason: String? = null,
) : EmployeeBaseDto()

fun EmployeeRequestDto.toEmployeeRequestEntity(): EmployeeRequestEntity {
    val entity = EmployeeRequestEntity()

    this.copyCommonPropertiesTo(entity)

    entity.requestState = this.requestState
    return entity
}

fun EmployeeRequestEntity.updateFromDto(dto: EmployeeRequestDto): EmployeeRequestEntity {
    dto.updateCommonPropertiesTo(this)
    this.joinAt = parseLocalDate(dto.joinAt)
    this.enlistmentAt = parseLocalDate(dto.enlistmentAt)
    this.dischargeAt = parseLocalDate(dto.dischargeAt)
    this.workRenewalAt = parseLocalDate(dto.workRenewalAt)
    this.leaveStartAt = parseLocalDate(dto.leaveStartAt)
    this.leaveEndAt = parseLocalDate(dto.leaveEndAt)
    this.resignationAt = parseLocalDate(dto.resignationAt)
    this.updatedAt = LocalDateTime.now()
    this.requestReason = dto.requestReason
    this.requestState = dto.requestState

    return this
}

fun EmployeeRequestEntity.toEmployeeDto(dto: EmployeeDto): EmployeeDto {
    this.updateCommonPropertiesTo(dto)
    dto.joinAt = this.joinAt.toString()
    dto.enlistmentAt = this.enlistmentAt.toString()
    dto.dischargeAt = this.dischargeAt.toString()
    dto.workRenewalAt = this.workRenewalAt.toString()
    dto.leaveStartAt = this.leaveStartAt.toString()
    dto.leaveEndAt = this.leaveEndAt.toString()
    dto.resignationAt = this.resignationAt.toString()

    return dto
}