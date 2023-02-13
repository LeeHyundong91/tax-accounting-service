package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@Comment("현금영수증매출관리")
@Suppress("JpaAttributeTypeInspection")
@Table(name = "sales_cash_receipt")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesCashReceiptEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("자료구분")
    @Column(name = "DATA_TYPE", length = 10)
    var dataType: String? = null,

    @Comment("매출일시")
    @Column(name = "SALES_DATE")
    var salesDate: LocalDate? = null,

    @Comment("승인번호")
    @Column(name = "CASH_PAYMENT_NO", length = 10)
    var cashPaymentNo: String? = null,

    @Comment("합계")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Long? = null,

    @Comment("공급가액")
    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Long? = null,

    @Comment("부가세")
    @Column(name = "VAT")
    var vat: Long? = null,

    @Comment("봉사료")
    @Column(name = "SERVICE_CHARGE")
    var serviceCharge: Long? = null,

    @Comment("거래구분")
    @Column(name = "DEAL_TYPE")
    var dealType: Long? = null,

)
