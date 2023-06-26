package net.dv.tax.service.consulting

import net.dv.tax.domain.consulting.IncomeStatementEntity
import net.dv.tax.domain.consulting.IncomeStatementItemEntity
import net.dv.tax.enums.consulting.IncomeStatementCategory
import net.dv.tax.enums.consulting.IncomeStatementItem
import net.dv.tax.enums.consulting.PurchaseCategory
import net.dv.tax.enums.consulting.PurchaseTitleItem
import net.dv.tax.repository.consulting.IncomeStatementItemRepository
import net.dv.tax.repository.consulting.IncomeStatementRepository
import net.dv.tax.repository.consulting.PurchaseReportRepository
import net.dv.tax.repository.consulting.SalesTypeRepository
import org.springframework.stereotype.Service
import java.time.LocalDate


//매출총이익 = 총매출액 - 매출원가
//영업이익 = 매출총액 - 매출원가 - 판관비
//소득세 차감전 이익 = 영업이익 + 영업외 수익
//당기순이익 = 총매출 - 영업비용, 판관비, 매출원과

@Service
class IncomeStatementService(
    private val incomeStatementRepository: IncomeStatementRepository,
    private val incomeStatementItemRepository: IncomeStatementItemRepository,
    private val salesTypeRepository: SalesTypeRepository,
    private val purchaseReportRepository: PurchaseReportRepository,
) {

    fun getData(hospitalId: String, year: String): IncomeStatementEntity {
        return incomeStatementRepository.findTopByHospitalIdAndResultYearMonthStartingWith(
            hospitalId,
            year
        ) ?: IncomeStatementEntity()
    }

    fun saveData(hospitalId: String, year: String) {
        incomeStatementRepository.save(makeData(hospitalId, year))
    }

    fun makeData(hospitalId: String, year: String): IncomeStatementEntity {


        val defaultCondition = IncomeStatementEntity()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        val data =
            incomeStatementRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
                ?: incomeStatementRepository.save(defaultCondition)

        data.also {

            val dataList = mutableListOf<IncomeStatementItemEntity>()

            val salesTypeItem =
                salesTypeRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)

            val purchaseItemList =
                purchaseReportRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)?.detailList

            /*매출총액 비율*/
            val salesTotalRatio = 100.0.toFloat()

            /*매출액 하위*/
            salesTypeItem?.detailList?.forEach { item ->
                val data = setItem(IncomeStatementCategory.SALES_REVENUE.code, item.itemName!!, item.groupAmount ?: 0)
                dataList.add(data)
            }
            /*매출액 타이틀*/
            val salesRevenueTotal = setItem(
                IncomeStatementCategory.SALES_REVENUE.code,
                IncomeStatementItem.SALES_REVENUE_TOTAL.value,
                salesTypeItem?.totalAmount ?: 0
            ).apply {
                currentValueRatio = salesTotalRatio
            }
            dataList.add(salesRevenueTotal)

            /*매출총액*/
            val salesTotalAmount = salesRevenueTotal.currentValueAmount ?: 0

            /*매출원가 타이틀*/
            var costOfGoodsSoldTotal = IncomeStatementItemEntity()

            /*매출원가 하위*/
            purchaseItemList?.filter { item -> item.category == PurchaseCategory.SALES_OF_COST.code }
                ?.forEach { item ->
                    var data = IncomeStatementItemEntity()
                    if (item.itemName == PurchaseTitleItem.COST_OF_SALES.value) {
                        costOfGoodsSoldTotal =
                            setItem(
                                IncomeStatementCategory.COST_OF_GOODS_SOLD.code,
                                IncomeStatementItem.COST_OF_GOODS_SOLD_TOTAL.value,
                                item.valueAmount ?: 0
                            ).apply {
                                currentValueRatio = item.valueAmount!!.toFloat().div(salesTotalAmount) * 100
                            }
                    } else {
                        data =
                            setItem(
                                IncomeStatementCategory.COST_OF_GOODS_SOLD.code,
                                item.itemName!!,
                                item.valueAmount ?: 0
                            )
                    }
                    dataList.add(data)
                }
            dataList.add(costOfGoodsSoldTotal)

            /*매출원가 금액*/
            val costOfGoodsSoldTotalAmount = costOfGoodsSoldTotal.currentValueAmount ?: 0


            /*매출총액 - 매출원가*/
            val grossProfitCurrentValue =
                salesTotalAmount.minus(costOfGoodsSoldTotalAmount)

            /*매출총이익*/
            val grossProfit = setItem(
                IncomeStatementCategory.GROSS_PROFIT.code,
                IncomeStatementItem.GROSS_PROFIT_TOTAL.value,
                grossProfitCurrentValue
            ).apply {
                currentValueRatio = grossProfitCurrentValue.toFloat().div(salesTotalAmount) * 100
            }
            dataList.add(grossProfit)

            /*판관비 타이틀*/
            var sgaExpenseTotal = IncomeStatementItemEntity()

            /*판관비 하위*/
            purchaseItemList?.filter { item -> item.category == PurchaseCategory.SGA_EXPENSE.code }
                ?.forEach { item ->
                    var data = IncomeStatementItemEntity()
                    if (item.itemName == PurchaseTitleItem.SGA_EXPENSE_TOTAL.value) {
                        sgaExpenseTotal =
                            setItem(
                                IncomeStatementCategory.SGA_EXPENSES.code,
                                IncomeStatementItem.SGA_EXPENSES_TOTAL.value,
                                item.valueAmount ?: 0
                            ).apply {
                                currentValueRatio = item.valueAmount!!.toFloat().div(salesTotalAmount) * 100
                            }
                    } else {
                        data =
                            setItem(IncomeStatementCategory.SGA_EXPENSES.code, item.itemName!!, item.valueAmount ?: 0)
                    }
                    dataList.add(data)
                }
            dataList.add(sgaExpenseTotal)

            /*판관비 합계액*/
            val sgaExpenseTotalAmount = sgaExpenseTotal.currentValueAmount ?: 0

            /*영업이익 금액*/
            val operatingProfitAmount = grossProfitCurrentValue.minus(sgaExpenseTotal.currentValueAmount ?: 0)

            /*영업이익*/
            dataList.add(
                setItem(
                    IncomeStatementCategory.OPERATING_PROFIT.code,
                    IncomeStatementItem.OPERATING_PROFIT_TOTAL.value,
                    /*영업이익 - 판관비*/
                    operatingProfitAmount
                ).apply {
                    currentValueRatio = operatingProfitAmount.toFloat().div(salesTotalAmount) * 100
                }
            )

            /*영업외 수익 타이틀*/
            var nonOperatingRevenueTotal = IncomeStatementItemEntity()

            /*영업외 수익 목록*/
            purchaseItemList?.filter { item -> item.category == PurchaseCategory.NON_OPERATING_INCOME.code }
                ?.forEach { item ->
                    var data = IncomeStatementItemEntity()
                    if (item.itemName == PurchaseTitleItem.NON_OPERATING_INCOME_TOTAL.value) {
                        nonOperatingRevenueTotal =
                            setItem(
                                IncomeStatementCategory.NON_OPERATING_REVENUE.code,
                                IncomeStatementItem.NON_OPERATING_REVENUE_TOTAL.value,
                                item.valueAmount ?: 0
                            ).apply {
                                currentValueRatio = item.valueAmount!!.toFloat().div(salesTotalAmount) * 100
                            }
                    } else {
                        data =
                            setItem(
                                IncomeStatementCategory.NON_OPERATING_REVENUE.code,
                                item.itemName!!,
                                item.valueAmount ?: 0
                            )
                    }
                    dataList.add(data)
                }
            dataList.add(nonOperatingRevenueTotal)


            /*영업외 비용 타이틀*/
            var nonOperatingExpenseTotal = IncomeStatementItemEntity()

            /*영업외 비용 목록*/
            purchaseItemList?.filter { item -> item.category == PurchaseCategory.NON_OPERATING_EXPENSES.code }
                ?.forEach { item ->
                    var data = IncomeStatementItemEntity()
                    if (item.itemName == PurchaseTitleItem.NON_OPERATING_EXPENSES_TOTAL.value) {
                        nonOperatingExpenseTotal =
                            setItem(
                                IncomeStatementCategory.NON_OPERATING_EXPENSE.code,
                                IncomeStatementItem.NON_OPERATING_EXPENSE_TOTAL.value,
                                item.valueAmount ?: 0
                            ).apply {
                                currentValueRatio = item.valueAmount!!.toFloat().div(salesTotalAmount) * 100
                            }
                    } else {
                        data =
                            setItem(
                                IncomeStatementCategory.NON_OPERATING_EXPENSE.code,
                                item.itemName!!,
                                item.valueAmount ?: 0
                            )
                    }
                    dataList.add(data)
                }
            dataList.add(nonOperatingExpenseTotal)

            /*영업외 비용 금액*/
            val nonOperatingExpenseTotalAmount = nonOperatingExpenseTotal.currentValueAmount ?: 0

            /*소득세 차감전 이익 금액*/
            val profitBeforeIncomeTaxValue =
                operatingProfitAmount.plus(nonOperatingRevenueTotal?.currentValueAmount ?: 0)

            val profitBeforeIncomeTax = setItem(
                IncomeStatementCategory.PROFIT_BEFORE_INCOME_TAX.code,
                IncomeStatementItem.PROFIT_BEFORE_INCOME_TAX_TOTAL.value,
                profitBeforeIncomeTaxValue
            ).apply {
                currentValueRatio = profitBeforeIncomeTaxValue.toFloat().div(salesTotalAmount) * 100
            }
            dataList.add(profitBeforeIncomeTax)

            /*소득세 등*/
            val incomeTax = setItem(
                IncomeStatementCategory.INCOME_TAX.code,
                IncomeStatementItem.INCOME_TAX_TOTAL.value,
                0
            ).apply {
                currentValueRatio = 0.0.toFloat()
            }
            dataList.add(incomeTax)


            val netProfitValue = salesTotalAmount
                .minus(nonOperatingExpenseTotalAmount)
                .minus(sgaExpenseTotalAmount)
                .minus(costOfGoodsSoldTotalAmount)

            val netProfit = setItem(
                IncomeStatementCategory.NET_PROFIT.code,
                IncomeStatementItem.NET_PROFIT_TOTAL.value,
                netProfitValue
            ).apply {
                currentValueRatio = netProfitValue.toFloat().div(salesTotalAmount) * 100
            }
            dataList.add(netProfit)

            dataList.removeIf { item -> item.category == null }

            dataList.forEach { item ->
                item.yearValueAmount = item.currentValueAmount!!.div(getCurrentMonthCount()) * 12
                item.yearValueRatio = item.currentValueRatio
            }

            it.exchangeRate = getCurrentDatePercentageOfYear()
            it.detailList = dataList
        }

        return data
    }


    fun setItem(category: String, itemName: String, itemValue: Long): IncomeStatementItemEntity {

        return IncomeStatementItemEntity(
            category = category,
            itemName = itemName,
            currentValueAmount = itemValue
        )
    }

    fun getCurrentMonthCount(): Int {
        val currentDate = LocalDate.now()
        return currentDate.monthValue
    }

    fun getCurrentDatePercentageOfYear(): Float {
        val currentDate = LocalDate.now()
        val startOfYear = LocalDate.of(currentDate.year, 1, 1)
        val endOfYear = LocalDate.of(currentDate.year, 12, 31)
        val totalDaysOfYear = endOfYear.toEpochDay() - startOfYear.toEpochDay() + 1
        val currentDayOfYear = currentDate.toEpochDay()+30 - startOfYear.toEpochDay() + 1
        return currentDayOfYear.toFloat().div(totalDaysOfYear) * 100
    }

}