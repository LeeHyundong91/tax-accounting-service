package net.dv.tax.service.common

import mu.KotlinLogging
import net.dv.tax.repository.sales.CarInsuranceRepository
import net.dv.tax.repository.sales.MedicalBenefitsRepository
import net.dv.tax.repository.sales.MedicalExamRepository
import net.dv.tax.service.feign.DataReceiveFeignService
import org.springframework.stereotype.Component

@Component
class TaxDataLoadAndSaveService(
    private val dataReceiveFeignService: DataReceiveFeignService,
    private val medicalExamRepository: MedicalExamRepository,
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val carInsuranceRepository: CarInsuranceRepository,
) {

    private val log = KotlinLogging.logger {}

    fun getBenefitsData() {
        medicalBenefitsRepository.saveAll(dataReceiveFeignService.getMedicalBenefits())
    }

    fun getCarInsuranceData(){
        carInsuranceRepository.saveAll(dataReceiveFeignService.getCarInsurance())
    }

    fun getMedicalExamData(){
        medicalExamRepository.saveAll(dataReceiveFeignService.getMedicalExam())
    }

}