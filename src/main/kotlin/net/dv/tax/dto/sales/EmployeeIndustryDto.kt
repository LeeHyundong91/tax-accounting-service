package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment
import java.util.*

class EmployeeIndustryDto {

    @Comment("기간")
    var dataPeriod: String? = null

    @Comment("지금일")
    var payday: String? = null

    @Comment("청구번호")
    var billingNo: Long = 0

    @Comment("청구건수")
    var billingCount: Long = 0

    @Comment("급여종류")
    var salaryType: String? = null

    @Comment("청구금액")
    var billingAmount: Long = 0

    @Comment("지급금액")
    var paymentAmount: Long = 0

    @Comment("실지급금액")
    var actualPayment: Long = 0

    @Comment("소득세")
    var incomeTax: Long = 0

    @Comment("주민세")
    var residenceTax: Long = 0

}
