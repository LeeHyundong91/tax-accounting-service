package net.dv.tax.dto.sales

import jakarta.persistence.Column
import org.hibernate.annotations.Comment

data class MedicalCareListDto(
    var medicalBenefitsList: List<MedicalCareDto>,
    var listTotal: MedicalCareDto,
)

data class MedicalCareDto(

    @Comment("진료년월 - retO")
    var treatmentYearMonth: String? = null,

    var totalCount: Long = 0,

    @Comment("소득세(원천징수액) - retV")
    var incomeTax: Long? = 0,

    @Comment("주민세(원천징수액) - retW")
    var residentTax: Long? = 0,

    @Comment("세액계(원천징수액) - retX")
    var taxAmount: Long? = 0,

    @Comment("가(환수상계액) - retY")
    @Column(name = "REDEMPTION_AMOUNT_A")
    var redemptionAmountA: Long? = 0,

    @Comment("나(환수상계액) - retZ")
    @Column(name = "REDEMPTION_AMOUNT_B")
    var redemptionAmountB: Long? = 0,

    @Comment("다(환수상계액) - retAA")
    @Column(name = "REDEMPTION_AMOUNT_C")
    var redemptionAmountC: Long? = 0,

    @Comment("공제금액 - retAB")
    var deductionAmount: Long? = 0,

    @Comment("본인부담 환급금 - retAC")
    var patientRefundAmount: Long? = 0,

    @Comment("검사기관 지급액 - retAD")
    var agencyPayment: Long? = 0,

    @Comment("절사금액 - retAE")
    var roundingAmount: Long? = 0,

    @Comment("당차수 실지급액 - retAF")
    @Column(name = "ACTUAL_PAYMENT_A")
    var actualPaymentA: Long? = 0,

    @Comment("이전차수 미지급금 지급액 - retAG")
    @Column(name = "PREVIOUS_PAYABLE_AMOUNT_A")
    var previousPayableAmountA: Long? = 0,

    @Comment("지급액 합계 - retAH")
    var payoutAmount: Long? = 0,

    @Comment("총의료급여비용(의료급여비용 심사결정 내역) - retAJ")
    @Column(name = "TOTAL_AMOUNT_MB")
    var totalAmountMB: Long? = 0,

    @Comment("건수(의료급여비용 심사결정 내역) - retAK")
    @Column(name = "COUNT_MB")
    var countMB: Long? = 0,

    @Comment("본인부담금(의료급여비용 심사결정 내역) - retAL")
    @Column(name = "OWN_CHARGE_MB")
    var ownChargeMB: Long? = 0,

    @Comment("장애인의료비(의료급여비용 심사결정 내역) - retAM")
    @Column(name = "DISABLED_EXPENSE_MB")
    var disabledExpenseMB: Long? = 0,

    @Comment("기관부담금(의료급여비용 심사결정 내역) - retAN")
    @Column(name = "AGENCY_DUES_MB")
    var agencyDuesMB: Long? = 0,

    @Comment("절사금액(의료급여비용 심사결정 내역) - retAO")
    @Column(name = "ROUNDING_AMOUNT_MB")
    var roundingAmountMB: Long? = 0,

    @Comment("기금부담금(지급결정) - retAP")
    @Column(name = "FUND_DUES_AD")
    var fundDuesAD: Long? = 0,

    @Comment("대불금(지급결정) - retAQ")
    @Column(name = "ALTERNATIVE_PAYOUT_AD")
    var alternativePayoutAD: Long? = 0,

    @Comment("본인부담환급금(지급결정) - retAR")
    @Column(name = "PATIENT_REFUND_AMOUNT_AD")
    var patientRefundAmountAD: Long? = 0,

    @Comment("검사기관지급액(지급결정) - retAS")
    @Column(name = "AGENCY_PAYMENT_AD")
    var agencyPaymentAD: Long? = 0,

    @Comment("예탁부족 미지급금 - retAT")
    @Column(name = "LACK_DEPOSIT_B")
    var lackDepositB: Long? = 0,

    @Comment("이전차수 미지급금 지급액 - retAU")
    @Column(name = "PREVIOUS_PAYABLE_AMOUNT_B")
    var previousPayableAmountB: Long? = 0,

    @Comment("당차수 실지급액 - retAV")
    @Column(name = "ACTUAL_PAYMENT_B")
    var actualPaymentB: Long? = 0,

    @Comment("국가재난의료비지원금(본인부담금) - retAW")
    var nationalMedicalPayment: Long? = 0,

    )
