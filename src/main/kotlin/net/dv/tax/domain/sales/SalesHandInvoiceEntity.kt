package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "SALES_HAND_INVOICE")
@Comment("수기세금계산서매출관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesHandInvoiceEntity(


    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("거래처 명")
    @Column(name = "CLIENT_NAME")
    var clientName : String? = null,

    @Comment("발급일자")
    @Column(name = "ISSUE_DATE")
    var issueDate: String? = null,

    @Comment("품목명")
    @Column(name = "ITEM_NAME")
    var itemName: String? = null,

    @Comment("공급가액")
    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Long? = null,

    @Comment("세액")
    @Column(name = "TAX_AMOUNT")
    var taxAmount: Long? = null,

    @Comment("합계금액")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Long? = null,

    @Comment("계산서종류")
    @Column(name = "BILL_TYPE")
    var billType: String? = null,

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment("등록일")
    @Column(updatable = false, name = "CREATED_AT")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Comment("등록자")
    @Column(name = "WRITER")
    var writer: String? = null,

    @Comment("삭제")
    @Column(name = "IS_DELETE")
    var isDelete: Boolean? = false,

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "DELETED_AT")
    var deletedAt: LocalDateTime = LocalDateTime.now(),
)
