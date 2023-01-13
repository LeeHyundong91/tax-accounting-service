package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@Comment("현금영수증매출관리")
@Suppress("JpaAttributeTypeInspection")
@Table(name = "SALES_CASH_RECEIPT")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesCashReceiptEntity(
    @Id
    @Column(name = "ID", nullable = false)
    var id: Long? = null,

    @Comment("자료구분")
    @Column(name = "DATA_TYPE", length = 10)
    var dataType: String? = null,

    @Comment("매출일시")
    @Column(name = "SALES_DATE")
    var salesDate: LocalDate? = null,

    @Comment("현금결제번호")
    @Column(name = "CASH_PAYMENT_NO", length = 10)
    var cashPaymentNo: String? = null,

    @Comment("합계")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Long? = null,

    @Comment("공급가액")
    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Long? = null,

    @Comment("세액")
    @Column(name = "VAT")
    var vat: Long? = null,

    @Comment("봉사료")
    @Column(name = "SERVICE_CHARGE")
    var serviceCharge: Long? = null,

    @Comment("발급수단")
    @Column(name = "ISSUE_METHOD", length = 10)
    var issueMethod: String? = null,

    @Comment("거래구분")
    @Column(name = "DEAL_TYPE")
    var dealType: Long? = null,

    @Comment("비고")
    @Column(name = "RECEIPT_NOTE")
    var receiptNote: Long? = null,
)
