package net.dv.tax.dto.employee

import net.dv.tax.enum.employee.RequestState
import java.time.LocalDateTime

data class EmployeeRequestDto(

    var id: Long,

    var residentNumber: String? = null,

    var hospitalId: String? = null,

    var hospitalName: String? = null,

    var name: String,

    var employment: String? = null,

    var employmentType: String? = null,

    var annualType: String,

    var annualIncome: String,

    var position: String,

    var joinAt: String? = null,

    var email: String,

    var jobClass: String,

    var reason: String,

    var createdAt: LocalDateTime? = LocalDateTime.now(),

    var requestStateCode: String? = RequestState.RequestState_P.requestStateCode,

    var requestStateName: String? = null,

    var resignationAt: String? = null,

    var resignationContents: String? = null,

    var mobilePhoneNumber: String? = null,

    var office: String? = null,

    var job: Long? = null,

    var jobDetail: Long? = null,

    var careerNumber: String? = null,

    var dependentCnt: String? = null,

    var address: String? = null,

    var apprAt: String? = null,

    var attachFileYn: String? = "N",

    var updatedAt: String? = null,

    )
