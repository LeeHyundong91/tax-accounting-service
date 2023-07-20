package net.dv.tax.app.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.PurchaseReportEntity
import net.dv.tax.domain.consulting.PurchaseReportItemEntity
import net.dv.tax.app.dto.consulting.SgaExpense
import net.dv.tax.app.dto.consulting.SgaExpenseDto
import net.dv.tax.app.enums.consulting.PurchaseCategory
import net.dv.tax.app.enums.consulting.PurchaseTitleItem
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
            val resultList: MutableList<SgaExpense> = mutableListOf()
            val calcEmptyList: MutableList<SgaExpenseDto> = mutableListOf()

            val cashReceipt = purchaseReportRepository.cashReceiptSgaExpense(hospitalId, year)
            val creditCard = purchaseReportRepository.creditCardSgaExpense(hospitalId, year)
            val elecInvoice = purchaseReportRepository.elecInvoiceSgaExpense(hospitalId, year)
            resultList.addAll(cashReceipt)
            resultList.addAll(creditCard)
            resultList.addAll(elecInvoice)

            resultList.forEach { cash ->
                val data = SgaExpenseDto(
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

            val sgaTotalAmount = calculateTotalAmountByCategory(
                updatedItemList,
                PurchaseCategory.SGA_EXPENSE.code,
                PurchaseTitleItem.SGA_EXPENSE_TOTAL.value
            )
            val salesOfCostTotal = calculateTotalAmountByCategory(
                updatedItemList,
                PurchaseCategory.SALES_OF_COST.code,
                PurchaseTitleItem.COST_OF_SALES.value
            )
            val operatingExpensesTotal =
                calculateTotalAmountByCategory(
                    updatedItemList,
                    PurchaseCategory.NON_OPERATING_EXPENSES.code,
                    PurchaseTitleItem.NON_OPERATING_EXPENSES_TOTAL.value
                )
            val operatingIncomeTotal =
                calculateTotalAmountByCategory(
                    updatedItemList,
                    PurchaseCategory.NON_OPERATING_INCOME.code,
                    PurchaseTitleItem.NON_OPERATING_INCOME_TOTAL.value
                )

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


    fun calculateTotalAmountByCategory(
        itemList: List<PurchaseReportItemEntity>,
        category: String,
        exceptItem: String,
    ): Long {
        return itemList.filter { it.category == category && it.itemName != exceptItem }
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
        val categoryItemMap = mapOf(
            PurchaseCategory.SALES_OF_COST.code to listOf(
                PurchaseTitleItem.BASIC_INVENTORY.value,
                PurchaseTitleItem.CURRENT_PURCHASE.value,
                PurchaseTitleItem.ENDING_STOCK.value,
                PurchaseTitleItem.COST_OF_SALES.value
            ),
            PurchaseCategory.NON_OPERATING_EXPENSES.code to listOf(
                PurchaseTitleItem.INTEREST_EXPENSE.value,
                PurchaseTitleItem.LEASE_INTEREST.value,
                PurchaseTitleItem.DONATION_EXPENSE.value,
                PurchaseTitleItem.SETTLEMENT_AMOUNT.value,
                PurchaseTitleItem.MISCELLANEOUS_LOSS.value,
                PurchaseTitleItem.LOSS_ON_DISPOSAL_OF_FIXED_ASSETS.value,
                PurchaseTitleItem.ADDITIONAL_NON_OPERATING_EXPENSES.value,
                PurchaseTitleItem.NON_OPERATING_EXPENSES_TOTAL.value
            ),
            PurchaseCategory.NON_OPERATING_INCOME.code to listOf(
                PurchaseTitleItem.SUPPORT_FUNDS.value,
                PurchaseTitleItem.MISCELLANEOUS_PROFIT.value,
                PurchaseTitleItem.INCOME_SETTLEMENT_AMOUNT.value,
                PurchaseTitleItem.GAIN_ON_DISPOSAL_OF_FIXED_ASSETS.value,
                PurchaseTitleItem.ADDITIONAL_NON_OPERATING_REVENUES.value,
                PurchaseTitleItem.NON_OPERATING_INCOME_TOTAL.value,
                PurchaseTitleItem.NON_OPERATING_TOTAL.value
            ),
            PurchaseCategory.DEPRECIATION.code to listOf(
                PurchaseTitleItem.DEPRECIABLE_AMOUNT.value,
                PurchaseTitleItem.DEPRECIATION_LIMIT.value,
                PurchaseTitleItem.DEPRECIATION_EXPENSE.value,
                PurchaseTitleItem.VEHICLE_LIMIT_EXCESS_AMOUNT.value,
                PurchaseTitleItem.RECOGNIZED_DEPRECIATION_AMOUNT.value
            )
        )

        categoryItemMap.forEach { (category, items) ->
            items.forEach { itemName ->
                dataList.add(setItem(category, itemName))
            }
        }

        return dataList
    }

}
