package net.dv.tax.app.statistics

import net.dv.tax.app.statistics.types.Criteria
import net.dv.tax.app.statistics.types.SalesStatistics


interface StatisticsCommand {
    fun salesStatistics(criteria: Criteria): SalesStatistics

    fun salesStatistics(hospitalId: String, options: CriteriaDto.() -> Unit): SalesStatistics =
        CriteriaDto(hospitalId).apply(options).run { salesStatistics(this) }

    data class CriteriaDto(
        override val hospitalId: String,
        override var term: Criteria.Term? = null,
        var year: String? = null,
        var yearMonth: String? = null,
    ) : Criteria {
        override val date: String? get() = term?.let {
            when(it) {
                Criteria.Term.ANNUAL -> year
                Criteria.Term.MONTHLY -> yearMonth
            }
        }
    }
}