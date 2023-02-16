package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment
import java.util.*

class EmployeeIndustryDto {

    @Comment("병원 아이디")
    var hospitalId: String? = null

    @Comment("기간")
    val dataPeriod: String? = null

    @Comment("지금일")
    val payday: String? = null

    @Comment("청구번호")
    val billingNo: Long = 0

    @Comment("청구건수")
    val billingCount: Long = 0

    @Comment("급여종류")
    val salaryType: String? = null

    @Comment("청구금액")
    val billingAmount: Long = 0

    @Comment("지급금액")
    val paymentAmount: Long = 0

    @Comment("실지급금액")
    val actualPayment: Long = 0

    @Comment("소득세")
    val incomeTax: Long = 0

    @Comment("주민세")
    val residenceTax: Long = 0


}
