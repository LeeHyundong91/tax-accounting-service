package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Comment("요양급여매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "medical_benefits")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class MedicalBenefitsEntity(
    /**
     * Medical expenses statement - MES
     * 진료비 명세서
     *
     * Audit Decision - AD
     * 심사결정
     *
     *
     */

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id: Long,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String?,

    @Column(name = "RECEIVE_DATA_ID")
    val receiveDataId: Long?,

    @Comment("진행상태 - retA")
    @Column(name = "PROCESSING_STATUS")
    val processingStatus: String? = null,

    @Comment("심사차수 - retB")
    @Column(name = "REVIEW_COUNT")
    val reviewCount: String? = null,

    @Comment("업무구분 - retC")
    @Column(name = "BUSINESS_TYPE")
    val businessType: String? = null,

    @Comment("인수일자 - retD")
    @Column(name = "ACCEPTANCE_DATE")
    val acceptanceDate: String? = null,

    @Comment("접수번호(청구) - retE")
    @Column(name = "CLAIM_NUMBER")
    val claimNumber: String? = null,

    @Comment("접수건수(청구) - retF")
    @Column(name = "CLAIM_COUNT")
    val claimCount: Long? = null,

    @Comment("접수금액(청구) - retG")
    @Column(name = "CLAIM_AMOUNT")
    val claimAmount: Long? = null,

    @Comment("지급구분 - retH")
    @Column(name = "PAYMENT_TYPE")
    val paymentType: String? = null,

    @Comment("지급일자(예정) - retI")
    @Column(name = "SCHEDULED_PAYMENT_DATE")
    val scheduledPaymentDate: String? = null,

    @Comment("지급건수 - retJ")
    @Column(name = "PAYMENT_COUNT")
    val paymentCount: Long? = null,

    @Comment("지급금액 - retK")
    @Column(name = "PAYMENT_AMOUNT")
    val paymentAmount: Long? = null,

    @Comment("지급차수 - retL")
    @Column(name = "PAYMENT_ORDER_COUNT")
    val paymentOrderCount: String? = null,

    @Comment("심사지원 - retM")
    @Column(name = "REVIEW_SUPPORT")
    val reviewSupport: String? = null,

    @Comment("묶음번호 - retN")
    @Column(name = "BUNDLE_NUMBER")
    val bundleNumber: String? = null,

    @Comment("진료년월 - retO")
    @Column(name = "TREATMENT_YEAR_MONTH")
    val treatmentYearMonth: String? = null,

    @Comment("청구일자 - retP")
    @Column(name = "CLAIM_DATE")
    val claimDate: String? = null,

    @Comment("접수일자 - retQ")
    @Column(name = "RECEIPT_DATE")
    val receiptDate: String? = null,

    @Comment("건수(청구 및 심사결정[진료비명세서]) - retR")
    @Column(name = "CLAIM_MES_COUNT")
    val claimMESCount: Long? = null,

    @Comment("총진료비(청구 및 심사결정[진료비명세서]) - retS")
    @Column(name = "CLAIM_MES_TOTAL_AMOUNT")
    val claimMESTotalAmount: Long? = null,

    @Comment("본인부담금(청구 및 심사결정[진료비명세서]) - retT")
    @Column(name = "CLAIM_MES_OWN_CHARGE")
    val claimMESOwnCharge: Long? = null,

    @Comment("청구액(청구 및 심사결정[진료비명세서]) - retU")
    @Column(name = "CLAIM_MES_BILLING_AMOUNT")
    val claimMESBillingAmount: Long? = null,

    @Comment("건수(청구 및 심사결정[심사결정]) - retV")
    @Column(name = "CLAIM_AD_COUNT")
    val claimADCount: Long? = null,

    @Comment("총진료비(청구 및 심사결정[심사결정]) - retW")
    @Column(name = "CLAIM_AD_TOTAL_AMOUNT")
    val claimADTotalAmount: Long? = null,

    @Comment("본인부담금(청구 및 심사결정[심사결정]) - retX")
    @Column(name = "CLAIM_AD_OWN_CHARGE")
    val claimADOwnCharge: Long? = null,

    @Comment("청구액(청구 및 심사결정[심사결정]) - retY")
    @Column(name = "CLAIM_AD_BILLING_AMOUNT")
    val claimADBillingAmount: Long? = null,

    @Comment("건수(지급불능 및 보류[진료비명세서]) - retZ")
    @Column(name = "DEFER_MES_COUNT")
    val deferMESCount: Long? = null,

    @Comment("총진료비(지급불능 및 보류[진료비명세서]) - retAA")
    @Column(name = "DEFER_MES_TOTAL_AMOUNT")
    val deferMESTotalAmount: Long? = null,

    @Comment("본인부담금(지급불능 및 보류[진료비명세서]) - retAB")
    @Column(name = "DEFER_MES_OWN_CHARGE")
    val deferMESOwnCharge: Long? = null,

    @Comment("청구액(지급불능 및 보류[진료비명세서]) - retAC")
    @Column(name = "DEFER_MES_BILLING_AMOUNT")
    val deferMESBillingAmount: Long? = null,

    @Comment("건수(지급불능 및 보류[심사결정]) - retAD")
    @Column(name = "DEFER_AD_COUNT")
    val deferADCount: Long? = null,

    @Comment("총진료비(지급불능 및 보류[심사결정]) - retAE")
    @Column(name = "DEFER_AD_TOTAL_AMOUNT")
    val deferADTotalAmount: Long? = null,

    @Comment("본인부담금(지급불능 및 보류[심사결정]) - retAF")
    @Column(name = "DEFER_AD_OWN_CHARGE")
    val deferADOwnCharge: Long? = null,

    @Comment("청구액(지급불능 및 보류[심사결정]) - retAG")
    @Column(name = "DEFER_AD_BILLING_AMOUNT")
    val deferADBillingAmount: Long? = null,

    @Comment("건수(지급결정[진료비명세서]) - retAH")
    @Column(name = "DECISION_MES_COUNT")
    val decisionMESCount: Long? = null,

    @Comment("총진료비(지급결정[진료비명세서]) - retAI")
    @Column(name = "DECISION_MES_TOTAL_AMOUNT")
    val decisionMESTotalAmount: Long? = null,

    @Comment("본인부담금(지급결정[진료비명세서]) - retAJ")
    @Column(name = "DECISION_MES_OWN_CHARGE")
    val decisionMESOwnCharge: Long? = null,

    @Comment("청구액(지급결정[진료비명세서]) - retAK")
    @Column(name = "DECISION_MES_BILLING_AMOUNT")
    val decisionMESBillingAmount: Long? = null,

    @Comment("건수(지급결정[심사결정]) - retAL")
    @Column(name = "DECISION_AD_COUNT")
    val decisionADCount: Long? = null,

    @Comment("총진료비(지급결정[심사결정]) - retAM")
    @Column(name = "DECISION_AD_TOTAL_AMOUNT")
    val decisionADTotalAmount: Long? = null,

    @Comment("본인부담금(지급결정[심사결정]) - retAN")
    @Column(name = "DECISION_AD_OWN_CHARGE")
    val decisionADOwnCharge: Long? = null,

    @Comment("청구액(지급결정[심사결정]) - retAO")
    @Column(name = "DECISION_AD_BILLING_AMOUNT")
    val decisionADBillingAmount: Long? = null,

    @Comment("지급일자 - retAP")
    @Column(name = "PAYMENT_DATE")
    val paymentDate: String? = null,

    @Comment("지급결정(공단부담금) - retAQ")
    @Column(name = "CORP_PAYMENT_DECISION")
    val corpPaymentDecision: Long? = null,

    @Comment("과표(원천징수세액) - retAR")
    @Column(name = "WITHHOLDING_TAX_AMOUNT")
    val withholdingTaxAmount: Long? = null,

    @Comment("소득세(원천징수세액) - retAS")
    @Column(name = "INCOME_TAX_AMOUNT")
    val incomeTaxAmount: Long? = null,

    @Comment("주민세(원천징수세액) - retAT")
    @Column(name = "RESIDENT_TAX_AMOUNT")
    val residentTaxAmount: Long? = null,

    @Comment("세액계(원천징수세액) - retAU")
    @Column(name = "TOTAL_WITHHOLDING_TAX_AMOUNT")
    val totalWithholdingTaxAmount: Long? = null,

    @Comment("본인부담환급금 - retAV")
    @Column(name = "PATIENT_REFUND_AMOUNT")
    val patientRefundAmount: Long? = null,

    @Comment("가지급정산금 - retAW")
    @Column(name = "ADVANCE_PAYMENT_AMOUNT")
    val advancePaymentAmount: Long? = null,

    @Comment("환수금 정산금(가) - retAX")
    @Column(name = "REFUND_AMOUNT_A")
    val refundAmountA: Long? = null,

    @Comment("환수금 정산금(나) - retAY")
    @Column(name = "REFUND_AMOUNT_B")
    val refundAmountB: Long? = null,

    @Comment("환수금정산금(다) - retAZ")
    @Column(name = "REFUND_AMOUNT_C")
    val refundAmountC: Long? = null,

    @Comment("검사료지급 - retBA")
    @Column(name = "EXAM_PAYMENT_FEE")
    val examPaymentFee: Long? = null,

    @Comment("공제처리 - retBB")
    @Column(name = "DEDUCTION_PROCESSING")
    val deductionProcessing: Long? = null,

    @Comment("대불비용(중재원공제금) - retBC")
    @Column(name = "REPLACEMENT_PAYMENT")
    val replacementPayment: Long? = null,

    @Comment("분담금(중재원공제금) - retBD")
    @Column(name = "DIVIDE_AMOUNT")
    val divideAmount: Long? = null,

    @Comment("절사금액 - retBE")
    @Column(name = "ROUNDING_AMOUNT")
    val roundingAmount: Long? = null,

    @Comment("증감금액 - retBF")
    @Column(name = "VARIATION_AMOUNT")
    val variationAmount: Long? = null,

    @Comment("실지급 retBG")
    @Column(name = "ACTUAL_PAYMENT_AMOUNT")
    val actualPaymentAmount: Long? = null,
)
