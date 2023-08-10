package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("건강검진 매출목록")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "HEALTH_CARE")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class HealthCareEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "HOSPITAL_ID")
    val hospitalId: String? = null,

    @Column(name = "RECEIVE_DATA_ID")
    var receiveDataId: Long ? = null,

    @Comment("사업년도 - businessYear")
    @Column(name = "BUSINESS_YEAR")
    val businessYear: String? = null,

    @Comment("접수지사 - mgmtNo")
    @Column(name = "RECEIVING_BRANCH")
    val receivingBranch: String? = null,

    @Comment("청구구분 - hcDmdCdNm1")
    @Column(name = "CLAIM_TYPE")
    val claimType: String? = null,

    @Comment("청구번호 - grhcDmdNo")
    @Column(name = "CLAIM_NUMBER")
    val claimNumber: String? = null,

    @Comment("청구일자 - hcDmdFileCreDt")
    @Column(name = "CLAIM_DATE")
    val claimDate: String? = null,

    @Comment("접수일자 - hcDmdRecvDt")
    @Column(name = "RECEIVING_DATE")
    val receivingDate: String? = null,

    @Comment("청구인원 - hcDmdNop")
    @Column(name = "CLAIMED_PERSONS")
    val claimedPersons: Long? = null,

    @Comment("조정인원 - hcAdjsNop")
    @Column(name = "ADJUSTED_PERSONS")
    val adjustedPersons: Long? = null,

    @Comment("검진인원 - hcNop")
    @Column(name = "SCREENED_PERSONS")
    val screenedPersons: Long? = null,

    @Comment("삭감인원 - hcToatRdNop")
    @Column(name = "DEDUCTED_PERSONS")
    val deductedPersons: Long? = null,

    @Comment("지급인원 - hcPayTgtNop")
    @Column(name = "PAID_PERSONS")
    val paidPersons: Long? = null,

    @Comment("지급차수 - grhcPayDfno")
    @Column(name = "PAID_INSTALLMENTS")
    val paidInstallments: String? = null,

    @Comment("접수지사명 - brchNm")
    @Column(name = "RECEIVING_BRANCH_NAME")
    val receivingBranchName: String? = null,

    @Comment("접수번호 - billRecvNo")
    @Column(name = "RECEIVING_NUMBER")
    val receivingNumber: String? = null,

    @Comment("청구내역확인 - hcDmdFileNm")
    @Column(name = "CHECK_CLAIM_DETAILS")
    val checkClaimDetails: String? = null,

    @Comment("청구금액 - hcDmdAmt")
    @Column(name = "CLAIM_AMOUNT")
    val claimAmount: Long? = null,

    @Comment("조정금액 - hcAdjsAmt")
    @Column(name = "ADJUSTED_AMOUNT")
    val adjustedAmount: Long? = null,

    @Comment("검진금액 - hcAmt")
    @Column(name = "SCREENING_AMOUNT")
    val screeningAmount: Long? = null,

    @Comment("삭감금액 - hcToatRdAmt")
    @Column(name = "DEDUCTED_AMOUNT")
    val deductedAmount: Long? = null,

    @Comment("지급금액 - hcPayTgtAmt")
    @Column(name = "PAID_AMOUNT")
    val paidAmount: Long? = null,

    @Comment("지급일자 - payDt")
    @Column(name = "PAID_DATE")
    val paidDate: String? = null,

    @Comment("가산인원 - hcHdayAddNop")
    @Column(name = "ADDED_PERSONS")
    val addedPersons: Long? = null,

    @Comment("가산금액 - hcHdayAddAmt")
    @Column(name = "ADDED_AMOUNT")
    val addedAmount: Long? = null,

    @Comment("추가지급액 - adpyAmt")
    @Column(name = "ADDITIONAL_PAYMENT")
    val additionalPayment: Long? = null,

    @Comment("세액 - whtxTxam")
    @Column(name = "TAX_AMOUNT")
    val taxAmount: Long? = null,

    @Comment("환수액 - rdamAmt")
    @Column(name = "REFUND_AMOUNT")
    val refundAmount: Long? = null,

    @Comment("공제액 - gongjeAmt")
    @Column(name = "DEDUCTION_AMOUNT")
    val deductionAmount: Long? = null,

    @Comment("채권압류액 - grnsTgtSpcd")
    @Column(name = "RECEIVABLE_AMOUNT")
    val receivableAmount: Long? = null,

    @Comment("송금액 - rpymAmt")
    @Column(name = "REMITTANCE_AMOUNT")
    val remittanceAmount: Long? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)



