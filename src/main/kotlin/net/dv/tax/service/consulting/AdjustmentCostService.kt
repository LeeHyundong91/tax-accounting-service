package net.dv.tax.service.consulting

import net.dv.tax.domain.consulting.AdjustmentCostEntity
import net.dv.tax.domain.consulting.AdjustmentCostItemEntity
import net.dv.tax.enums.consulting.AdjustmentCostCategory
import net.dv.tax.enums.consulting.AdjustmentCostItem
import net.dv.tax.repository.consulting.AdjustmentCostRepository
import org.springframework.stereotype.Service

@Service
class AdjustmentCostService(private val adjustmentCostRepository: AdjustmentCostRepository) {


    fun saveData(hospitalId: String, year: String) {
        adjustmentCostRepository.save(makeData(hospitalId, year))
    }

    fun getData(hospitalId: String, year: String): AdjustmentCostEntity {
        return adjustmentCostRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: AdjustmentCostEntity()
    }

    fun makeData(hospitalId: String, year: String): AdjustmentCostEntity {

        var defaultCondition = AdjustmentCostEntity()
        val dataList: MutableList<AdjustmentCostItemEntity> = mutableListOf()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        val data = adjustmentCostRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: adjustmentCostRepository.save(defaultCondition)

        data.also {
            dataList.addAll(makeBasicForm(dataList))
            it.detailList = dataList
            adjustmentCostRepository.save(it)
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
                AdjustmentCostItem.ADDITIONAL_EXPENSES_TOTAL.value
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
                AdjustmentCostItem.INCOME_ADJUSTMENT.value
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
            category = category
        )
    }

}