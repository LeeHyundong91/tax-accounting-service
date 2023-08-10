package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Comment("현금영수증매출관리")
@Suppress("JpaAttributeTypeInspection")
@Table(name = "SALES_CASH_RECEIPT")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesCashReceiptEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Column(name = "RECEIVE_DATA_ID")
    var receiveDataId: Long,

    @Comment("발행구분 - pblClCd")
    @Column(name = "ISSUE_TYPE")
    val issueType: String? = null,

    @Comment("매출일시 - trsDtm")
    @Column(name = "SALES_DATE")
    val salesDate: String? = null,

    @Comment("공급가액 - splCft")
    @Column(name = "SUPPLY_AMOUNT")
    val supplyAmount: Long? = null,

    @Comment("부가세 - vaTxamt")
    @Column(name = "TAX_AMOUNT")
    val taxAmount: Long? = null,

    @Comment("봉사료 - tip")
    @Column(name = "SERVICE_FEE")
    val serviceFee: Long? = null,

    @Comment("총금액 - totaTrsAmt")
    @Column(name = "TOTAL_AMOUNT")
    val totalAmount: Long? = null,

    @Comment("승인번호 - aprvNo")
    @Column(name = "APPROVAL_NUMBER")
    val approvalNumber: String? = null,

    @Comment("신분확인뒷4자리 - spstCnfrPartNo")
    @Column(name = "VERIFICATION_NUMBER")
    val verificationNumber: String? = null,

    @Comment("거래구분 - trsClNm")
    @Column(name = "TRANSACTION_TYPE")
    val transactionType: String? = null,


    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)