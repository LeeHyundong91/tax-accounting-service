package net.dv.tax.app.dto.employee

import net.dv.tax.app.employee.parseLocalDate
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeHistoryEntity
import net.dv.tax.utils.Encrypt
import java.time.LocalDateTime

data class EmployeeDto(
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var fileList: List<EmployeeAttachFileDto>? = mutableListOf(),
    var taxRate: String? = null,
    var accountId: String? = null,

    // 프론트를 위한 코드
    var jobList: List<String>? = mutableListOf(),
    var academicHistoryList: List<String>? = mutableListOf()
) : EmployeeBaseDto()

fun EmployeeDto.toEmployeeEntity(): EmployeeEntity {
    val entity = EmployeeEntity()
    this.copyCommonPropertiesTo(entity)

    val encrypt = Encrypt()
    entity.encryptResidentNumber = encrypt.encodeToBase64(this.residentNumber.toString())
    entity.apprAt = LocalDateTime.parse(this.apprAt)
    return entity
}
fun EmployeeEntity.toEmployeeDto(): EmployeeDto {
    val dto = EmployeeDto()
    this.copyCommonPropertiesTo(dto)
    dto.joinAt = this.joinAt?.toString()
    dto.enlistmentAt = this.enlistmentAt?.toString()
    dto.dischargeAt = this.dischargeAt?.toString()
    dto.workRenewalAt = this.workRenewalAt?.toString()
    dto.leaveStartAt = this.leaveStartAt?.toString()
    dto.leaveEndAt = this.leaveEndAt?.toString()
    dto.resignationAt = this.resignationAt?.toString()

    dto.apprAt = this.apprAt?.toString()

    return dto
}

fun EmployeeEntity.updateFromDto(dto: EmployeeDto): EmployeeEntity {
    dto.updateCommonPropertiesTo(this)
    this.joinAt = parseLocalDate(dto.joinAt)
    this.enlistmentAt = parseLocalDate(dto.enlistmentAt)
    this.dischargeAt = parseLocalDate(dto.dischargeAt)
    this.workRenewalAt = parseLocalDate(dto.workRenewalAt)
    this.leaveStartAt = parseLocalDate(dto.leaveStartAt)
    this.leaveEndAt = parseLocalDate(dto.leaveEndAt)
    this.resignationAt = parseLocalDate(dto.resignationAt)
    this.updatedAt = LocalDateTime.now()

    return this
}

fun EmployeeEntity.toHistoryEntity(): EmployeeHistoryEntity {
    val historyEntity = EmployeeHistoryEntity()
    this.copyCommonPropertiesTo(historyEntity)
    historyEntity.employee = this

    return historyEntity
}