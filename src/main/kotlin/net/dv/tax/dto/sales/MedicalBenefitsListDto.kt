package net.dv.tax.dto.sales

import jakarta.persistence.Column
import org.hibernate.annotations.Comment

class MedicalBenefitsListDto {

    var totalCount: Long? = 0

    @Comment("진료년월 - retO")
    val treatmentYearMonth: String? = null

    @Comment("총진료비(지급결정[심사결정]) - retAM")
    @Column(name = "DECISION_AD_TOTAL_AMOUNT")
    val decisionADTotalAmount: Long? = null

    @Comment("본인부담금(지급결정[심사결정]) - retAN")
    @Column(name = "DECISION_AD_OWN_CHARGE")
    val decisionADOwnCharge: Long? = null

    @Comment("지급결정(공단부담금) - retAQ")
    val corpPaymentDecision: Long? = null

    @Comment("과표(원천징수세액) - retAR")
    val withholdingTaxAmount: Long? = null

    @Comment("소득세(원천징수세액) - retAS")
    val incomeTaxAmount: Long? = null

    @Comment("주민세(원천징수세액) - retAT")
    val residentTaxAmount: Long? = null

    @Comment("세액계(원천징수세액) - retAU")
    val totalWithholdingTaxAmount: Long? = null

    @Comment("본인부담환급금 - retAV")
    val patientRefundAmount: Long? = null

    @Comment("가지급정산금 - retAW")
    val advancePaymentAmount: Long? = null

    @Comment("환수금 정산금(가) - retAX")
    @Column(name = "REFUND_AMOUNT_A")
    val refundAmountA: Long? = null

    @Comment("환수금 정산금(나) - retAY")
    @Column(name = "REFUND_AMOUNT_B")
    val refundAmountB: Long? = null

    @Comment("환수금정산금(다) - retAZ")
    @Column(name = "REFUND_AMOUNT_C")
    val refundAmountC: Long? = null

    @Comment("검사료지급 - retBA")
    val examPaymentFee: Long? = null

    @Comment("공제처리 - retBB")
    val deductionProcessing: Long? = null

    @Comment("대불비용(중재원공제금) - retBC")
    val replacementPayment: Long? = null

    @Comment("분담금(중재원공제금) - retBD")
    val divideAmount: Long? = null

    @Comment("절사금액 - retBE")
    val roundingAmount: Long? = null

    @Comment("증감금액 - retBF")
    val variationAmount: Long? = null

    @Comment("실지급 retBG")
    val actualPaymentAmount: Long? = null
}