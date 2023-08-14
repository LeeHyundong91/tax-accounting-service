package net.dv.tax.app.dto.employee

import net.dv.tax.app.employee.parseLocalDate
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeHistoryEntity
import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.utils.Encrypt
import java.time.LocalDateTime
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

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



// 리플렉션으로 소스객체의 모든 프로퍼티를 대상 객체의 해당 프로퍼티로 복사한다. (같은 프로퍼티끼리만 복사된다는 말)
fun <T : Any, R : Any> T.copyCommonPropertiesTo(target: R) {
    val sourceProperties = this::class.members.filterIsInstance<KProperty1<T, *>>()
    val targetProperties = target::class.members.filterIsInstance<KMutableProperty1<R, *>>()

    for (sourceProp in sourceProperties) {
        val targetProp = targetProperties.find { it.name == sourceProp.name && it.returnType == sourceProp.returnType }
        if (targetProp != null) {
            val srcValue = sourceProp.get(this)
            val tarValue = targetProp.get(target)
            targetProp.setter.call(target, srcValue)
        }
    }
}

fun <T : Any, R : Any> T.updateCommonPropertiesTo(target: R) {
    val sourceProperties = this::class.members.filterIsInstance<KProperty1<T, *>>()
    val targetProperties = target::class.members.filterIsInstance<KMutableProperty1<R, *>>()

    for (sourceProp in sourceProperties) {
        val targetProp = targetProperties.find { it.name == sourceProp.name && it.returnType == sourceProp.returnType }
        val srcValue = sourceProp.get(this)
        if (targetProp != null && srcValue != null) {
            targetProp.setter.call(target, srcValue)
        }
    }
}

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