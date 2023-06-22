package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("연환산 손익계산서")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "income_statement")
@DynamicUpdate
data class IncomeStatementEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var consultingReportId: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    @Comment("환산율")
    var exchangeRate: Float? = 0.0.toFloat(),

    val yearCount: Int? = 12,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 100)
    @JoinColumn(name = "INCOME_STATEMENT_ID")
    var detailList: MutableList<IncomeStatementItemEntity> = mutableListOf(),


    )

@Comment("연환산 손익계산서 항목")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "income_statement_item")
@DynamicUpdate
data class IncomeStatementItemEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "INCOME_STATEMENT_ID")
    var incomeStatementId: Long? = null,

    @Comment("과목 - 매출액, 매출원가..")
    var category: String? = null,

    @Comment("과목명")
    var itemName: String? = null,

    @Comment("현재 기간별 금액")
    var currentValueAmount: Long? = null,

    @Comment("현재 기간별 비율")
    var currentValueRatio: Float? = 0.0.toFloat(),

    @Comment("연환산 금액")
    var yearValueAmount: Long? = null,

    @Comment("연환산 금액")
    var yearValueRatio: Float? = 0.0.toFloat(),


    )