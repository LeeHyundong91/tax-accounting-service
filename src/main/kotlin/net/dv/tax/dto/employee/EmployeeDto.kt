package net.dv.tax.dto.employee

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.Comment
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime

data class EmployeeDto(

    var id: Long,

    var residentNumber: String? = null,

    var hospitalId: String,

    var hospitalName: String,

    var name: String,

    var employmentType: String,

    var annualType: String,

    var annualIncome: String,

    var position: String? = null,

    var joinAt: String? = null,

    var email: String? = null,

    var jobClass: String,

    var reason: String? = null,

    var createdAt: String? = null,

    var requestState: String,

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
