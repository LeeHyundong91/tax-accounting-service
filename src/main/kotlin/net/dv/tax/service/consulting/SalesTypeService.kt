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

        if (salesTypeRepository.countAllByHospitalIdAndResultYearMonth(hospitalId, year).toInt() == 0) {
            val data = SalesTypeEntity(
                hospitalId = hospitalId,
                resultYearMonth = year
            )
            salesTypeRepository.save(data)

        } else {
            val data = salesTypeRepository.findTopByHospitalIdAndResultYearMonth(hospitalId, year)
            val detailItemList = mutableListOf<SalesTypeItemEntity>()


            val carInsurance = carInsuranceRepository.monthlySumAmount(hospitalId, year) ?: 0
            val vaccine = vaccineService.getList(hospitalId, year).listTotal?.payAmount ?: 0
            val employee = employeeIndustryRepository.monthlySumAmount(hospitalId, year) ?: 0
            val healthCare = healthCareRepository.monthlySumAmount(hospitalId, year) ?: 0

            detailItemList.add(itemSet(SalesTypeItem.CAR_INSURANCE.value, carInsurance))
            detailItemList.add(itemSet(SalesTypeItem.VACCINE.value, vaccine))
            detailItemList.add(itemSet(SalesTypeItem.EMPLOYEE.value, employee))
            detailItemList.add(itemSet(SalesTypeItem.HEALTH_CARE.value, healthCare))

            salesOtherBenefitsRepository.dataList(hospitalId, year).forEach {
                val item = SalesTypeItemEntity(
                    itemName = it.itemName,
                    itemOwnChargeAmount = it.ownCharge,
                    itemCorpChargeAmount = it.agencyExpense,
                    groupAmount = it.totalAmount
                )
                detailItemList.add(item)
            }

            data.detailList = detailItemList
            log.error { data.detailList }
            log.error { salesOtherBenefitsRepository.dataList(hospitalId, year) }


            data.totalAmount = detailItemList.sumOf { it.groupAmount!! }
            data.totalRatio = 100.0.toFloat()

            detailItemList.forEach {
                it.itemCorpChargeRatio = it.itemCorpChargeAmount!!.toFloat().div(data.totalAmount!!) * 100
                it.itemOwnChargeRatio = it.itemOwnChargeAmount!!.toFloat().div(data.totalAmount!!) * 100
                it.groupRatio = it.groupAmount!!.toFloat().div(data.totalAmount!!) * 100
            }


            salesTypeRepository.save(data)
        }

    }

    fun itemSet(itemName: String, amountValue: Long): SalesTypeItemEntity {
        return SalesTypeItemEntity(
            itemName = itemName,
            groupAmount = amountValue
        )
    }

}