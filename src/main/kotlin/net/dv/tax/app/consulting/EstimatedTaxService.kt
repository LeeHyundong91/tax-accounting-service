package net.dv.tax.app.consulting

import net.dv.tax.domain.consulting.EstimatedTaxEntity
import net.dv.tax.domain.consulting.EstimatedTaxItemEntity
import net.dv.tax.domain.consulting.EstimatedTaxPersonalEntity
import net.dv.tax.domain.consulting.TaxCreditPersonalEntity
import net.dv.tax.app.enums.consulting.EstimatedTaxCategory
import net.dv.tax.app.enums.consulting.EstimatedTaxItem
import org.springframework.stereotype.Service

@Service
class EstimatedTaxService(
    private val estimatedTaxRepository: EstimatedTaxRepository,
    private val estimatedTaxPersonalRepository: EstimatedTaxPersonalRepository,
    private val estimatedTaxItemRepository: EstimatedTaxItemRepository,
    private val taxCreditPersonalRepository: TaxCreditPersonalRepository,
) {

    fun getData(hospitalId: String, year: String): EstimatedTaxEntity {
        return estimatedTaxRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: EstimatedTaxEntity()
    }


    fun makeData(hospitalId: String, year: String) {

        val defaultCondition = EstimatedTaxEntity()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        /*연환산금액*/
        val data = estimatedTaxRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: estimatedTaxRepository.save(defaultCondition)

        data.also {

            val directorList = mutableListOf<TaxCreditPersonalEntity>()

            taxCreditPersonalRepository.directorAndStakeList(hospitalId).forEach { item ->

                val personalData = EstimatedTaxPersonalEntity(
                    estimatedTaxId = data.id,
                    director = item.director,
                    stake = item.stake.toFloat(),
                    directorId = item.directorId,
                    hospitalId = hospitalId,
                )

                val data = estimatedTaxPersonalRepository.findAllByHospitalIdAndDirectorAndDirectorId(
                    hospitalId,
                    item.director,
                    item.directorId
                ) ?: estimatedTaxPersonalRepository.save(personalData)

                data.also { personalItem ->
                    val itemList = mutableListOf<EstimatedTaxItemEntity>()

                    itemList.addAll(defaultItem(itemList))
                    personalItem.detailList = itemList

                    estimatedTaxPersonalRepository.save(personalItem)
                }
            }
            estimatedTaxRepository.save(data)
        }

    }


    fun personalSetItem(category: String, itemName: String): EstimatedTaxItemEntity {
        return EstimatedTaxItemEntity(
            category = category,
            itemName = itemName,
        )
    }

    fun defaultItem(dataList: MutableList<EstimatedTaxItemEntity>): MutableList<EstimatedTaxItemEntity> {
        val categoryItemMap = mapOf(
            EstimatedTaxCategory.INCOME_TAX.code to listOf(
                EstimatedTaxItem.OTHER_INCOME.value,
                EstimatedTaxItem.TOTAL_INCOME.value,
                EstimatedTaxItem.INCOME_DEDUCTIONS.value,
                EstimatedTaxItem.TAXABLE_BASE.value,
                EstimatedTaxItem.TAX_RATE.value,
                EstimatedTaxItem.CALCULATED_TAX.value,
                EstimatedTaxItem.TAX_REDUCTION.value,
                EstimatedTaxItem.TAX_DEDUCTION.value,
                EstimatedTaxItem.COMPREHENSIVE_TAX.value,
                EstimatedTaxItem.SEPARATE_TAXATION.value,
                EstimatedTaxItem.TOTAL_DETERMINED_TAX.value,
                EstimatedTaxItem.ADDITIONAL_TAX.value,
                EstimatedTaxItem.ADDITIONAL_PAYMENT_TAX.value,
                EstimatedTaxItem.TOTAL_INCOME_TAX.value,
                EstimatedTaxItem.PREPAID_TAX.value,
                EstimatedTaxItem.TOTAL_TAX_PAYABLE.value,
                EstimatedTaxItem.SPECIAL_DEDUCTION_TAX_DEDUCTION.value,
                EstimatedTaxItem.SPECIAL_DEDUCTION_TAX_ADDITIONAL.value,
                EstimatedTaxItem.INSTALMENT_TAX.value,
            ),
            EstimatedTaxCategory.LOCAL_INCOME_TAX.code to listOf(
                EstimatedTaxItem.TAXABLE_BASE.value,
                EstimatedTaxItem.CALCULATED_TAX.value,
                EstimatedTaxItem.TAX_REDUCTION.value,
                EstimatedTaxItem.TAX_DEDUCTION.value,
                EstimatedTaxItem.COMPREHENSIVE_TAX.value,
                EstimatedTaxItem.SEPARATE_TAXATION.value,
                EstimatedTaxItem.TOTAL_DETERMINED_TAX.value,
                EstimatedTaxItem.ADDITIONAL_TAX.value,
                EstimatedTaxItem.TOTAL_LOCAL_INCOME_TAX.value,
                EstimatedTaxItem.PREPAID_TAX.value,
            ),
            EstimatedTaxCategory.SPECIAL_TAX.code to listOf(
                EstimatedTaxItem.TAXABLE_BASE.value,
                EstimatedTaxItem.TAX_RATE.value,
                EstimatedTaxItem.CALCULATED_TAX.value,
                EstimatedTaxItem.INSTALMENT_TAX.value,
            ),

            EstimatedTaxCategory.PRE_PAID_TAX_AMOUNT.code to listOf(
                EstimatedTaxItem.INTERIM_PREPAID_TAX.value,
                EstimatedTaxItem.ANTICIPATED_DECLARATION_TAX.value,
                EstimatedTaxItem.ANTICIPATED_NOTIFICATION_TAX.value,
                EstimatedTaxItem.ADDITIONAL_TAX_ASSESSED.value,
                EstimatedTaxItem.INTEREST_INCOME.value,
                EstimatedTaxItem.DIVIDEND_INCOME.value,
                EstimatedTaxItem.BUSINESS_INCOME.value,
                EstimatedTaxItem.LABOR_INCOME.value,
                EstimatedTaxItem.PENSION_INCOME.value,
                EstimatedTaxItem.ETC_INCOME.value,
            ),
        )

        categoryItemMap.forEach { (category, items) ->
            items.forEach { itemName ->
                dataList.add(personalSetItem(category, itemName))
            }
        }
        return dataList
    }


}