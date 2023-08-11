package net.dv.tax.app.dto.employee

import net.dv.tax.app.enums.employee.RequestState
import java.time.LocalDate
import java.time.LocalDateTime

data class EmployeeRequestDto(

    var id: Long,

    var residentNumber: String? = null,

    var hospitalId: String? = null,

    var hospitalName: String? = null,

    var employeeCode: String? = null,

    var name: String? = null,

    var careerBreakYn: String? = "N",

    var spec: String? = null,

    var academicHistory: String? = null,

    var contractDuration: Int? = null,

    var employment: String? = null,

    var annualType: String? = null,

    var annualIncome: String? = null,

    var position: String? = null,

    var joinAt: LocalDate? = null,

    var email: String? = null,

    var jobClass: String,

    var reason: String? = null,

    var enlistmentAt: LocalDate? = null,

    var dischargeAt: LocalDate? = null,

    var workRenewalAt: LocalDate? = null,

    var createdAt: LocalDateTime? = LocalDateTime.now(),

    var requestStateCode: String? = RequestState.RequestState_P.requestStateCode,

    var requestStateName: String? = null,

    var resignationAt: String? = null,

    var resignationContents: String? = null,

    var mobilePhoneNumber: String? = null,

    var office: String? = null,

    var job: String? = null,

    var jobDetail: Long? = null,

    var careerNumber: String? = null,

    var dependentCnt: String? = null,

    var address: String? = null,

    var apprAt: String? = null,

    var attachFileYn: String? = "N",

    var updatedAt: String? = null,

    var writerId: String? = null,

    var writerName: String? = null,

    )
