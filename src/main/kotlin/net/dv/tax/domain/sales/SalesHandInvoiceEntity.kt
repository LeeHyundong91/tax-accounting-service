package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

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
    var hospitalId: Int,

    @Comment("계산서종류")
    @Column(name = "BILL_TYPE", length = 10)
    var billType: String? = null,

    @Comment("발급일자")
    @Column(name = "ISSUE_DT")
    var issueDt: LocalDate? = null,

    @Comment("품목명")
    @Column(name = "ITEM_NAME", length = 50)
    var itemName: String? = null,

    @Comment("공급가액")
    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Int? = null,

    @Comment("세액")
    @Column(name = "TAX_AMOUNT")
    var taxAmount: Int? = null,

    @Comment("등록일시")
    @Column(name = "CREATED_DT")
    var createdDt: LocalDate? = null,

    @Comment("등록자")
    @Column(name = "WRITER", length = 10)
    var writer: String? = null,

    @Comment("삭제")
    @Column(name = "IS_DELETE")
    var isDelete: Boolean? = false,

    )
