package net.dv.tax.service.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.SalesTypeEntity
import net.dv.tax.domain.consulting.SalesTypeItemEntity
import net.dv.tax.enums.consulting.SalesTypeItem
import net.dv.tax.repository.consulting.SalesTypeRepository
import net.dv.tax.repository.sales.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SalesTypeService(
    private val salesTypeRepository: SalesTypeRepository,
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val medicalCareRepository: MedicalCareRepository,
    private val carInsuranceRepository: CarInsuranceRepository,
    private val healthCareRepository: HealthCareRepository,
    private val vaccineRepository: SalesVaccineRepository,
    private val employeeIndustryRepository: EmployeeIndustryRepository,
    private val salesOtherBenefitsRepository: SalesOtherBenefitsRepository,
) {
    private val log = KotlinLogging.logger {}

    @Transactional
    fun getData(hospitalId: String, year: String): SalesTypeEntity? {
        return salesTypeRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: SalesTypeEntity()
    }

    fun saveData(hospitalId: String, year: String) {
        salesTypeRepository.save(makeData(hospitalId, year))
    }


    fun makeData(hospitalId: String, year: String): SalesTypeEntity {

        val defaultCondition = SalesTypeEntity()
        val itemList = mutableListOf<SalesTypeItemEntity>()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        val data =
            salesTypeRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
                ?: salesTypeRepository.save(defaultCondition)

        data.also {

            /*자동차보험*/
            val carInsurance = carInsuranceRepository.monthlySumAmount(hospitalId, year) ?: 0
            itemList.add(itemSet(SalesTypeItem.CAR_INSURANCE.value, carInsurance))

            /*예방접종*/
            val vaccine = vaccineRepository.monthlySumAmount(hospitalId, year) ?: 0
            itemList.add(itemSet(SalesTypeItem.VACCINE.value, vaccine))

            /*산업, 고용보험*/
            val employee = employeeIndustryRepository.monthlySumAmount(hospitalId, year) ?: 0
            itemList.add(itemSet(SalesTypeItem.EMPLOYEE.value, employee))

            /*건강검진*/
            val healthCare = healthCareRepository.monthlySumAmount(hospitalId, year) ?: 0
            itemList.add(itemSet(SalesTypeItem.HEALTH_CARE.value, healthCare))


            /*요양급여*/
            val medicalBenefitCorpChargeAmount = medicalBenefitsRepository.monthlyCorpSumAmount(hospitalId, year) ?: 0
            val medicalBenefitOwnChargeAmount =
                medicalBenefitsRepository.monthlyOwnChargeSumAmount(hospitalId, year) ?: 0
            val medicalBenefitGroupAmount = medicalBenefitCorpChargeAmount.plus(medicalBenefitOwnChargeAmount)
            val medicalBenefits = SalesTypeItemEntity(
                itemName = SalesTypeItem.MEDICAL_BENEFITS.value,
                itemCorpChargeAmount = medicalBenefitCorpChargeAmount,
                itemOwnChargeAmount = medicalBenefitOwnChargeAmount,
                groupAmount = medicalBenefitGroupAmount
            )

            /* 의료급여 */
            val medicalCareCorpAmount = medicalCareRepository.monthlyAgencySumAmount(hospitalId, year) ?: 0
            val medicalCareOwnChargeAmount = medicalCareRepository.monthlyOwnChargeSumAmount(hospitalId, year) ?: 0
            val medicalCareGroupAmount = medicalCareCorpAmount.plus(medicalCareOwnChargeAmount)
            val medicalCare = SalesTypeItemEntity(
                itemName = SalesTypeItem.MEDICAL_CARE.value,
                itemCorpChargeAmount = medicalCareCorpAmount,
                itemOwnChargeAmount = medicalCareOwnChargeAmount,
                groupAmount = medicalCareGroupAmount
            )

            itemList.add(medicalCare)

            /*기타급여 - 금연치료,소견서,희귀,기타*/
            salesOtherBenefitsRepository.dataList(hospitalId, year).forEach { item ->
                val otherBenefits = SalesTypeItemEntity(
                    itemName = item.itemName,
                    itemOwnChargeAmount = item.ownCharge,
                    itemCorpChargeAmount = item.agencyExpense,
                    groupAmount = item.totalAmount
                )
                itemList.add(otherBenefits)
            }

            /*일반매출 : 요양급여 소계 - 다른 소계전부*/
            val normalSalesAmount = medicalBenefitGroupAmount.minus(itemList.sumOf { item -> item.groupAmount!! })

            /*일반 매출*/
            itemList.add(itemSet(SalesTypeItem.NORMAL.value, normalSalesAmount))

            /*일반매출 계산을 위해 요양급여는 제일 마지막에 더함*/
            itemList.add(medicalBenefits)

            /*최종 합계*/
            val itemTotalAmount = itemList.sumOf { item -> item.groupAmount!! }
            it.totalAmount = itemTotalAmount
            it.totalRatio = 100.0.toFloat()

            itemList.forEach { item ->
                item.itemCorpChargeRatio = item.itemCorpChargeAmount!!.toFloat().div(itemTotalAmount) * 100
                item.itemOwnChargeRatio = item.itemOwnChargeAmount!!.toFloat().div(itemTotalAmount) * 100
                item.groupRatio = item.groupAmount!!.toFloat().div(itemTotalAmount) * 100
            }

            it.detailList = itemList

        }

        return data
    }

    fun itemSet(itemName: String, amountValue: Long): SalesTypeItemEntity {
        return SalesTypeItemEntity(
            itemName = itemName,
            groupAmount = amountValue
        )
    }

}