package net.dv.tax.service.consulting

import net.dv.tax.domain.consulting.ExpectedIncomeEntity
import net.dv.tax.domain.consulting.ExpectedIncomeItemEntity
import net.dv.tax.domain.consulting.ExpectedIncomeMonthlyItemEntity
import net.dv.tax.enums.consulting.AdjustmentCostItem
import net.dv.tax.enums.consulting.ExpectedIncomeCategory
import net.dv.tax.enums.consulting.ExpectedIncomeItem
import net.dv.tax.repository.consulting.*
import org.springframework.stereotype.Service

@Service
class ExpectedIncomeService(
    private val expectedIncomeRepository: ExpectedIncomeRepository,
    private val expectedIncomeItemRepository: ExpectedIncomeItemRepository,
    private val expectedIncomeMonthlyItemRepository: ExpectedIncomeMonthlyItemRepository,
    private val purchaseReportRepository: PurchaseReportRepository,
    private val salesTypeRepository: SalesTypeRepository,
    private val adjustmentCostRepository: AdjustmentCostRepository,

    ) {

    fun getData(hospitalId: String, year: String): ExpectedIncomeEntity {
        return expectedIncomeRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: ExpectedIncomeEntity()
    }

    fun saveData(hospitalId: String, year: String) {
        expectedIncomeRepository.save(makeData(hospitalId, year))
    }

    fun makeData(hospitalId: String, year: String): ExpectedIncomeEntity {


        val defaultCondition = ExpectedIncomeEntity()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year
        defaultCondition.targetIncomeRatio = 10.0.toFloat()


        /*연환산금액*/
        val data =
            expectedIncomeRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
                ?: expectedIncomeRepository.save(defaultCondition)

        data.also {
            val itemList = mutableListOf<ExpectedIncomeItemEntity>()
            val monthlyList = mutableListOf<ExpectedIncomeMonthlyItemEntity>()
            itemList.addAll(makeBasicForm(itemList))

            val annualSalesAmount =
                salesTypeRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)!!.totalAmount
                    ?: 0
            val annualCostAmount =
                purchaseReportRepository.findTopByHospitalIdAndResultYearMonthStartingWith(
                    hospitalId,
                    year
                )!!.purchaseTotalAmount ?: 0

            val annualIncomeAmount = annualSalesAmount.minus(annualCostAmount)
            val incomeRate = annualIncomeAmount.toFloat().div(annualSalesAmount) * 100
            itemList.filter { filter -> filter.category == ExpectedIncomeCategory.ANNUALIZED_AMOUNT.code }
                .forEach { item ->
                    when (item.itemName) {
                        ExpectedIncomeItem.ANNUAL_SALES.value -> item.itemValue = annualSalesAmount
                        ExpectedIncomeItem.ANNUAL_COST.value -> item.itemValue = annualCostAmount
                        ExpectedIncomeItem.ANNUAL_INCOME.value -> item.itemValue = annualIncomeAmount
                        ExpectedIncomeItem.INCOME_RATE.value -> item.itemRatio = incomeRate
                    }
                }

            /*추가비용*/
            val adjustValueList =
                adjustmentCostRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)?.detailList

            val adjustSalesAmount: Long =
                adjustValueList?.find { find -> find.itemName == AdjustmentCostItem.INCOME_ADJUSTMENT_TOTAL.value }!!.itemValue
                    ?: 0

            val adjustCostAmount: Long =
                adjustValueList.find { find -> find.itemName == AdjustmentCostItem.ADDITIONAL_EXPENSES_TOTAL.value }!!.itemValue
                    ?: 0
            val adjustSumAmount: Long = adjustSalesAmount.minus(adjustCostAmount)

            itemList.filter { filter -> filter.category == ExpectedIncomeCategory.EXPECTED_ADDITIONAL.code }
                .forEach { item ->
                    when (item.itemName) {
                        ExpectedIncomeItem.ADDITIONAL_EXPECTED_SALES.value -> item.itemValue = adjustSalesAmount
                        ExpectedIncomeItem.ADDITIONAL_ESTIMATED_COSTS.value -> item.itemValue = adjustCostAmount
                        ExpectedIncomeItem.EXTRA_EXPECTED_INCOME.value -> item.itemValue = adjustSumAmount
                    }
                }

            /*목표금액*/
            val targetSalesAmount = annualSalesAmount.plus(adjustSalesAmount)
            val targetCostAmount = annualCostAmount.plus(adjustCostAmount)
            val targetIncomeAmount = annualIncomeAmount.plus(adjustSumAmount)
            val targetIncomeRate = targetIncomeAmount.toFloat().div(targetSalesAmount) * 100

            itemList.filter { filter -> filter.category == ExpectedIncomeCategory.TARGET_AMOUNT.code }
                .forEach { item ->
                    when (item.itemName) {
                        ExpectedIncomeItem.TARGET_ANNUAL_SALES.value -> item.itemValue = targetSalesAmount
                        ExpectedIncomeItem.TARGET_ANNUAL_COST.value -> item.itemValue = targetCostAmount
                        ExpectedIncomeItem.TARGET_ANNUAL_INCOME.value -> item.itemValue = targetIncomeAmount
                        ExpectedIncomeItem.TARGET_INCOME_RATE.value -> item.itemRatio = targetIncomeRate
                    }
                }


            monthlyList.addAll(makeMonthlyForm(annualSalesAmount, annualCostAmount))


            data.monthlyList = monthlyList
            data.detailList = itemList
        }

        return data

    }


    fun setItem(category: String, itemName: String): ExpectedIncomeItemEntity {
        return ExpectedIncomeItemEntity(
            category = category,
            itemName = itemName,
        )
    }

    fun setMonthly(salesAmount: Long, costAmount: Long, month: String): ExpectedIncomeMonthlyItemEntity {
        return ExpectedIncomeMonthlyItemEntity(
            sales = salesAmount,
            cost = costAmount,
            expectedMonth = month
        )

    }

    fun makeMonthlyForm(salesAmount: Long, costAmount: Long): MutableList<ExpectedIncomeMonthlyItemEntity> {
        val dataList = mutableListOf<ExpectedIncomeMonthlyItemEntity>()
        val divideSalesAmount = salesAmount / 12
        val divideCostAmount = costAmount / 12

        for (i in 1..12) {
            val month = String.format("%02d", i)
            dataList.add(setMonthly(divideSalesAmount, divideCostAmount, month))
        }

        return dataList
    }


    fun makeBasicForm(dataList: MutableList<ExpectedIncomeItemEntity>): MutableList<ExpectedIncomeItemEntity> {
        val categoryItemMap = mapOf(
            ExpectedIncomeCategory.ANNUALIZED_AMOUNT.code to listOf(
                ExpectedIncomeItem.ANNUAL_SALES.value,
                ExpectedIncomeItem.ANNUAL_COST.value,
                ExpectedIncomeItem.ANNUAL_INCOME.value,
                ExpectedIncomeItem.INCOME_RATE.value,
            ),
            ExpectedIncomeCategory.EXPECTED_ADDITIONAL.code to listOf(
                ExpectedIncomeItem.ADDITIONAL_EXPECTED_SALES.value,
                ExpectedIncomeItem.ADDITIONAL_ESTIMATED_COSTS.value,
                ExpectedIncomeItem.EXTRA_EXPECTED_INCOME.value,
            ),
            ExpectedIncomeCategory.TARGET_AMOUNT.code to listOf(
                ExpectedIncomeItem.TARGET_ANNUAL_SALES.value,
                ExpectedIncomeItem.TARGET_ANNUAL_COST.value,
                ExpectedIncomeItem.TARGET_ANNUAL_INCOME.value,
                ExpectedIncomeItem.TARGET_INCOME_RATE.value,
            ),
        )

        categoryItemMap.forEach { (category, items) ->
            items.forEach { itemName ->
                dataList.add(setItem(category, itemName))
            }
        }

        return dataList
    }

}