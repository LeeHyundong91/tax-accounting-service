package net.dv.tax.dto.employee

import java.time.LocalDateTime

data class EmployeeDto(

    var id: Long? = null,

    var residentNumber: String? = null,

    var hospitalId: String? = null ,

    var hospitalName: String? = null ,

    var name: String,

    var employmentName: String? = null,

    var employmentType: String? = null,

    var annualType: String? = null,

    var annualIncome: String? = null,

    var position: String? = null,

    var joinAt: String? = null,

    var email: String? = null,

    var jobClass: String,

    var reason: String? = null,

    var createdAt: String? = null,

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

    var updatedAt: String? = null,

    var fileList: List<EmployeeAttachFileDto>? = null

    )
