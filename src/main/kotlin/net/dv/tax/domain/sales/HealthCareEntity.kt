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
@Table(name = "health_care")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class HealthCareEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val hospitalId: String? = null,

    var dataReceiveId: Long ? = null,

    @Comment("사업년도 - businessYear")
    val businessYear: String? = null,

    @Comment("접수지사 - mgmtNo")
    val receivingBranch: String? = null,

    @Comment("청구구분 - hcDmdCdNm1")
    val claimType: String? = null,

    @Comment("청구번호 - grhcDmdNo")
    val claimNumber: String? = null,

    @Comment("청구일자 - hcDmdFileCreDt")
    val claimDate: String? = null,

    @Comment("접수일자 - hcDmdRecvDt")
    val receivingDate: String? = null,

    @Comment("청구인원 - hcDmdNop")
    val claimedPersons: Long? = null,

    @Comment("조정인원 - hcAdjsNop")
    val adjustedPersons: Long? = null,

    @Comment("검진인원 - hcNop")
    val screenedPersons: Long? = null,

    @Comment("삭감인원 - hcToatRdNop")
    val deductedPersons: Long? = null,

    @Comment("지급인원 - hcPayTgtNop")
    val paidPersons: Long? = null,

    @Comment("지급차수 - grhcPayDfno")
    val paidInstallments: String? = null,

    @Comment("접수지사명 - brchNm")
    val receivingBranchName: String? = null,

    @Comment("접수번호 - billRecvNo")
    val receivingNumber: String? = null,

    @Comment("청구내역확인 - hcDmdFileNm")
    val checkClaimDetails: String? = null,

    @Comment("청구금액 - hcDmdAmt")
    val claimAmount: Long? = null,

    @Comment("조정금액 - hcAdjsAmt")
    val adjustedAmount: Long? = null,

    @Comment("검진금액 - hcAmt")
    val screeningAmount: Long? = null,

    @Comment("삭감금액 - hcToatRdAmt")
    val deductedAmount: Long? = null,

    @Comment("지급금액 - hcPayTgtAmt")
    val paidAmount: Long? = null,

    @Comment("지급일자 - payDt")
    val paidDate: String? = null,

    @Comment("가산인원 - hcHdayAddNop")
    val addedPersons: Long? = null,

    @Comment("가산금액 - hcHdayAddAmt")
    val addedAmount: Long? = null,

    @Comment("추가지급액 - adpyAmt")
    val additionalPayment: Long? = null,

    @Comment("세액 - whtxTxam")
    val taxAmount: Long? = null,

    @Comment("환수액 - rdamAmt")
    val refundAmount: Long? = null,

    @Comment("공제액 - gongjeAmt")
    val deductionAmount: Long? = null,

    @Comment("채권압류액 - grnsTgtSpcd")
    val receivableAmount: Long? = null,

    @Comment("송금액 - rpymAmt")
    val remittanceAmount: Long? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )



