package net.dv.tax.app.consulting

import net.dv.tax.domain.consulting.TaxCreditEntity
import net.dv.tax.domain.consulting.TaxCreditItemEntity
import net.dv.tax.domain.consulting.TaxCreditPersonalEntity
import net.dv.tax.domain.consulting.TaxCreditPersonalItemEntity
import net.dv.tax.app.dto.consulting.TaxCreditDto
import net.dv.tax.app.enums.consulting.TaxCreditCategory
import net.dv.tax.app.enums.consulting.TaxCreditItem
import net.dv.tax.app.enums.consulting.TaxCreditPersonalCategory
import net.dv.tax.app.enums.consulting.TaxCreditPersonalItem
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TaxCreditService(
    private val taxCreditRepository: TaxCreditRepository,
    private val taxCreditItemRepository: TaxCreditItemRepository,
    private val taxCreditPersonalRepository: TaxCreditPersonalRepository,
    private val taxCreditPersonalItemRepository: TaxCreditPersonalItemRepository,
) {

    fun deletePersonalItem(hospitalId: String, year: String, id: Long): ResponseEntity<HttpStatus> {
        taxCreditPersonalItemRepository.deleteById(id)
        patchItemOption(hospitalId, year, TaxCreditCategory.DEFAULT.code)
        return ResponseEntity(HttpStatus.OK)
    }

    fun updatePersonalItem(
        hospitalId: String,
        year: String,
        taxCreditPersonalId: Long,
        dataList: MutableList<TaxCreditPersonalItemEntity>,
    ): ResponseEntity<HttpStatus> {
        taxCreditPersonalRepository.findById(taxCreditPersonalId).get().let { report ->
            val itemList = report.detailList

            val newThingList = dataList.filter { it.id == null && it.taxCreditPersonalId == taxCreditPersonalId }
                .map { item ->
                    TaxCreditPersonalItemEntity(
                        category = item.category,
                        itemName = item.itemName,
                        lastYearAmount = item.lastYearAmount,
                        currentAccruals = item.currentAccruals,
                        vanishingAmount = item.vanishingAmount,
                        currentDeduction = item.currentDeduction,
                        amountCarriedForward = item.amountCarriedForward,
                    )
                }
            val updatedItemList = itemList + newThingList
            taxCreditPersonalItemRepository.saveAll(newThingList).also { savedData ->
                updatedItemList + savedData
            }

            updatedItemList.forEach { origin ->
                dataList.find { addData -> origin.id == addData.id }
                    ?.let { addData ->
                        origin.lastYearAmount = addData.lastYearAmount
                        origin.currentAccruals = addData.currentAccruals
                        origin.vanishingAmount = addData.vanishingAmount
                        origin.currentDeduction = addData.currentDeduction
                        origin.amountCarriedForward = addData.amountCarriedForward
                    }
            }

            report.detailList.addAll(updatedItemList)
            taxCreditPersonalRepository.save(report)
        }

        patchItemOption(hospitalId, year, TaxCreditCategory.DEFAULT.code)

        return ResponseEntity(HttpStatus.OK)
    }

    /*병원 공제 내역 삭제*/
    fun deleteHospitalItem(hospitalId: String, year: String, id: Long): ResponseEntity<HttpStatus> {
        taxCreditItemRepository.deleteById(id)
        patchItemOption(hospitalId, year, TaxCreditCategory.DEFAULT.code)
        return ResponseEntity(HttpStatus.OK)
    }

    /*병원 공제 항목 추가 및 수정*/
    fun updateHospitalItem(
        dataList: MutableList<TaxCreditItemEntity>,
        hospitalId: String,
        year: String,
    ): ResponseEntity<HttpStatus> {

        taxCreditRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)?.let { report ->
            val itemList = report.detailList

            /*새로운 항목*/
            val newThingList = dataList.filter { it.id == null }
                .map { item ->
                    TaxCreditItemEntity(
                        category = TaxCreditCategory.DEFAULT.code,
                        itemName = item.itemName,
                        itemValue = item.itemValue,
                        itemValue2 = item.itemValue2,
                        itemValue3 = item.itemValue3,
                    )
                }
            val updatedItemList = itemList + newThingList

            taxCreditItemRepository.saveAll(newThingList).also { saveData ->
                updatedItemList + saveData
            }

            /*데이터 치환*/
            updatedItemList.forEach { origin ->
                dataList.find { addData -> origin.id == addData.id }
                    ?.let { addData ->
                        origin.itemValue = addData.itemValue
                        origin.category = addData.category
                        origin.itemValue = addData.itemValue
                        origin.itemValue2 = addData.itemValue2
                        origin.itemValue3 = addData.itemValue3
                    }
            }

            report.detailList.addAll(updatedItemList)
            taxCreditRepository.save(report)
        }
        patchItemOption(hospitalId, year, TaxCreditCategory.DEFAULT.code)
        return ResponseEntity(HttpStatus.OK)
    }


    fun patchItemOption(hospitalId: String, year: String, option: String): ResponseEntity<HttpStatus> {
        taxCreditRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year).also {

            val taxCreditItemList = it!!.detailList

            taxCreditPersonalRepository.findAllByHospitalIdAndResultYearMonthStartingWith(
                hospitalId, year
            ).forEach { item ->
                val personalList =
                    item.detailList.filter { filter -> filter.category == TaxCreditPersonalCategory.PERSONAL.code }
                        .toMutableList()

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
                val totalSumList = mutableListOf<TaxCreditPersonalItemEntity>()

                /*병원 공제 합계*/
                val hospitalSum = sumData(
                    itemList,
                    TaxCreditPersonalCategory.HOSPITAL.code,
                    TaxCreditPersonalItem.HOSPITAL_SMALL_AMOUNT.value
                )
                itemList.add(hospitalSum)
                totalSumList.add(hospitalSum)

                val filterSumId =
                    personalList.filter { filter -> filter.itemName == TaxCreditPersonalItem.PERSONAL_SMALL_AMOUNT.value }[0].id

                personalList.removeIf { item -> item.id == filterSumId }

                /*개인 공제 합계*/
                val personalSum = sumData(
                    personalList,
                    TaxCreditPersonalCategory.PERSONAL.code,
                    TaxCreditPersonalItem.PERSONAL_SMALL_AMOUNT.value
                )
                personalList.add(personalSum)
                totalSumList.add(personalSum)


                itemList.addAll(personalList)

                val totalSum = sumData(
                    totalSumList,
                    TaxCreditPersonalCategory.TOTAL.code,
                    TaxCreditPersonalItem.TOTAL_AMOUNT.value
                )

//                /*이슈*/
//                itemList.forEach { item ->
//                    if (item.category == TaxCreditPersonalCategory.TOTAL.code && item.itemName == TaxCreditPersonalItem.TOTAL_AMOUNT.value) {
//                        item.lastYearAmount = totalSum.lastYearAmount
//                        item.currentAccruals = totalSum.currentAccruals
//                        item.vanishingAmount = totalSum.vanishingAmount
//                        item.currentDeduction = totalSum.currentDeduction
//                        item.amountCarriedForward = totalSum.amountCarriedForward
//                    } else {
//                        itemList.add(totalSum)
//                    }
//                }
                itemList.add(totalSum)
                item.detailList = itemList

                taxCreditPersonalRepository.save(item)
            }

        }
        return ResponseEntity.ok(HttpStatus.OK)
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

    /*병원공제 기본항목 생성*/
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

    /*개인공제 및 병원공제 기본항목 생성
    * 통합고용세액공제 (INTEGRATED_EMPLOYMENT) */
    fun makePersonalData(hospitalId: String, year: String) {

        val taxCreditItemList =
            taxCreditRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)?.detailList
        val directorList = mutableListOf<TaxCreditPersonalEntity>()
        taxCreditPersonalRepository.directorAndStakeList(hospitalId).forEach { item ->

            val personalData = TaxCreditPersonalEntity(
                director = item.director,
                stake = item.stake.toFloat(),
                directorId = item.directorId,
                hospitalId = hospitalId,
                resultYearMonth = year,
                consultingReportId = 1,
            )

            val data = taxCreditPersonalRepository.findAllByHospitalIdAndDirectorAndDirectorId(
                hospitalId,
                item.director,
                item.directorId
            ) ?: taxCreditPersonalRepository.save(personalData)

            data.also {

                val itemList = mutableListOf<TaxCreditPersonalItemEntity>()
                /*개인공제 기본 항목*/
                itemList.addAll(personalItem(itemList))

                taxCreditItemList?.filter { filter ->
                    filter.category == TaxCreditCategory.INTEGRATED_EMPLOYMENT.code || filter.category == TaxCreditCategory.DEFAULT.code
                }?.forEach { personalItem ->

                    val currentAccruals: Long = personalItem.itemValue?.times((it.stake!!.div(100)))!!.toLong()

                    /*병원공제 항목*/
                    itemList.add(
                        personalSetItemWithValue(
                            TaxCreditPersonalCategory.HOSPITAL.code, personalItem.itemName!!, currentAccruals
                        )
                    )
                }

                /*병원공제 합계*/
                itemList.add(
                    sumData(
                        itemList,
                        TaxCreditPersonalCategory.HOSPITAL.code,
                        TaxCreditPersonalItem.HOSPITAL_SMALL_AMOUNT.value
                    )
                )

                it.detailList = itemList
                directorList.add(it)
            }
        }
        taxCreditPersonalRepository.saveAll(directorList)
    }


    fun sumData(
        dataList: MutableList<TaxCreditPersonalItemEntity>,
        category: String,
        itemName: String,
    ): TaxCreditPersonalItemEntity {

        return TaxCreditPersonalItemEntity(
            category = category,
            itemName = itemName,
            lastYearAmount = dataList.sumOf { sum -> sum.lastYearAmount ?: 0 },
            currentAccruals = dataList.sumOf { sum -> sum.currentAccruals ?: 0 },
            vanishingAmount = dataList.sumOf { sum -> sum.vanishingAmount ?: 0 },
            currentDeduction = dataList.sumOf { sum -> sum.currentDeduction ?: 0 },
            amountCarriedForward = dataList.sumOf { sum -> sum.amountCarriedForward ?: 0 },
        )
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
            TaxCreditPersonalCategory.TOTAL.code to listOf(
                TaxCreditPersonalItem.TOTAL_AMOUNT.value,
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