package net.dv.tax.app.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.AdjustmentCostEntity
import net.dv.tax.domain.consulting.AdjustmentCostItemEntity
import net.dv.tax.app.enums.consulting.AdjustmentCostCategory
import net.dv.tax.app.enums.consulting.AdjustmentCostItem
import net.dv.tax.app.enums.consulting.AdjustmentCostItemOption
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AdjustmentCostService(
    private val adjustmentCostRepository: AdjustmentCostRepository,
    private val adjustmentCostItemRepository: AdjustmentCostItemRepository,
) {

    private val log = KotlinLogging.logger {}

    fun removeItem(id: Long): ResponseEntity<HttpStatus>{
        adjustmentCostItemRepository.deleteById(id)
        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun saveData(hospitalId: String, year: String) {
        adjustmentCostRepository.save(makeData(hospitalId, year))
    }

    fun getData(hospitalId: String, year: String): AdjustmentCostEntity {
        return adjustmentCostRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: AdjustmentCostEntity()
    }

    fun makeData(hospitalId: String, year: String): AdjustmentCostEntity {

        val defaultCondition = AdjustmentCostEntity()
        val dataList: MutableList<AdjustmentCostItemEntity> = mutableListOf()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        val data = adjustmentCostRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: adjustmentCostRepository.save(defaultCondition)

        data.also {
            dataList.addAll(makeBasicForm(dataList))
            it.detailList = dataList
//            adjustmentCostRepository.save(it)
        }
        return data

    }

    fun makeBasicForm(dataList: MutableList<AdjustmentCostItemEntity>): MutableList<AdjustmentCostItemEntity> {
        val categoryItemMap = mapOf(
            AdjustmentCostCategory.INCOME_ADJUSTMENT.code to listOf(
                AdjustmentCostItem.TRIPOD_AMOUNT.value,
                AdjustmentCostItem.RETIREMENT_PENSION.value,
                AdjustmentCostItem.ADDITIONAL_INTEREST_EXPENSE.value,
                AdjustmentCostItem.HEALTH_INSURANCE_EXEMPTION.value,
                AdjustmentCostItem.FREELANCER.value,
                AdjustmentCostItem.ADDITIONAL_COMPENSATION.value,
                AdjustmentCostItem.ADVERTISING_EXPENSE.value,
                AdjustmentCostItem.MEDICINE.value,
                AdjustmentCostItem.CONSUMABLES.value,
                AdjustmentCostItem.OTHER_TAX_INVOICES.value,
                AdjustmentCostItem.ADDITIONAL_ENTERTAINMENT_EXPENSE.value,
                AdjustmentCostItem.CARD_COMMISSION.value,
                AdjustmentCostItem.CREDIT_CARD_EXPENSE.value,
                AdjustmentCostItem.DONATION.value,
                AdjustmentCostItem.OTHER.value,
                AdjustmentCostItem.INCOME_ADJUSTMENT_TOTAL.value
            ),
            AdjustmentCostCategory.ADDITIONAL_EXPENSES.code to listOf(
                AdjustmentCostItem.INTEREST_EXPENSE.value,
                AdjustmentCostItem.BUSINESS_PRIVATE_VEHICLE_EXPENSE.value,
                AdjustmentCostItem.DEPRECIATION_EXPENSE.value,
                AdjustmentCostItem.ENTERTAINMENT_EXPENSE.value,
                AdjustmentCostItem.LEASE_PAYMENT.value,
                AdjustmentCostItem.FRINGE_BENEFIT_EXPENSE.value,
                AdjustmentCostItem.CONSUMABLES_EXPENSE.value,
                AdjustmentCostItem.INCOME_TAX.value,
                AdjustmentCostItem.ADDITIONAL_EXPENSES_TOTAL.value
            )

        )

        categoryItemMap.forEach { (category, items) ->
            items.forEach { itemName ->
                dataList.add(setItem(category, itemName))
            }
        }

        return dataList
    }

    fun setItem(category: String, itemName: String): AdjustmentCostItemEntity {
        return AdjustmentCostItemEntity(
            itemName = itemName,
            category = category,
            distribution = AdjustmentCostItemOption.FINAL.code
        )
    }

    fun calculateTotalAmountByCategory(
        itemList: List<AdjustmentCostItemEntity>,
        category: String,
        exceptItem: String,
    ): Long {
        return itemList.filter { it.category == category && it.itemName != exceptItem }
            .fold(0L) { acc, item ->
                acc + (item.itemValue ?: 0)
            }
    }


    fun saveItemData(
        hospitalId: String,
        year: String,
        dataList: MutableList<AdjustmentCostItemEntity>,
    ) {
        adjustmentCostRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)?.let { report ->
            val itemList = report.detailList

            val newThingList = dataList.filter { it.id == null }
                .map { item ->
                    AdjustmentCostItemEntity(
                        itemName = item.itemName,
                        category = item.category,
                        memo = item.memo,
                        itemValue = item.itemValue,
                        distribution = item.distribution,
                        startDate = item.startDate,
                        endDate = item.endDate
                    )
                }

            val updatedItemList = itemList + newThingList
            adjustmentCostItemRepository.saveAll(newThingList).also { savedData ->
                updatedItemList + savedData
            }


            updatedItemList.forEach { origin ->
                dataList.find { addData -> origin.id == addData.id }
                    ?.let { addData ->
                        origin.itemValue = addData.itemValue
                        origin.memo = addData.memo
                        origin.distribution = addData.distribution
                        if (addData.distribution == AdjustmentCostItemOption.PERIOD.code) {
                            origin.startDate = addData.startDate
                            origin.endDate = addData.endDate
                        }
                    }
            }


            val additionalExpensesTotalAmount = calculateTotalAmountByCategory(
                updatedItemList,
                AdjustmentCostCategory.ADDITIONAL_EXPENSES.code,
                AdjustmentCostItem.ADDITIONAL_EXPENSES_TOTAL.value
            )


           val incomeAdjustmentTotalAmount = calculateTotalAmountByCategory(
               updatedItemList,
               AdjustmentCostCategory.INCOME_ADJUSTMENT.code,
               AdjustmentCostItem.INCOME_ADJUSTMENT_TOTAL.value
           )


            updatedItemList.forEach { item ->
                when (item.itemName) {
                    AdjustmentCostItem.ADDITIONAL_EXPENSES_TOTAL.value -> item.itemValue = additionalExpensesTotalAmount
                    AdjustmentCostItem.INCOME_ADJUSTMENT_TOTAL.value -> item.itemValue = incomeAdjustmentTotalAmount
                }
            }

            report.detailList.addAll(updatedItemList)
            adjustmentCostRepository.save(report)

        }


    }
}