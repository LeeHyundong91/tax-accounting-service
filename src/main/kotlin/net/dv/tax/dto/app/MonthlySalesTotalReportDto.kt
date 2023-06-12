package net.dv.tax.dto.app

import net.dv.tax.domain.consulting.SalesTypeItemEntity
import org.hibernate.annotations.Comment


data class MonthlyReportDto(

    val totalAmountData: MonthlySalesTotalReportDto? = null,
    val payMethodData: MonthlyPayMethodReportDto? = null,
    val salesTypeData: MonthlySalesTypeReportDto? = null,
)

data class MonthlySalesTotalReportDto(

    @Comment("당월")
    val currentMonthAmount: Long? = 0,

    @Comment("전월")
    val beforeMonthAmount: Long? = 0,

    @Comment("당월 - 전월")
    val compareMonthAmount: Long? = 0,

    @Comment("전월 / 당월")
    val compareMonthRatio: Float? = 0.0.toFloat(),

    @Comment("전년")
    val beforeYearMonthAmount: Long? = 0,

    @Comment("당월 - 전년 전월")
    val compareYearMonthAmount: Long? = 0,

    )

data class MonthlyPayMethodReportDto(

    val creditCardAmount: Long? = 0,
    val creditCardRatio: Float? = 0.0.toFloat(),

    val cashReceiptAmount: Long? = 0,
    val cashReceiptRatio: Float? = 0.0.toFloat(),

    val cashAmountAmount: Long? = 0,
    val cashRatio: Float? = 0.0.toFloat(),

    )

data class MonthlySalesTypeReportDto(

    val itemList: List<SalesTypeItemEntity>? = mutableListOf()

    )