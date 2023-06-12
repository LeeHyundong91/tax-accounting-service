package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("과/면세비율 메인")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "tax_exemption")
@DynamicUpdate
data class TaxExemptionEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var consultingReportId: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    @Comment("총합계")
    var totalAmount: Long? = 0,

    @Comment("총합계 비율")
    var totalAmountRatio: Float? = 0.0.toFloat(),

    @Comment("과세합계")
    var taxAmount: Long? = 0,

    @Comment("과세합계 비율")
    var taxAmountRatio: Float? = 0.0.toFloat(),

    @Comment("면세합계")
    var exemptionAmount: Long? = 0,

    @Comment("면세합계 비율")
    var exemptionAmountRatio: Float? = 0.0.toFloat(),

    @Comment("비급여중 과세비율")
    var taxRatio: Float? = 0.0.toFloat(),

    @Comment("작성자")
    var writer: String? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true,fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    @JoinColumn(name = "TAX_EXEMPTION_ID")
    var detailList: MutableList<TaxExemptionItemEntity>? = mutableListOf(),

    )

@Comment("과/면세비율 항목")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "tax_exemption_item")
@DynamicUpdate
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