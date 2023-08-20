package net.dv.tax.app.statistics

import net.dv.tax.app.statistics.types.SalesAggregation
import org.springframework.stereotype.Repository


@Repository
interface StatisticsRepository {
    fun monthlySalesStatistics(hospitalId: String, month: String): SalesAggregation
    fun annualSalesStatistics(hospitalId: String, year: String): SalesAggregation
}