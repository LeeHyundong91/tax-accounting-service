package net.dv.tax.app.statistics

import net.dv.tax.app.statistics.types.*
import org.springframework.stereotype.Service
import java.math.BigDecimal
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
}