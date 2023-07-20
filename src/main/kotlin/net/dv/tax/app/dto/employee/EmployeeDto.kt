package net.dv.tax.app.dto.employee

import java.time.LocalDateTime

data class EmployeeDto(

    var id: Long? = null,

    var residentNumber: String? = null,

    var hospitalId: String? = null ,

    var hospitalName: String? = null ,

    var employeeCode: String? = null,

    var name: String? = null,

    var employment: String? = null,

    var annualType: String? = null,

    var annualIncome: String? = null,

    var position: String? = null,

    var joinAt: String? = null,

    var email: String? = null,

    var jobClass: String,

    var reason: String? = null,

    var createdAt: LocalDateTime? = null,

    var resignationAt: String? = null,

    var resignationContents: String? = null,

    var mobilePhoneNumber: String? = null,

    var office: String? = null,

    var job: String? = null,

    var careerNumber: String? = null,

    var dependentCnt: String? = null,

    var address: String? = null,

    var apprAt: LocalDateTime? = null,

    var attachFileYn: String? = "N",

    var updatedAt: LocalDateTime? = null,

    var fileList: List<EmployeeAttachFileDto>? = null,

    var writerId: String? = null,

    var writerName: String? = null,

    var taxRate: String? = null

    )
