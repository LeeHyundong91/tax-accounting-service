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
@Table(name = "sales_elec_invoice")
@Comment("전자계산서매출관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesElecInvoiceEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Comment("병원 아이디")
    var hospitalId: String,

    @JsonProperty("elecTaxInvoiceId")
    var receiveDataId: Long? = 0,

    @Comment("작성일시")
    @JsonProperty("createdDt")
    var dataPeriod: String? = null,

    @Comment("전송일시")
    var sendDt: String? = null,

    @Comment("발급일시")
    var issueDt: String? = null,

    @Comment("승인번호")
    var approvalNo: String? = null,

    @Comment("계선서종류")
    var billType: String? = null,

    @Comment("발급유형")
    var issueType: String? = null,

    @Comment("구분")
    var chargeType: String? = null,

    @Comment("품목명")
    var itemName: String? = null,

    @Comment("공급가액")
    var supplyPrice: Long? = 0,

    @Comment("세액")
    var taxAmount: Long? = 0,

    @Comment("전제 세금계산서 판별여부 true : 전자세금계산서 , false : 전자계산서")
    val isTax: Boolean? = null,

    @Comment("합계금액")
    var totalAmount: Long? = 0,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )

