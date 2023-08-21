package net.dv.tax.app.statistics

import net.dv.tax.app.AccountingItemCategory
import net.dv.tax.app.statistics.types.*
import org.springframework.stereotype.Service
import java.time.Year
import java.time.YearMonth


@Service
class StatisticsService(
    private val repository: StatisticsRepository
): StatisticsCommand {

    private fun stat(amount:Long, total:Long): StatValue = object: StatValue {
        override val amount: Long = amount
        override val ratio: Double = (amount.toDouble() / total.toDouble()) * 100
    }

    override fun salesStatistics(criteria: Criteria): SalesStatistics {
        val hospitalId = criteria.hospitalId
        val term = criteria.term
        val date = criteria.date

        val (current, before) = when(term!!) {
            Criteria.Term.ANNUAL -> {
                val c = Year.parse(date!!)
                arrayOf(
                    repository.annualSalesStatistics(hospitalId, c.toString()),
                    repository.annualSalesStatistics(hospitalId, c.minusYears(1).toString())
                )
            }
            Criteria.Term.MONTHLY -> {
                val c = YearMonth.parse(date!!)
                arrayOf(
                    repository.monthlySalesStatistics(hospitalId, c.toString()),
                    repository.monthlySalesStatistics(hospitalId, c.minusMonths(1).toString())
                )
            }
        }

        val currentAmount = aggregateSummary(current)
        val beforeAmount = aggregateSummary(before)

        val summary = object: SalesAmount {
            override val currentAmount: Long = currentAmount
            override val beforeAmount: Long = beforeAmount
            override val compareAmount: Long = currentAmount - beforeAmount
            override val compareRatio: Double = (currentAmount.toDouble() / beforeAmount.toDouble()) * 100
        }


        return object: SalesStatistics {
            override val summary: SalesAmount = summary
            override val byPayments: SalesPayment = aggregatePayments(currentAmount, current)
            override val byCategories: SalesCategory = aggregateCategories(currentAmount, current)
        }
    }

    private fun aggregateSummary(target: SalesAggregation): Long {
        val creditCard = target.creditCard ?: 0
        val cashReceipt = target.cashReceipt ?: 0
        val salesAgent = target.salesAgent ?: 0
        val netCash = target.netCash ?: 0
        return creditCard + cashReceipt + salesAgent + netCash
    }

    private fun aggregatePayments(total:Long, target: SalesAggregation): SalesPayment {
        return object: SalesPayment {
            override val creditCard: StatValue = stat(target.creditCard ?: 0, total)
            override val cashReceipt: StatValue = stat(target.cashReceipt ?: 0, total)
            override val netCash: StatValue = stat(target.salesAgent ?: 0, total)
            override val salesAgent: StatValue = stat(target.netCash ?: 0, total)
            val total: Long = total
        }
    }

    private fun aggregateCategories(total: Long, target: SalesAggregation): SalesCategory {
        val medicalB = target.medicalBenefits ?: 0
        val medicalC = target.medicalCareBenefits ?: 0
        val industry = target.industry ?: 0
        val car = target.carInsurance ?: 0
        val health = target.healthCare ?: 0
        val vaccine = target.vaccine ?: 0
        val others = target.others ?: 0

        val nonCovered = total - (medicalB + medicalC + industry + car + health + vaccine + others)

        return object: SalesCategory {
            override val medicalCareBenefits: StatValue = stat(target.medicalCareBenefits ?: 0, total)
            override val medicalBenefits: StatValue = stat(target.medicalBenefits ?: 0, total)
            override val carInsurance: StatValue = stat(target.carInsurance ?: 0, total)
            override val healthCheck: StatValue = stat(target.healthCare ?: 0, total)
            override val others: StatValue = stat(industry + vaccine + others, total)
            override val nonCovered: StatValue = stat(nonCovered, total)
            val total: Long = total
        }
    }

    override fun purchaseStatistics(criteria: Criteria): PurchaseStatistics {
        val hospitalId = criteria.hospitalId
        val term = criteria.term
        val period = criteria.date

        val (cDate, bDate) = when(term!!) {
            Criteria.Term.ANNUAL -> {
                val year = Year.parse(period!!)
                Pair(
                    year.toString(),
                    year.minusYears(1).toString()
                )
            }
            Criteria.Term.MONTHLY -> {
                val yearMonth = YearMonth.parse(period!!)
                Pair(
                    yearMonth.toString(),
                    yearMonth.minusMonths(1).toString()
                )
            }
        }

        println("--> data : $cDate  /  $bDate")

        val current = repository.purchaseStatistics(hospitalId, cDate)
        val before = repository.purchaseStatistics(hospitalId, bDate)

        val currentAmount = current.sumOf { it.amount }
        val beforeAmount = before.sumOf { it.amount }

        val summary = when(term) {
            Criteria.Term.ANNUAL -> purchaseSummaryByYear(currentAmount, beforeAmount)
            Criteria.Term.MONTHLY -> {
                val beforeYearAmount = repository.purchaseStatistics(
                    hospitalId, YearMonth.parse(period).minusYears(1).toString()
                ).sumOf { it.amount }
                purchaseSummaryByMonth(currentAmount, beforeAmount, beforeYearAmount)
            }
        }

        val aggregation: Map<String, PurchaseAggregation> = current
            .groupBy { it.category }
            .mapValues {(k, v)->
                val category = AccountingItemCategory.valueOf(k)
                object: PurchaseAggregation {
                    override val hospitalId: String = hospitalId
                    override val period: String = period
                    override val debitAccount: String = category.label
                    override val category: String = category.name
                    override val amount: Long = v.sumOf { it.amount }
                    override val ratio: Double = v.sumOf { it.amount }.toDouble() / currentAmount * 100
                    var meanRatio: Double = 0.0
                }
            }

        return object: PurchaseStatistics {
            override val summary: PurchaseAmount = summary
            override val byCategories: List<PurchaseAggregation> = aggregation.values.toList()
        }
    }

    private fun purchaseSummaryByYear(current: Long, before: Long): PurchaseAmount {
        return object: PurchaseAmount {
            override val currentAmount: Long = current
            override val beforeAmount: Long = before
            override val compareAmount: Long = current - before
            override val compareRatio: Double = current.toDouble() / before.toDouble() * 100
        }
    }

    private fun purchaseSummaryByMonth(current: Long, before: Long, beforeYear: Long): PurchaseAmount {
        return object: PurchaseAmount {
            override val currentAmount: Long = current
            override val beforeAmount: Long = before
            override val compareAmount: Long = current - before
            override val compareRatio: Double = current.toDouble() / before.toDouble() * 100
            val beforeYear: Long = beforeYear
            val compareBeforeYear: Long = current - beforeYear
        }
    }
}