package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

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
    var hospitalId: String,

    @JsonProperty("cashReceiptId")
    var receiveDataId: Long,

    @Comment("매출일시")
    @JsonProperty("salesDate")
    var dataPeriod: String? = null,

    @Comment("승인번호")
    var cashPaymentNo: String? = null,

    @Comment("합계")
    var totalAmount: Long? = null,

    @Comment("공급가액")
    var supplyPrice: Long? = null,

    @Comment("부가세")
    var vat: Long? = null,

    @Comment("봉사료")
    var serviceCharge: Long? = null,

    @Comment("발행구분")
    var issueMethod: String? = null,

    @Comment("거래구분")
    var dealType: String? = null,

    )
