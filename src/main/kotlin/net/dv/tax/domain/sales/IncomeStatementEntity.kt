package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "income_statement")
@Comment("손익계산서")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class IncomeStatementEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,


    @Comment("매출액")
    val salesAmount: Long? = 0,


    @Comment("보험매출")
    val insuranceSales: Long? = 0,


    @Comment("일반매출")
    val generalSales: Long? = 0,


    @Comment("매출원가")
    val salesCost: Long? = 0,


    @Comment("매출총이익")
    val grossProfit: Long? = 0,


    @Comment("판관비")
    val sgaExpenses: Long? = 0,


    @Comment("인건비")
    val personnelExpenses: Long? = 0,


    @Comment("감가비")
    val depreciationExpense: Long? = 0,


    @Comment("임차료")
    val rentalCost: Long? = 0,


    @Comment("기타비용")
    val otherCosts: Long? = 0,


    @Comment("기타판관비")
    val otherSgaExpenses: Long? = 0,


    @Comment("영업이익")
    val operatingProfit: Long? = 0,


    @Comment("영업외수익")
    val nonOperatingIncome: Long? = 0,


    @Comment("영업외비용")
    val nonOperatingExpenses: Long? = 0,


    @Comment("당기순이익")
    val netIncome: Long? = 0,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )

