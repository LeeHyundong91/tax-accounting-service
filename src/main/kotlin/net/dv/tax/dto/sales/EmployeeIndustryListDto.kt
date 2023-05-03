package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment
import java.util.*

data class EmployeeIndustryListDto(

    val employeeIndustryList: List<EmployeeIndustryDto>,

    var totalList: EmployeeIndustryDto? = null,

    )

data class EmployeeIndustryDto(

    @Comment("진료년월")
    var treatmentYearMonth: String? = null,

    @Comment("청구건수")
    var totalCount: Long = 0,

    @Comment("청구금액")
    var billingAmount: Long = 0,

    @Comment("지급금액")
    var paymentAmount: Long = 0,

    @Comment("소득세")
    var incomeTax: Long = 0,

    @Comment("주민세")
    var residenceTax: Long = 0,

    @Comment("실지급금액")
    var actualPayment: Long = 0,

    )


