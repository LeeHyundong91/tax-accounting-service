package net.dv.tax.dto.sales

import jakarta.persistence.Column
import org.hibernate.annotations.Comment

data class MedicalBenefitsListDto(
    var medicalBenefitsList: List<MedicalBenefitsDto>,
    var listTotal: MedicalBenefitsDto? = null,
)


data class MedicalBenefitsDto(
    var totalCount: Long? = 0,

    @Comment("진료년월 - retO")
    var treatmentYearMonth: String? = null,

    @Comment("총진료비(지급결정[심사결정]) - retAM")
    @Column(name = "DECISION_AD_TOTAL_AMOUNT")
    var decisionADTotalAmount: Long? = 0,

    @Comment("본인부담금(지급결정[심사결정]) - retAN")
    @Column(name = "DECISION_AD_OWN_CHARGE")
    var decisionADOwnCharge: Long? = 0,

    @Comment("지급결정(공단부담금) - retAQ")
    var corpPaymentDecision: Long? = 0,

    @Comment("과표(원천징수세액) - retAR")
    var withholdingTaxAmount: Long? = 0,

    @Comment("소득세(원천징수세액) - retAS")
    var incomeTaxAmount: Long? = 0,

    @Comment("주민세(원천징수세액) - retAT")
    var residentTaxAmount: Long? = 0,

    @Comment("세액계(원천징수세액) - retAU")
    var totalWithholdingTaxAmount: Long? = 0,

    @Comment("본인부담환급금 - retAV")
    var patientRefundAmount: Long? = 0,

    @Comment("가지급정산금 - retAW")
    var advancePaymentAmount: Long? = 0,

    @Comment("환수금 정산금(가) - retAX")
    @Column(name = "REFUND_AMOUNT_A")
    var refundAmountA: Long? = 0,

    @Comment("환수금 정산금(나) - retAY")
    @Column(name = "REFUND_AMOUNT_B")
    var refundAmountB: Long? = 0,

    @Comment("환수금정산금(다) - retAZ")
    @Column(name = "REFUND_AMOUNT_C")
    var refundAmountC: Long? = 0,

    @Comment("검사료지급 - retBA")
    var examPaymentFee: Long? = 0,

    @Comment("공제처리 - retBB")
    var deductionProcessing: Long? = 0,

    @Comment("대불비용(중재원공제금) - retBC")
    var replacementPayment: Long? = 0,

    @Comment("분담금(중재원공제금) - retBD")
    var divideAmount: Long? = 0,

    @Comment("절사금액 - retBE")
    var roundingAmount: Long? = 0,

    @Comment("증감금액 - retBF")
    var variationAmount: Long? = 0,

    @Comment("실지급 retBG")
    var actualPaymentAmount: Long? = 0,
)
