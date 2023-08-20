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

@Comment("매출 누계 매출유형별 현황")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "SALES_TYPE")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesTypeEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("결산 년월")
    @Column(name = "RESULT_YEAR_MONTH")
    var resultYearMonth: String? = null,

    @Column(name = "CONSULTING_REPORT_ID")
    val consultingReportId: Long? = null,

    @Comment("합계액")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Long? = null,

    @Comment("합계 비율")
    @Column(name = "TOTAL_RATIO")
    var totalRatio: Float? = null,

    @Comment("일반매출")
    @Column(name = "NORMAL_SALES_AMOUNT")
    var normalSalesAmount: Long? = 0,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    @JoinColumn(name = "SALES_TYPE_ID")
    var detailList: MutableList<SalesTypeItemEntity>? = mutableListOf(),

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

@Comment("매출 누계 매출유형별 현황 아이템")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "SALES_TYPE_ITEM")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesTypeItemEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "SALES_TYPE_ID")
    var salesTypeId: Long? = null,

    @Comment("구분 - 청구매출, 요양급여, 의료급여 ...")
    @Column(name = "ITEM_NAME")
    var itemName: String? = null,

    @Comment("본인부담금")
    @Column(name = "ITEM_OWN_CHARGE_AMOUNT")
    var itemOwnChargeAmount: Long? = 0,

    @Comment("본인부담 비률")
    @Column(name = "ITEM_OWN_CHARGE_RATIO")
    var itemOwnChargeRatio: Float? = 0.0.toFloat(),

    @Comment("공단부담금")
    @Column(name = "ITEM_CORP_CHARGE_AMOUNT")
    var itemCorpChargeAmount: Long? = 0,

    @Comment("공단부담금 비율")
    @Column(name = "ITEM_CORP_CHARGE_RATIO")
    var itemCorpChargeRatio: Float? = 0.0.toFloat(),

    @Comment("소계")
    @Column(name = "GROUP_AMOUNT")
    var groupAmount: Long? = 0,

    @Comment("소계 비율")
    @Column(name = "GROUP_RATIO")
    var groupRatio: Float? = 0.0.toFloat(),
)
