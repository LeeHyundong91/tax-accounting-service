package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


@Comment("예상손익")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "expected_income")
@DynamicUpdate
data class ExpectedIncomeEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var consultingReportId: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    @Comment("목표소득률")
    var targetIncomeRatio: Float? = 0.0.toFloat(),

    @Comment("연부족액")
    var yearShortfall: Long? = 0,

    var writer: String? = null,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 100)
    @JoinColumn(name = "EXPECTED_INCOME_ID")
    var detailList: MutableList<ExpectedIncomeItemEntity> = mutableListOf(),


    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 100)
    @JoinColumn(name = "EXPECTED_INCOME_ID")
    var monthlyList: MutableList<ExpectedIncomeMonthlyItemEntity> = mutableListOf(),


    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )

@Comment("예상손익 항목")
@Entity
@Table(name = "expected_income_item")
@DynamicUpdate
data class ExpectedIncomeItemEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "EXPECTED_INCOME_ID")
    var expectedIncomeId: Long? = null,

    var category: String? = null,

    var itemName: String? = null,

    var itemValue: Long? = 0,

    var itemRatio: Float? = 0.0.toFloat(),


    )

@Comment("예상손익 개월별 항목")
@Entity
@Table(name = "expected_income_monthly_item")
@DynamicUpdate
data class ExpectedIncomeMonthlyItemEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "EXPECTED_INCOME_ID")
    var expectedIncomeId: Long? = null,

    var expectedMonth: String? = null,

    @Comment("매출")
    var sales: Long? = 0,

    @Comment("비용")
    var cost: Long? = 0,

    @Comment("예상매출")
    var expectedSales: Long? = 0,

    @Comment("예상비용")
    var expectedCost: Long? = 0,
)