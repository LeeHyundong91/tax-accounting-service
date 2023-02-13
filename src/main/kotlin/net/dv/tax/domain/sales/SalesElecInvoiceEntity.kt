package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@Table(name = "sales_elec_invoice")
@Comment("전자계산서매출관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesElecInvoiceEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("작성일시")
    @Column(name = "CREATED_DT")
    var createdDt: LocalDate? = null,

    @Comment("전송일시")
    @Column(name = "SEND_DT")
    var sendDt: LocalDate? = null,

    @Comment("발급일시")
    @Column(name = "ISSUE_DT")
    var issueDt: LocalDate? = null,

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
    var supplyPrice: Int? = null,

    @Comment("세액")
    @Column(name = "TAX_AMOUNT")
    var taxAmount: Int? = null,

    @Comment("합계금액")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Int? = null,

    @Comment("부가세")
    @Column(name = "VAT")
    var vat: Int? = null,
)

