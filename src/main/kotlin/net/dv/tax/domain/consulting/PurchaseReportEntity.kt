package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("컨설팅 매입관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "purchase_report")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class PurchaseReportEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var hospitalId: String? = null,

    var consultingReportId: Long? = null,

    var resultYearMonth: String? = null,

    @Comment("매입합계")
    val purchaseTotalAmount: Long? = 0,

    @Comment("매출원가")
    val costOfSalesTotal: Long? = 0,

    @Comment("판관비 합계")
    val sgaExpensesTotal: Long? = 0,

    @Comment("영업외 합계")
    val nonOperatingIncomeTotal: Long? = 0,

    var writer: String? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),


    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 30)
    @JoinColumn(name = "PURCHASE_REPORT_ID")
    var detailList: MutableList<PurchaseReportItemEntity> = mutableListOf(),

    )

@Comment("컨설팅 매입 항목")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "purchase_report_item")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class PurchaseReportItemEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "PURCHASE_REPORT_ID")
    var purchaseReportId: Long? = null,

    var category: String? = null,

    var itemName: String? = null,

    var valueAmount: Long? = 0,

    var valueRatio: Float? = 0.0.toFloat(),

    var memo: String? = null,

    )



