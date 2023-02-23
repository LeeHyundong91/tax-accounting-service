package net.dv.tax.dto.employee

import java.time.LocalDateTime

data class EmployeeRequestDto(

    val id: Long,

    var residentNumber: String? = null,

    var name: String,

    var employment: String,

    var annualType: String,

    var annualIncome: String,

    var position: String,

    var joinAt: LocalDateTime,

    var email: String,

    var jobClass: String,

    var reason: String,

    var createdAt: LocalDateTime,

    var requestStateCode: String,

    var requestStateName: String,

    )
