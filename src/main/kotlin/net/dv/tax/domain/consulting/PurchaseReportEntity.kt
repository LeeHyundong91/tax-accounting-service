package net.dv.tax.domain.consulting

import jakarta.persistence.Column
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


data class PurchaseReportEntity (
    val id: Long? = null,

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



    )

data class PurchaseReportItem(
    val id: Long? = null,

    val title: String? = null,

    val category: String? = null,

    val valueAmount: Long? = 0,

    val valueRatio: Float? = 0.0.toFloat(),

    val memo: String? = null
)