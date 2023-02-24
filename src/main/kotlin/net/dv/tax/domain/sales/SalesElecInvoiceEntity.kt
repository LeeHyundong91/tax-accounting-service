package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "SALES_ELEC_INVOICE")
@Comment("전자계산서매출관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesElecInvoiceEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @JsonProperty("elecTaxInvoiceId")
    var receiveDataId: Long? = 0,

    @Comment("작성일시")
    @JsonProperty("createdDt")
    var dataPeriod: String? = null,

    @Comment("전송일시")
    @Column(name = "SEND_DT")
    var sendDt: String? = null,

    @Comment("발급일시")
    @Column(name = "ISSUE_DT")
    var issueDt: String? = null,

    @Comment("승인번호")
    @Column(name = "APPROVAL_NO", length = 10)
    var approvalNo: String? = null,

    @Comment("계선서종류")
    @Column(name = "BILL_TYPE", length = 10)
    var billType: String? = null,

    @Comment("발급유형")
    @Column(name = "ISSUE_TYPE", length = 10)
    var issueType: String? = null,

    @Comment("구분")
    @Column(name = "CHARGE_TYPE", length = 10)
    var chargeType: String? = null,

    @Comment("품목명")
    @Column(name = "ITEM_NAME", length = 30)
    var itemName: String? = null,

    @Comment("공급가액")
    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Long? = 0,

    @Comment("세액")
    @Column(name = "TAX_AMOUNT")
    var taxAmount: Long? = 0,

    @Comment("합계금액")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Long? = 0,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )

