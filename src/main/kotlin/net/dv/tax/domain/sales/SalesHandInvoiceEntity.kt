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
@Table(name = "sales_hand_invoice")
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
    var hospitalId: String,

    @Comment("계산서종류")
    var billType: String? = null,

    @Comment("발급일자")
    var issueDt: String? = null,

    @Comment("품목명")
    var itemName: String? = null,

    @Comment("공급가액")
    var supplyPrice: Long? = null,

    @Comment("세액")
    var taxAmount: Long? = null,

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment("등록일")
    @Column(updatable = false, name = "CREATED_AT")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Comment("등록자")
    var writer: String? = null,

    @Comment("삭제")
    var isDelete: Boolean? = false,

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var deletedAt: LocalDateTime = LocalDateTime.now(),
)
