package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("자동차보험 매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "car_insurance")
@EntityListeners(AuditingEntityListener::class)
data class CarInsuranceEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val receiveDataId: Long,

    @Comment("병원 아이디")
    var hospitalId: String,

    @Comment("처리상태 - retStatus")
    val processingStatus: String? = null,

    @Comment("접수일자 - recvDt")
    val receivingDate: String? = null,

    @Comment("진료년월 - diagYyyymm")
    val treatmentYearMonth: String? = null,

    @Comment("접수번호 - recvNo")
    val receivingNumber: String? = null,

    @Comment("청구구분 - dmdType")
    val claimType: String? = null,

    @Comment("청구일 - billSeqNo")
    val claimDate: String? = null,

    @Comment("청구건수 - totDmdCnt")
    val claimItemsCount: Long? = null,

    @Comment("지급불능건수 - paynCnt")
    val unablePayItemsCount: Long? = null,

    @Comment("진료구분 - diagTypeNm")
    val treatmentType: String? = null,

    @Comment("진료형태 - inPatOpatType")
    val treatmentForm: String? = null,

    @Comment("심사차수 - exmOrdcnt")
    val auditsRound: String? = null,

    @Comment("통보일자 - ntcDataSndDtime")
    val notificationDate: String? = null,

    @Comment("심사자 - exmNsprsnNm")
    val evaluator: String? = null,

    @Comment("연락처 - telNo")
    val contactNumber: String? = null,

    @Comment("묶음번호 - bndlNo")
    val bundleNumber: String? = null,

    @Comment("반송구분 - retnType")
    val returnType: String? = null,

    @Comment("반송일자 - retnDt")
    val returnDate: String? = null,

    @Comment("청구번호 - dmdNo")
    val claimNumber: String? = null,

    @Comment("진료비총액 (청구사항) - dmdTotTramt")
    val claimTotalAmount: Long? = null,

    @Comment("환자납부총액 (청구사항) - dmdPtntPymnTotAmt")
    val claimPatientPayment: Long? = null,

    @Comment("청구총액 (청구사항) - dmdJbrdnAmt")
    val claimAmount: Long? = null,

    @Comment("진료비총액 (심사결정) - edecTdamt")
    val decisionTotalAmount: Long? = null,

    @Comment("환자납부총액 (심사결정) - edecPtntPymnTotAmt")
    val decisionPatientPayment: Long? = null,

    @Comment("청구총액 (심사결정) - edecAmt")
    val decisionAmount: Long? = null,


    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),


    )


