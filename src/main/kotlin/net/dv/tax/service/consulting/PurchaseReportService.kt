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
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PurchaseReportService(
    private val purchaseReportRepository: PurchaseReportRepository,
    private val purchaseReportItemRepository: PurchaseReportItemRepository,
) {
    private val log = KotlinLogging.logger {}

    fun makeData(hospitalId: String, year: String) {

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




            /*매출원가*/
            dataList.add(setItem(PurchaseCategory.SALES_OF_COST.code, PurchaseTitleItem.BASIC_INVENTORY.value))
            dataList.add(setItem(PurchaseCategory.SALES_OF_COST.code, PurchaseTitleItem.CURRENT_PURCHASE.value))
            dataList.add(setItem(PurchaseCategory.SALES_OF_COST.code, PurchaseTitleItem.ENDING_STOCK.value))
            dataList.add(setItem(PurchaseCategory.SALES_OF_COST.code, PurchaseTitleItem.COST_OF_SALES.value))


            dataList.addAll(uniqueDataList)
            /*판관비 합계*/
            dataList.forEach { item ->
                if (item.category == PurchaseCategory.SGA_EXPENSE.code) {
                    sgaTotalAmount = +item.valueAmount!!
                }
            }
            dataList.add(
                PurchaseReportItemEntity(
                    category = PurchaseCategory.SGA_EXPENSE.code,
                    itemName = PurchaseTitleItem.SGA_EXPENSE_TOTAL.value,
                    valueAmount = sgaTotalAmount
                )
            )

            /*영업외 비용*/
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_EXPENSES.code, PurchaseTitleItem.INTEREST_EXPENSE.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_EXPENSES.code, PurchaseTitleItem.LEASE_INTEREST.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_EXPENSES.code, PurchaseTitleItem.DONATION_EXPENSE.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_EXPENSES.code, PurchaseTitleItem.SETTLEMENT_AMOUNT.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_EXPENSES.code, PurchaseTitleItem.MISCELLANEOUS_LOSS.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_EXPENSES.code, PurchaseTitleItem.LOSS_ON_DISPOSAL_OF_FIXED_ASSETS.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_EXPENSES.code, PurchaseTitleItem.ADDITIONAL_NON_OPERATING_EXPENSES.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_EXPENSES.code, PurchaseTitleItem.NON_OPERATING_EXPENSES_TOTAL.value))


            /*영업외 수익*/
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_INCOME.code, PurchaseTitleItem.SUPPORT_FUNDS.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_INCOME.code, PurchaseTitleItem.MISCELLANEOUS_PROFIT.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_INCOME.code, PurchaseTitleItem.INCOME_SETTLEMENT_AMOUNT.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_INCOME.code, PurchaseTitleItem.GAIN_ON_DISPOSAL_OF_FIXED_ASSETS.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_INCOME.code, PurchaseTitleItem.ADDITIONAL_NON_OPERATING_REVENUES.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_INCOME.code, PurchaseTitleItem.NON_OPERATING_INCOME_TOTAL.value))
            dataList.add(setItem(PurchaseCategory.NON_OPERATING_INCOME.code, PurchaseTitleItem.NON_OPERATING_TOTAL.value))

            /*감가비*/
            dataList.add(setItem(PurchaseCategory.DEPRECIATION.code, PurchaseTitleItem.DEPRECIABLE_AMOUNT.value))
            dataList.add(setItem(PurchaseCategory.DEPRECIATION.code, PurchaseTitleItem.DEPRECIATION_LIMIT.value))
            dataList.add(setItem(PurchaseCategory.DEPRECIATION.code, PurchaseTitleItem.DEPRECIATION_EXPENSE.value))
            dataList.add(setItem(PurchaseCategory.DEPRECIATION.code, PurchaseTitleItem.VEHICLE_LIMIT_EXCESS_AMOUNT.value))
            dataList.add(setItem(PurchaseCategory.DEPRECIATION.code, PurchaseTitleItem.RECOGNIZED_DEPRECIATION_AMOUNT.value))


            it.detailList = dataList

            purchaseReportRepository.save(it)

        }

    }


    fun saveItemData(dataList: MutableList<PurchaseReportItemEntity>): ResponseEntity<HttpStatus> {
        purchaseReportItemRepository.saveAll(dataList)
        return ResponseEntity(HttpStatus.OK)
    }

    fun setItem(category: String, itemName: String): PurchaseReportItemEntity {

        return PurchaseReportItemEntity(
            itemName = itemName,
            category = category
        )
    }
}
