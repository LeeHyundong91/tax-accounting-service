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

    @Column(name = "RECEIVE_DATA_ID")
    val receiveDataId: Long,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("처리상태 - retStatus")
    @Column(name = "PROCESSING_STATUS")
    val processingStatus: String? = null,

    @Comment("접수일자 - recvDt")
    @Column(name = "RECEIVING_DATE")
    val receivingDate: String? = null,

    @Comment("진료년월 - diagYyyymm")
    @Column(name = "TREATMENT_YEAR_MONTH")
    val treatmentYearMonth: String? = null,

    @Comment("접수번호 - recvNo")
    @Column(name = "RECEIVING_NUMBER")
    val receivingNumber: String? = null,

    @Comment("청구구분 - dmdType")
    @Column(name = "CLAIM_TYPE")
    val claimType: String? = null,

    @Comment("청구일 - billSeqNo")
    @Column(name = "CLAIM_DATE")
    val claimDate: String? = null,

    @Comment("청구건수 - totDmdCnt")
    @Column(name = "CLAIM_ITEM_COUNT")
    val claimItemsCount: Long? = null,

    @Comment("지급불능건수 - paynCnt")
    @Column(name = "UNABLE_PAY_ITEMS_COUNT")
    val unablePayItemsCount: Long? = null,

    @Comment("진료구분 - diagTypeNm")
    @Column(name = "TREATMENT_TYPE")
    val treatmentType: String? = null,

    @Comment("진료형태 - inPatOpatType")
    @Column(name = "TREATMENT_FORM")
    val treatmentForm: String? = null,

    @Comment("심사차수 - exmOrdcnt")
    @Column(name = "AUDITS_ROUND")
    val auditsRound: String? = null,

    @Comment("통보일자 - ntcDataSndDtime")
    @Column(name = "NOTIFICATION_DATE")
    val notificationDate: String? = null,

    @Comment("심사자 - exmNsprsnNm")
    @Column(name = "EVALUATOR")
    val evaluator: String? = null,

    @Comment("연락처 - telNo")
    @Column(name = "CONTACT_NUMBER")
    val contactNumber: String? = null,

    @Comment("묶음번호 - bndlNo")
    @Column(name = "BUNDLE_NUMBER")
    val bundleNumber: String? = null,

    @Comment("반송구분 - retnType")
    @Column(name = "RETURN_TYPE")
    val returnType: String? = null,

    @Comment("반송일자 - retnDt")
    @Column(name = "RETURN_DATE")
    val returnDate: String? = null,

    @Comment("청구번호 - dmdNo")
    @Column(name = "CLAIM_NUMBER")
    val claimNumber: String? = null,

    @Comment("진료비총액 (청구사항) - dmdTotTramt")
    @Column(name = "CLAIM_TOTAL_AMOUNT")
    val claimTotalAmount: Long? = null,

    @Comment("환자납부총액 (청구사항) - dmdPtntPymnTotAmt")
    @Column(name = "CLAIM_PATENT_PAYMENT")
    val claimPatientPayment: Long? = null,

    @Comment("청구총액 (청구사항) - dmdJbrdnAmt")
    @Column(name = "CLAIM_AMOUNT")
    val claimAmount: Long? = null,

    /*자동차 보험 해당 항목 사용*/
    @Comment("진료비총액 (심사결정) - edecTdamt")
    @Column(name = "DECISION_TOTAL_AMOUNT")
    val decisionTotalAmount: Long? = null,

    @Comment("환자납부총액 (심사결정) - edecPtntPymnTotAmt")
    @Column(name = "DECISION_PATIENT_PAYMENT")
    val decisionPatientPayment: Long? = null,

    @Comment("청구총액 (심사결정) - edecAmt")
    @Column(name = "DECISION_AMOUNT")
    val decisionAmount: Long? = null,


    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)


