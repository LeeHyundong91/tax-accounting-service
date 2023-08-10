package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Comment("의료급여매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "MEDICAL_CARE")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class MedicalCareEntity(

    /**
     * Medical benefits - MB
     * 의료급여
     */

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id: Long?,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Column(name = "RECEIVE_DATA_ID")
    var receiveDataId: Long,

    @Comment("업무구분 - retA")
    @Column(name = "BUSINESS_TYPE")
    val businessType: String? = null,

    @Comment("예탁기관기호 - retB")
    @Column(name = "AGENCY_SYMBOL")
    val agencySymbol: String? = null,

    @Comment("예탁기관명 - retC")
    @Column(name = "AGENCY_NAME")
    val agencyName: String? = null,

    @Comment("접수일자 - retD")
    @Column(name = "RECEIPT_DATE")
    val receiptDate: String? = null,

    @Comment("접수번호 - retE")
    @Column(name = "CLAIM_NUMBER")
    val claimNumber: String? = null,

    @Comment("접수금액 - retF")
    @Column(name = "CLAIM_AMOUNT")
    val claimAmount: Long? = null,

    @Comment("공단인수일자 - retG")
    @Column(name = "TAKEOVER_DATE")
    val takeoverDate: String? = null,

    @Comment("지급구분 - retH")
    @Column(name = "PAYMENT_TYPE")
    val paymentType: String? = null,

    @Comment("지급일자 - retI")
    @Column(name = "PAYMENT_DATE")
    val paymentDate: String? = null,

    @Comment("지급금액 - retJ")
    @Column(name = "PAYMENT_AMOUNT")
    val paymentAmount: Long? = null,

    @Comment("미지급금액 - retK")
    @Column(name = "ACCOUNT_PAYABLE")
    val accountsPayable: Long? = null,

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

    @Comment("심사차수 - retP")
    @Column(name = "REVIEW_COUNT")
    val reviewCount: String? = null,

    @Comment("공단지급차수 - retQ")
    @Column(name = "CORP_PAYMENT_ORDER")
    val corpPaymentOrder: String? = null,

    @Comment("지급결정 기금부담금 - retR")
    @Column(name = "PAYMENT_DECISION")
    val paymentDecision: Long? = null,

    @Comment("지급보류액 - retS")
    @Column(name = "PENDING_PAYMENT")
    val pendingPayment: Long? = null,

    @Comment("지급불능액 - retT")
    @Column(name = "INSOLVENCY_AMOUNT")
    val insolvencyAmount: Long? = null,

    @Comment("예탁부족 미지급금 - retU")
    @Column(name = "LACK_DEPOSIT_A")
    val lackDepositA: Long? = null,

    @Comment("소득세(원천징수액) - retV")
    @Column(name = "INCOME_TAX")
    val incomeTax: Long? = null,

    @Comment("주민세(원천징수액) - retW")
    @Column(name = "RESIDENT_TAX")
    val residentTax: Long? = null,

    @Comment("세액계(원천징수액) - retX")
    @Column(name = "TAX_AMOUNT")
    val taxAmount: Long? = null,

    @Comment("가(환수상계액) - retY")
    @Column(name = "REDEMPTION_AMOUNT_A")
    val redemptionAmountA: Long? = null,

    @Comment("나(환수상계액) - retZ")
    @Column(name = "REDEMPTION_AMOUNT_B")
    val redemptionAmountB: Long? = null,

    @Comment("다(환수상계액) - retAA")
    @Column(name = "REDEMPTION_AMOUNT_C")
    val redemptionAmountC: Long? = null,

    @Comment("공제금액 - retAB")
    @Column(name = "DEDUCTION_AMOUNT")
    val deductionAmount: Long? = null,

    @Comment("본인부담 환급금 - retAC")
    @Column(name = "PATIENT_REFUND_AMOUNT")
    val patientRefundAmount: Long? = null,

    @Comment("검사기관 지급액 - retAD")
    @Column(name = "AGENCY_PAYMENT")
    val agencyPayment: Long? = null,

    @Comment("절사금액 - retAE")
    @Column(name = "ROUNDING_AMOUNT")
    val roundingAmount: Long? = null,

    @Comment("당차수 실지급액 - retAF")
    @Column(name = "ACTUAL_PAYMENT_A")
    val actualPaymentA: Long? = null,

    @Comment("이전차수 미지급금 지급액 - retAG")
    @Column(name = "PREVIOUS_PAYABLE_AMOUNT_A")
    val previousPayableAmountA: Long? = null,

    @Comment("지급액 합계 - retAH")
    @Column(name = "PAYOUT_AMOUNT")
    val payoutAmount: Long? = null,

    @Comment("심평원 지급차수 - retAI")
    @Column(name = "PAYMENT_ORDER")
    val paymentOrder: String? = null,

    @Comment("총의료급여비용(의료급여비용 심사결정 내역) - retAJ")
    @Column(name = "TOTAL_AMOUNT_MB")
    val totalAmountMB: Long? = null,

    @Comment("건수(의료급여비용 심사결정 내역) - retAK")
    @Column(name = "COUNT_MB")
    val countMB: Long? = null,

    @Comment("본인부담금(의료급여비용 심사결정 내역) - retAL")
    @Column(name = "OWN_CHARGE_MB")
    val ownChargeMB: Long? = null,

    @Comment("장애인의료비(의료급여비용 심사결정 내역) - retAM")
    @Column(name = "DISABLED_EXPENSE_MB")
    val disabledExpenseMB: Long? = null,

    @Comment("기관부담금(의료급여비용 심사결정 내역) - retAN")
    @Column(name = "AGENCY_DUES_MB")
    val agencyDuesMB: Long? = null,

    @Comment("절사금액(의료급여비용 심사결정 내역) - retAO")
    @Column(name = "ROUNDING_AMOUNT_MB")
    val roundingAmountMB: Long? = null,

    @Comment("기금부담금(지급결정) - retAP")
    @Column(name = "FUND_DUES_AD")
    val fundDuesAD: Long? = null,

    @Comment("대불금(지급결정) - retAQ")
    @Column(name = "ALTERNATIVE_PAYOUT_AD")
    val alternativePayoutAD: Long? = null,

    @Comment("본인부담환급금(지급결정) - retAR")
    @Column(name = "PATIENT_REFUND_AMOUNT_AD")
    val patientRefundAmountAD: Long? = null,

    @Comment("검사기관지급액(지급결정) - retAS")
    @Column(name = "AGENCY_PAYMENT_AD")
    val agencyPaymentAD: Long? = null,

    @Comment("예탁부족 미지급금 - retAT")
    @Column(name = "LACK_DEPOSIT_B")
    val lackDepositB: Long? = null,

    @Comment("이전차수 미지급금 지급액 - retAU")
    @Column(name = "PREVIOUS_PAYABLE_AMOUNT_B")
    val previousPayableAmountB: Long? = null,

    @Comment("당차수 실지급액 - retAV")
    @Column(name = "ACTUAL_PAYMENT_B")
    val actualPaymentB: Long? = null,

    @Comment("국가재난의료비지원금(본인부담금) - retAW")
    @Column(name = "NATIONAL_MEDICAL_PAYMENT")
    val nationalMedicalPayment: Long? = null,
)