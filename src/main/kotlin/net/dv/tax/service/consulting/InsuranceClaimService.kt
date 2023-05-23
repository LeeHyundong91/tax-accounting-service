package net.dv.tax.service.consulting

import net.dv.tax.domain.consulting.InsuranceClaimEntity
import net.dv.tax.domain.consulting.InsuranceClaimItemEntity
import net.dv.tax.enums.consulting.SalesTypeItem
import net.dv.tax.repository.consulting.InsuranceClaimRepository
import net.dv.tax.repository.sales.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InsuranceClaimService(
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val medicalCareRepository: MedicalCareRepository,
    private val carInsuranceRepository: CarInsuranceRepository,
    private val healthCareRepository: HealthCareRepository,
    private val salesVaccineRepository: SalesVaccineRepository,
    private val employeeIndustryRepository: EmployeeIndustryRepository,
    private val otherBenefitsRepository: SalesOtherBenefitsRepository,
    private val insuranceClaimRepository: InsuranceClaimRepository,
) {

    fun makeData(hospitalId: String, year: String) {

        val defaultCondition = InsuranceClaimEntity()
        val itemList = mutableListOf<InsuranceClaimItemEntity>()

        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        val data = insuranceClaimRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
            ?: insuranceClaimRepository.save(defaultCondition)


        data.also {

            /*요양급여*/
            val medicalBenefitCorp = medicalBenefitsRepository.monthlyCorpSumAmount(hospitalId, year) ?: 0
            val medicalBenefitOwn = medicalBenefitsRepository.monthlyOwnChargeSumAmount(hospitalId, year) ?: 0

            /*의료급여*/
            val medicalCareCorp = medicalCareRepository.monthlyAgencySumAmount(hospitalId, year) ?: 0
            val medicalCareOwn = medicalCareRepository.monthlyOwnChargeSumAmount(hospitalId, year) ?: 0

            /*자동차 보험*/
            val carInsurance = carInsuranceRepository.monthlySumAmount(hospitalId, year) ?: 0

            /*건강검진*/
            val healthCare = healthCareRepository.monthlySumAmount(hospitalId, year) ?: 0

            /*예방접종*/
            val vaccine = salesVaccineRepository.monthlySumAmount(hospitalId, year) ?: 0

            /*고용산재*/
            val employee = employeeIndustryRepository.monthlySumAmount(hospitalId, year) ?: 0

            /*기타급여 (금연치료, 소견서, 희귀, 기타급여)*/
            val otherBenefits = otherBenefitsRepository.dataList(hospitalId, year)

            otherBenefits.forEach {
                var ownCharge = InsuranceClaimItemEntity(
                    itemName = it.itemName + SalesTypeItem.OWN_CHARGE.value,
                    itemAmount = it.ownCharge
                )
                var corpCharge = InsuranceClaimItemEntity(
                    itemName = it.itemName + SalesTypeItem.CORP_CHARGE.value,
                    itemAmount = it.agencyExpense
                )
                itemList.add(ownCharge)
                itemList.add(corpCharge)
            }

            itemList.add(
                itemSet(
                    SalesTypeItem.MEDICAL_BENEFITS.value + SalesTypeItem.OWN_CHARGE.value,
                    medicalBenefitOwn
                )
            )
            itemList.add(
                itemSet(
                    SalesTypeItem.MEDICAL_BENEFITS.value + SalesTypeItem.CORP_CHARGE.value,
                    medicalBenefitCorp
                )
            )
            itemList.add(itemSet(SalesTypeItem.MEDICAL_CARE.value + SalesTypeItem.OWN_CHARGE.value, medicalCareOwn))
            itemList.add(
                itemSet(
                    SalesTypeItem.MEDICAL_CARE.value + SalesTypeItem.CORP_CHARGE.value,
                    medicalCareCorp
                )
            )
            itemList.add(itemSet(SalesTypeItem.CAR_INSURANCE.value, carInsurance))
            itemList.add(itemSet(SalesTypeItem.HEALTH_CARE.value, healthCare))
            itemList.add(itemSet(SalesTypeItem.VACCINE.value, vaccine))
            itemList.add(itemSet(SalesTypeItem.EMPLOYEE.value, employee))


            val itemTotalAmount = itemList.sumOf { loop -> loop.itemAmount ?: 0 }

            it.totalAmount = itemTotalAmount
            itemList.forEach { loop ->
                loop.itemRatio = loop.itemAmount!!.toFloat().div(itemTotalAmount) * 100
            }

            it.detailList = itemList

            insuranceClaimRepository.save(it)
        }
    }

    @Transactional
    fun getData(hospitalId: String, year: String): InsuranceClaimEntity {
        return insuranceClaimRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)!!
    }


    fun itemSet(itemName: String, amountValue: Long): InsuranceClaimItemEntity {
        return InsuranceClaimItemEntity(
            itemName = itemName,
            itemAmount = amountValue
        )
    }

}