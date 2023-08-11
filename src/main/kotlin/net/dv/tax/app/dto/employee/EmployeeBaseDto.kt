package net.dv.tax.app.dto.employee

import java.time.LocalDate
import java.time.LocalDateTime

open class EmployeeBaseDto (
    var id: Long? = null,
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
    var joinAt: String? = null,
    var email: String? = null,
    var jobClass: String? = null,
    var reason: String? = null,
    var enlistmentAt: String? = null,
    var dischargeAt: String? = null,
    var workRenewalAt: String? = null,
    var resignationAt: String? = null,
    var resignationContents: String? = null,
    var mobilePhoneNumber: String? = null,
    var office: String? = null,
    var job: String? = null,
    var careerNumber: String? = null,
    var dependentCnt: String? = null,
    var address: String? = null,
    var attachFileYn: String? = "N",
    var writerId: String? = null,
    var writerName: String? = null
)
