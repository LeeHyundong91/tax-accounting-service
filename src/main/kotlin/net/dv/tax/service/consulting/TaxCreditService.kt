package net.dv.tax.service.consulting

import net.dv.tax.domain.consulting.TaxCreditEntity
import net.dv.tax.domain.consulting.TaxCreditItemEntity
import net.dv.tax.domain.consulting.TaxCreditPersonalEntity
import net.dv.tax.domain.consulting.TaxCreditPersonalItemEntity
import net.dv.tax.dto.consulting.TaxCreditDto
import net.dv.tax.enums.consulting.TaxCreditCategory
import net.dv.tax.enums.consulting.TaxCreditItem
import net.dv.tax.enums.consulting.TaxCreditPersonalCategory
import net.dv.tax.enums.consulting.TaxCreditPersonalItem
import net.dv.tax.repository.consulting.TaxCreditPersonalRepository
import net.dv.tax.repository.consulting.TaxCreditRepository
import org.springframework.stereotype.Service

@Service
class TaxCreditService(
    private val taxCreditRepository: TaxCreditRepository,
    private val taxCreditPersonalRepository: TaxCreditPersonalRepository,
) {

    fun patchItemOption(hospitalId: String, year: String, option: String) {
        taxCreditRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year).also {

            val taxCreditItemList = it!!.detailList

            taxCreditPersonalRepository.findAllByHospitalIdAndResultYearMonthStartingWith(
                hospitalId, year
            ).forEach { item ->
                var personalList =
                    item.detailList.filter { filter -> filter.category == TaxCreditPersonalCategory.PERSONAL.code }

                val itemList = mutableListOf<TaxCreditPersonalItemEntity>()

                taxCreditItemList.filter { filter -> filter.category == option || filter.category == TaxCreditCategory.DEFAULT.code }
                    .forEach { personalItem ->
                        val currentAccruals: Long = personalItem.itemValue?.times((item.stake!!.div(100)))!!.toLong()
                        itemList.add(
                            personalSetItemWithValue(
                                TaxCreditPersonalCategory.HOSPITAL.code, personalItem.itemName!!, currentAccruals
                            )
                        )
                    }

                val hospitalSum = TaxCreditPersonalItemEntity(
                    category = TaxCreditPersonalCategory.HOSPITAL.code,
                    itemName = TaxCreditPersonalItem.HOSPITAL_SMALL_AMOUNT.value,
                    lastYearAmount = itemList.sumOf { sum -> sum.lastYearAmount ?: 0 },
                    currentAccruals = itemList.sumOf { sum -> sum.currentAccruals ?: 0 },
                    vanishingAmount = itemList.sumOf { sum -> sum.vanishingAmount ?: 0 },
                    currentDeduction = itemList.sumOf { sum -> sum.currentDeduction ?: 0 },
                    amountCarriedForward = itemList.sumOf { sum -> sum.amountCarriedForward ?: 0 },
                )

                itemList.add(hospitalSum)
                itemList.addAll(personalList)

                item.detailList = itemList

                taxCreditPersonalRepository.save(item)
            }

        }
    }

    fun getData(hospitalId: String, year: String): TaxCreditDto {
        return TaxCreditDto(
            taxCredit = taxCreditRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year),
            directorList = taxCreditPersonalRepository.findAllByHospitalIdAndResultYearMonthStartingWith(
                hospitalId, year
            )
        )
    }

    fun saveData(hospitalId: String, year: String) {
        taxCreditRepository.save(makeHospitalData(hospitalId, year)).also {
            makePersonalData(hospitalId, year)
        }
    }

    fun makeHospitalData(hospitalId: String, year: String): TaxCreditEntity {

        val defaultCondition = TaxCreditEntity()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        /*연환산금액*/
        val data = taxCreditRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: taxCreditRepository.save(defaultCondition)
        data.also {
            val itemList = mutableListOf<TaxCreditItemEntity>()
            itemList.addAll(employmentIncreaseItem(itemList))
            itemList.addAll(integratedEmploymentItem(itemList))
            itemList.addAll(defaultItem(itemList))
            itemList.forEach { item ->
                item.itemValue = 100000000
            }
            it.detailList = itemList
        }

        return data

    }


    fun makePersonalData(hospitalId: String, year: String) {

        val taxCreditItemList =
            taxCreditRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)?.detailList
        val directorList = mutableListOf<TaxCreditPersonalEntity>()
        taxCreditPersonalRepository.directorAndStakeList(hospitalId).forEach { item ->

            val personalData = TaxCreditPersonalEntity(
                director = item.director,
                stake = item.stake.toFloat(),
                hospitalId = hospitalId,
                resultYearMonth = year,
                consultingReportId = 1,
            )

            taxCreditPersonalRepository.save(personalData).also {

                val itemList = mutableListOf<TaxCreditPersonalItemEntity>()
                itemList.addAll(personalItem(itemList))

                taxCreditItemList?.filter { filter ->
                    filter.category == TaxCreditCategory.INTEGRATED_EMPLOYMENT.code || filter.category == TaxCreditCategory.DEFAULT.code
                }?.forEach { personalItem ->

                        val currentAccruals: Long = personalItem.itemValue?.times((it.stake!!.div(100)))!!.toLong()

                        itemList.add(
                            personalSetItemWithValue(
                                TaxCreditPersonalCategory.HOSPITAL.code, personalItem.itemName!!, currentAccruals
                            )
                        )
                    }

                val hospitalSum = TaxCreditPersonalItemEntity(
                    category = TaxCreditPersonalCategory.HOSPITAL.code,
                    itemName = TaxCreditPersonalItem.HOSPITAL_SMALL_AMOUNT.value,
                    lastYearAmount = itemList.sumOf { sum -> sum.lastYearAmount ?: 0 },
                    currentAccruals = itemList.sumOf { sum -> sum.currentAccruals ?: 0 },
                    vanishingAmount = itemList.sumOf { sum -> sum.vanishingAmount ?: 0 },
                    currentDeduction = itemList.sumOf { sum -> sum.currentDeduction ?: 0 },
                    amountCarriedForward = itemList.sumOf { sum -> sum.amountCarriedForward ?: 0 },
                )
                itemList.add(hospitalSum)

                it.detailList = itemList
                directorList.add(it)
            }
        }

        taxCreditPersonalRepository.saveAll(directorList)

    }

    fun taxCreditSetItem(category: String, itemName: String): TaxCreditItemEntity {
        return TaxCreditItemEntity(
            category = category,
            itemName = itemName,
        )
    }

    fun personalSetItem(category: String, itemName: String): TaxCreditPersonalItemEntity {
        return TaxCreditPersonalItemEntity(
            category = category,
            itemName = itemName,
        )
    }

    fun personalSetItemWithValue(
        category: String,
        itemName: String,
        currentAccruals: Long,
    ): TaxCreditPersonalItemEntity {
        return TaxCreditPersonalItemEntity(
            category = category, itemName = itemName, currentAccruals = currentAccruals
        )
    }


    fun integratedEmploymentItem(dataList: MutableList<TaxCreditItemEntity>): MutableList<TaxCreditItemEntity> {
        val categoryItemMap = mapOf(
            TaxCreditCategory.INTEGRATED_EMPLOYMENT.code to listOf(
                TaxCreditItem.INTEGRATED_EMPLOYMENT.value,
                TaxCreditItem.ADDITIONAL_DEDUCTION.value,
            ),
        )

        categoryItemMap.forEach { (category, items) ->
            items.forEach { itemName ->
                dataList.add(taxCreditSetItem(category, itemName))
            }
        }

        return dataList
    }


    fun defaultItem(dataList: MutableList<TaxCreditItemEntity>): MutableList<TaxCreditItemEntity> {
        val categoryItemMap = mapOf(
            TaxCreditCategory.DEFAULT.code to listOf(
                TaxCreditItem.SME_PERFORMANCE_SHARING.value,
                TaxCreditItem.FAITHFUL_DECLARATION_COST.value,
                TaxCreditItem.SME_SPECIAL_TAX_REDUCTION.value,
                TaxCreditItem.INTEGRATED_PROJECT.value,
            ),
        )

        categoryItemMap.forEach { (category, items) ->
            items.forEach { itemName ->
                dataList.add(taxCreditSetItem(category, itemName))
            }
        }
        return dataList
    }

    fun employmentIncreaseItem(dataList: MutableList<TaxCreditItemEntity>): MutableList<TaxCreditItemEntity> {
        val categoryItemMap = mapOf(
            TaxCreditCategory.EMPLOYMENT_INCREASE.code to listOf(
                TaxCreditItem.EMPLOYMENT_INCREASE.value,
                TaxCreditItem.SOCIAL_INSURANCE.value,
                TaxCreditItem.CAREER_BREAK_FEMALE.value,
                TaxCreditItem.REGULAR_EMPLOYEE_CONVERSION.value,
                TaxCreditItem.EMPLOYMENT_INCREASE.value,
                TaxCreditItem.RETURN_FROM_PARENTAL_LEAVE.value,
            ),
        )

        categoryItemMap.forEach { (category, items) ->
            items.forEach { itemName ->
                dataList.add(taxCreditSetItem(category, itemName))
            }
        }

        return dataList
    }


    fun personalItem(dataList: MutableList<TaxCreditPersonalItemEntity>): MutableList<TaxCreditPersonalItemEntity> {
        val categoryItemMap = mapOf(
            TaxCreditPersonalCategory.PERSONAL.code to listOf(
                TaxCreditPersonalItem.CHILD.value,
                TaxCreditPersonalItem.RETIREMENT_PENSION.value,
                TaxCreditPersonalItem.PENSION_SAVINGS.value,
                TaxCreditPersonalItem.GUARANTEED_INSURANCE_GENERAL.value,
                TaxCreditPersonalItem.GUARANTEED_INSURANCE_DISABILITY.value,
                TaxCreditPersonalItem.MEDICAL_EXPENSES.value,
                TaxCreditPersonalItem.EDUCATION_EXPENSES.value,
                TaxCreditPersonalItem.DONATION.value,
                TaxCreditPersonalItem.STANDARD_DEDUCTION.value,
                TaxCreditPersonalItem.PERSONAL_SMALL_AMOUNT.value,
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