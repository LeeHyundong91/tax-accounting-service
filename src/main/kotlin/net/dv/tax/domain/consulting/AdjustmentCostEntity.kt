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

@Comment("결산조정비용")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "ADJUSTMENT_COST")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class AdjustmentCostEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "CONSULTING_REPORT_ID")
    var consultingReportId: Long? = null,

    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Column(name = "RESULT_YEAR_MONTH")
    var resultYearMonth: String? = null,

    @Column(name = "WRITER")
    var writer: String? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    @JoinColumn(name = "ADJUSTMENT_COST_ID")
    var detailList: MutableList<AdjustmentCostItemEntity> = mutableListOf(),
)

@Comment("결산조정비용 항목")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "ADJUSTMENT_COST_ITEM")
@DynamicUpdate
data class AdjustmentCostItemEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "ADJUSTMENT_COST_ID")
    var adjustmentCostId: Long? = null,

    @Comment("분류 (추가비용, 소득조정)")
    @Column(name = "CATEGORY")
    var category: String? = null,

    @Comment("항목")
    @Column(name = "ITEM_NAME")
    var itemName: String? = null,

    @Comment("금액")
    @Column(name = "ITEM_VALUE")
    var itemValue: Long? = 0,

    @Comment("FINAL / MONTH / PERIOD")
    @Column(name = "DISTRIBUTION")
    var distribution: String? = null,

    @Column(name = "START_DATE")
    var startDate: String? = null,

    @Column(name = "END_DATE")
    var endDate: String? = null,

    @Column(name = "MEMO")
    var memo: String? = null,
)