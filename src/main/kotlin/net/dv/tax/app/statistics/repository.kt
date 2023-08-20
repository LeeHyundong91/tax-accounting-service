package net.dv.tax.app.statistics

import net.dv.tax.app.statistics.types.PurchaseAggregation
import net.dv.tax.app.statistics.types.PurchaseStatistics
import net.dv.tax.app.statistics.types.SalesAggregation
import org.springframework.stereotype.Repository


@Repository
interface StatisticsRepository {
    fun monthlySalesStatistics(hospitalId: String, month: String): SalesAggregation
    fun annualSalesStatistics(hospitalId: String, year: String): SalesAggregation
    fun purchaseStatistics(hospitalId: String, period: String): List<PurchaseAggregation>
}