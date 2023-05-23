package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Comment("과/면세비율 항목")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "tax_exemption_item")
data class TaxExemptionItemEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "TAX_EXEMPTION_ID")
    var taxExemptionId: Long? = null,

    @Comment("분류 (카드, 현금영수증, 현금)")
    var category: String? = null,

    @Comment("과/면세 항목")
    var itemName: String? = null,

    @Comment("공급대가 (SMALL_TOTAL: 소계, TAX: 과세, TAX_FREE: 면세, COMPLEX: 공단)")
    var costOfSupply: Long? = 0,

    @Comment("공급대가 비율")
    var costOfSupplyRatio: Float? = 0.0.toFloat(),

    @Comment("공급가액")
    var supplyValue: Long? = 0,

    @Comment("공급가액 비율")
    var supplyValueRatio: Float? = 0.0.toFloat(),


    )
