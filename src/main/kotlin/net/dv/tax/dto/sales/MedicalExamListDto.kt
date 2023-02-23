package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

class MedicalExamListDto {

    @Comment("접수년월")
    var dataPeriod: String? = null

    @Comment("접수금액")
    var receptionAmount: Long? = 0

    @Comment("건수")
    var benefitsCount: Long? = 0

    @Comment("총의료급여비용")
    var medicalBenefitsAmount: Long? = 0

    @Comment("본인부담금")
    var ownCharge: Long? = 0

    @Comment("장애인의료비")
    var disabledExpenses: Long? = 0

    @Comment("기관부담금")
    var agencyExpenses: Long? = 0

    @Comment("절사금액")
    var cutOffAmount: Long? = 0

    @Comment("기금부담금")
    var fundExpense: Long? = 0

    @Comment("대불금")
    var proxyPayment: Long? = 0

    @Comment("본인부담환급금")
    var refundPaid: Long? = 0

    @Comment("검사기관지급액")
    var agencyPayment: Long? = 0

    @Comment("당차수 실지급액")
    var actualPayment: Long? = 0
}