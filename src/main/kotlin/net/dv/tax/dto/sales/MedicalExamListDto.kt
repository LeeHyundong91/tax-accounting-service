package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

class MedicalExamListDto{

    @Comment("접수년월")
    var dataPeriod: String? = null

//    @Comment("접수일자")
//    var receptionDate: String? = null

    @Comment("접수금액 - retF")
    var receptionAmount: Long? = 0

    @Comment("총의료급여비용(의료급여비용 심사결정 내역) - retAJ")
    var medicalBenefitsAmount: Long? = 0

    @Comment("건수(의료급여비용 심사결정 내역) - retAK")
    var benefitsCount: Long? = 0

    @Comment("본인부담금(의료급여비용 심사결정 내역)- retAL" )
    var ownCharge: Long? = 0

    @Comment("장애인의료비(의료급여비용 심사결정 내역) - retAM")
    var disabledExpenses: Long? = 0

    @Comment("기관부담금(의료급여비용 심사결정 내역) - retAN")
    var agencyExpenses: Long? = 0

    @Comment("절사금액(의료급여비용 심사결정 내역) - retAO")
    var cutOffAmount: Long? = 0

    @Comment("기금부담금(지급결정) - retAP")
    var fundExpense: Long? = 0

    @Comment("대불금(지급결정) - retAQ")
    var proxyPayment: Long? = 0

    @Comment("본인부담환급금(지급결정) - retAR")
    var refundPaid: Long? = 0

    @Comment("검사기관지급액(지급결정) - retAS")
    var agencyPayment: Long? = 0

    @Comment("당차수 실지급액 - retAV")
    var actualPayment: Long? = 0
}