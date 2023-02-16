package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*


@Comment("신용카드매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "sales_credit_card")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesCreditCardEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JsonProperty("salesCardDetailId")
    val receiveDataId: Long,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("승인년월")
    @JsonProperty("approvalYearMonth")
    val dataPeriod: String? = null,

    @Comment("자료구분")
    val cardCategory: String? = null,

    @Comment("건수 - crdcstlScnt")
    val salesCount: Long? = 0,

    @Comment("매출합계")
    val totalSales: Long? = 0,

    @Comment("신용카드 결재")
    val creditCardSalesAmount: Long? = 0,

    @Comment("구매전용카드 결재")
    val purchaseCardSalesAmount: Long? = 0,

    @Comment("비과세 금액")
    val taxFreeAmount: Long? = 0,

    )
