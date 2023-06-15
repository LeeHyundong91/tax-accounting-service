package net.dv.tax.service.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.PurchaseReportEntity
import net.dv.tax.domain.consulting.PurchaseReportItemEntity
import net.dv.tax.dto.consulting.SgaExpenseCalcDto
import net.dv.tax.dto.consulting.SgaExpenseDto
import net.dv.tax.enums.consulting.PurchaseCategory
import net.dv.tax.enums.consulting.PurchaseTitleItem
import net.dv.tax.repository.consulting.PurchaseReportItemRepository
import net.dv.tax.repository.consulting.PurchaseReportRepository
import org.springframework.stereotype.Service

@Service
class PurchaseReportService(
    private val purchaseReportRepository: PurchaseReportRepository,
    private val purchaseReportItemRepository: PurchaseReportItemRepository,
) {
    private val log = KotlinLogging.logger {}

    fun saveData(hospitalId: String, year: String) {
        purchaseReportRepository.save(makeData(hospitalId, year))
    }

    fun getData(hospitalId: String, year: String): PurchaseReportEntity {
        return purchaseReportRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: PurchaseReportEntity()
    }

    fun makeData(hospitalId: String, year: String): PurchaseReportEntity {

        var defaultCondition = PurchaseReportEntity()
        val dataList: MutableList<PurchaseReportItemEntity> = mutableListOf()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        val data = purchaseReportRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: purchaseReportRepository.save(defaultCondition)

        data.also {
            val resultList: MutableList<SgaExpenseDto> = mutableListOf()
            val calcEmptyList: MutableList<SgaExpenseCalcDto> = mutableListOf()

            val cashReceipt = purchaseReportRepository.cashReceiptSgaExpense(hospitalId, year)
            val creditCard = purchaseReportRepository.creditCardSgaExpense(hospitalId, year)
            val elecInvoice = purchaseReportRepository.elecInvoiceSgaExpense(hospitalId, year)
            resultList.addAll(cashReceipt)
            resultList.addAll(creditCard)
            resultList.addAll(elecInvoice)

            resultList.forEach { cash ->
                val data = SgaExpenseCalcDto(
                    valueAmount = cash.valueAmount,
                    itemName = cash.itemName
                )
                calcEmptyList.add(data)
            }

            val uniqueDataMap = mutableMapOf<String, Long>()

            calcEmptyList.forEach { item ->
                val itemName = item.itemName ?: return@forEach
                val valueAmount = item.valueAmount ?: 0

                uniqueDataMap[itemName] = uniqueDataMap.getOrDefault(itemName, 0L) + valueAmount
            }

            val uniqueDataList = uniqueDataMap.map { (itemName, valueAmount) ->
                PurchaseReportItemEntity(
                    valueAmount = valueAmount,
                    itemName = itemName,
                    category = PurchaseCategory.SGA_EXPENSE.code
                )
            }

            var sgaTotalAmount: Long? = 0


            dataList.addAll(uniqueDataList)
            /*판관비 합계*/
            dataList.forEach { item ->
                if (item.category == PurchaseCategory.SGA_EXPENSE.code) {
                    sgaTotalAmount = sgaTotalAmount?.plus(item.valueAmount ?: 0)
                }
            }
            dataList.add(
                PurchaseReportItemEntity(
                    category = PurchaseCategory.SGA_EXPENSE.code,
                    itemName = PurchaseTitleItem.SGA_EXPENSE_TOTAL.value,
                    valueAmount = sgaTotalAmount
                )
            )

            dataList.addAll(makeBasicForm(dataList))

            it.detailList = dataList

//            purchaseReportRepository.save(it)

        }
        return data

    }

    fun saveItemData(
        hospitalId: String,
        year: String,
        dataList: MutableList<PurchaseReportItemEntity>,
    ) {

        purchaseReportRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)?.let { report ->
            val itemList = report.detailList

            val newThingList = dataList.filter { it.id == null }
                .map { item ->
                    PurchaseReportItemEntity(
                        itemName = item.itemName,
                        category = item.category,
                        memo = item.memo,
                        valueAmount = item.valueAmount
                    )
                }

            val updatedItemList = itemList + newThingList
            purchaseReportItemRepository.saveAll(newThingList).also { savedData ->
                updatedItemList + savedData
            }

            updatedItemList.forEach { origin ->
                dataList.find { addData -> origin.id == addData.id }
                    ?.let { addData ->
                        origin.valueAmount = addData.valueAmount
                        origin.memo = addData.memo
                    }
            }

            val sgaTotalAmount = calculateTotalAmountByCategory(updatedItemList, PurchaseCategory.SGA_EXPENSE.code)
            val salesOfCostTotal = calculateTotalAmountByCategory(updatedItemList, PurchaseCategory.SALES_OF_COST.code)
            val operatingExpensesTotal =
                calculateTotalAmountByCategory(updatedItemList, PurchaseCategory.NON_OPERATING_EXPENSES.code)
            val operatingIncomeTotal =
                calculateTotalAmountByCategory(updatedItemList, PurchaseCategory.NON_OPERATING_INCOME.code)

            itemList.forEach { item ->
                when (item.itemName) {
                    PurchaseTitleItem.COST_OF_SALES.value -> item.valueAmount = salesOfCostTotal
                    PurchaseTitleItem.SGA_EXPENSE_TOTAL.value -> item.valueAmount = sgaTotalAmount
                    PurchaseTitleItem.NON_OPERATING_EXPENSES_TOTAL.value -> item.valueAmount = operatingExpensesTotal
                    PurchaseTitleItem.NON_OPERATING_INCOME_TOTAL.value -> item.valueAmount = operatingIncomeTotal
                    PurchaseTitleItem.NON_OPERATING_TOTAL.value ->
                        item.valueAmount = operatingExpensesTotal + operatingIncomeTotal
                }
            }

            val totalAmount = itemList.filter {
                it.itemName == PurchaseTitleItem.COST_OF_SALES.value ||
                        it.itemName == PurchaseTitleItem.SGA_EXPENSE_TOTAL.value ||
                        it.itemName == PurchaseTitleItem.NON_OPERATING_TOTAL.value ||
                        it.itemName == PurchaseTitleItem.RECOGNIZED_DEPRECIATION_AMOUNT.value
            }.fold(0L) { acc, item ->
                acc + (item.valueAmount ?: 0)
            }

            report.purchaseTotalAmount = totalAmount
            report.costOfSalesTotal = salesOfCostTotal
            report.sgaExpensesTotal = sgaTotalAmount
            report.nonOperatingIncomeTotal = operatingExpensesTotal + operatingIncomeTotal
            report.detailList.addAll(newThingList)

            purchaseReportRepository.save(report)
        }
    }


    // Function to calculate the total amount for a given category in the item list
    fun calculateTotalAmountByCategory(itemList: List<PurchaseReportItemEntity>, category: String): Long {
        return itemList.filter { it.category == category }
            .fold(0L) { acc, item ->
                acc + (item.valueAmount ?: 0)
            }
    }

    fun setItem(category: String, itemName: String): PurchaseReportItemEntity {

        return PurchaseReportItemEntity(
            itemName = itemName,
            category = category
        )
    }

    fun makeBasicForm(dataList: MutableList<PurchaseReportItemEntity>): MutableList<PurchaseReportItemEntity> {
        /*매출원가*/
        dataList.add(setItem(PurchaseCategory.SALES_OF_COST.code, PurchaseTitleItem.BASIC_INVENTORY.value))
        dataList.add(setItem(PurchaseCategory.SALES_OF_COST.code, PurchaseTitleItem.CURRENT_PURCHASE.value))
        dataList.add(setItem(PurchaseCategory.SALES_OF_COST.code, PurchaseTitleItem.ENDING_STOCK.value))
        dataList.add(setItem(PurchaseCategory.SALES_OF_COST.code, PurchaseTitleItem.COST_OF_SALES.value))

        /*영업외 비용*/
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_EXPENSES.code,
                PurchaseTitleItem.INTEREST_EXPENSE.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_EXPENSES.code,
                PurchaseTitleItem.LEASE_INTEREST.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_EXPENSES.code,
                PurchaseTitleItem.DONATION_EXPENSE.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_EXPENSES.code,
                PurchaseTitleItem.SETTLEMENT_AMOUNT.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_EXPENSES.code,
                PurchaseTitleItem.MISCELLANEOUS_LOSS.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_EXPENSES.code,
                PurchaseTitleItem.LOSS_ON_DISPOSAL_OF_FIXED_ASSETS.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_EXPENSES.code,
                PurchaseTitleItem.ADDITIONAL_NON_OPERATING_EXPENSES.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_EXPENSES.code,
                PurchaseTitleItem.NON_OPERATING_EXPENSES_TOTAL.value
            )
        )

        /*영업외 수익*/
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_INCOME.code,
                PurchaseTitleItem.SUPPORT_FUNDS.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_INCOME.code,
                PurchaseTitleItem.MISCELLANEOUS_PROFIT.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_INCOME.code,
                PurchaseTitleItem.INCOME_SETTLEMENT_AMOUNT.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_INCOME.code,
                PurchaseTitleItem.GAIN_ON_DISPOSAL_OF_FIXED_ASSETS.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_INCOME.code,
                PurchaseTitleItem.ADDITIONAL_NON_OPERATING_REVENUES.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_INCOME.code,
                PurchaseTitleItem.NON_OPERATING_INCOME_TOTAL.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.NON_OPERATING_INCOME.code,
                PurchaseTitleItem.NON_OPERATING_TOTAL.value
            )
        )

        /*감가비*/
        dataList.add(
            setItem(
                PurchaseCategory.DEPRECIATION.code,
                PurchaseTitleItem.DEPRECIABLE_AMOUNT.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.DEPRECIATION.code,
                PurchaseTitleItem.DEPRECIATION_LIMIT.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.DEPRECIATION.code,
                PurchaseTitleItem.DEPRECIATION_EXPENSE.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.DEPRECIATION.code,
                PurchaseTitleItem.VEHICLE_LIMIT_EXCESS_AMOUNT.value
            )
        )
        dataList.add(
            setItem(
                PurchaseCategory.DEPRECIATION.code,
                PurchaseTitleItem.RECOGNIZED_DEPRECIATION_AMOUNT.value
            )
        )

        return dataList
    }

}
