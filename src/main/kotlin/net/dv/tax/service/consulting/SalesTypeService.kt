package net.dv.tax.service.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.SalesTypeEntity
import net.dv.tax.domain.consulting.SalesTypeItemEntity
import net.dv.tax.enums.consulting.SalesTypeItem
import net.dv.tax.repository.consulting.SalesTypeRepository
import net.dv.tax.repository.sales.*
import net.dv.tax.service.sales.SalesVaccineService
import org.springframework.stereotype.Service

@Service
class SalesTypeService(
    private val salesTypeRepository: SalesTypeRepository,
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val medicalCareRepository: MedicalCareRepository,
    private val carInsuranceRepository: CarInsuranceRepository,
    private val healthCareRepository: HealthCareRepository,
    private val vaccineService: SalesVaccineService,
    private val employeeIndustryRepository: EmployeeIndustryRepository,
    private val salesOtherBenefitsRepository: SalesOtherBenefitsRepository,
) {
    private val log = KotlinLogging.logger {}

    fun makeData(hospitalId: String, year: String) {

        val defaultCondition = SalesTypeEntity()
        val itemList = mutableListOf<SalesTypeItemEntity>()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        val data =
            salesTypeRepository.findTopByHospitalIdAndResultYearMonth(hospitalId, year) ?: salesTypeRepository.save(
                defaultCondition
            )

        data.also {

            /*자동차보험*/
            val carInsurance = carInsuranceRepository.monthlySumAmount(hospitalId, year) ?: 0
            /*예방접종*/
            val vaccine = vaccineService.getList(hospitalId, year).listTotal?.payAmount ?: 0
            /*산업, 고용보험*/
            val employee = employeeIndustryRepository.monthlySumAmount(hospitalId, year) ?: 0
            /*건강검진*/
            val healthCare = healthCareRepository.monthlySumAmount(hospitalId, year) ?: 0

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
            itemList.add(itemSet(SalesTypeItem.CAR_INSURANCE.value, carInsurance))
            itemList.add(itemSet(SalesTypeItem.VACCINE.value, vaccine))
            itemList.add(itemSet(SalesTypeItem.EMPLOYEE.value, employee))
            itemList.add(itemSet(SalesTypeItem.HEALTH_CARE.value, healthCare))

            /*기타급여 - 금연치료,소견서,희귀,기타*/
            salesOtherBenefitsRepository.dataList(hospitalId, year).forEach {
                val item = SalesTypeItemEntity(
                    itemName = it.itemName,
                    itemOwnChargeAmount = it.ownCharge,
                    itemCorpChargeAmount = it.agencyExpense,
                    groupAmount = it.totalAmount
                )
                itemList.add(item)
            }

            /*일반매출 : 요양급여 소계 - 다른 소계전부*/
            val normalSalesAmount = medicalBenefitGroupAmount.minus(itemList.sumOf { it.groupAmount!! })

            /*일반 매출*/
            itemList.add(itemSet(SalesTypeItem.NORMAL.value, normalSalesAmount))

            /*일반매출 계산을 위해 요양급여는 제일 마지막에 더함*/
            itemList.add(medicalBenefits)

            /*최종 합계*/
            val itemTotalAmount = itemList.sumOf { it.groupAmount!! }
            it.totalAmount = itemTotalAmount
            it.totalRatio = 100.0.toFloat()

            itemList.forEach { item ->
                item.itemCorpChargeRatio = item.itemCorpChargeAmount!!.toFloat().div(itemTotalAmount) * 100
                item.itemOwnChargeRatio = item.itemOwnChargeAmount!!.toFloat().div(itemTotalAmount) * 100
                item.groupRatio = item.groupAmount!!.toFloat().div(itemTotalAmount) * 100
            }

            it.detailList = itemList

            salesTypeRepository.save(it)
        }
    }

    fun itemSet(itemName: String, amountValue: Long): SalesTypeItemEntity {
        return SalesTypeItemEntity(
            itemName = itemName,
            groupAmount = amountValue
        )
    }

}