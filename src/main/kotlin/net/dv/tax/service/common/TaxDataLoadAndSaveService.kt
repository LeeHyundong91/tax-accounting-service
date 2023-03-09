package net.dv.tax.service.common

import mu.KotlinLogging
import net.dv.tax.repository.sales.*
import net.dv.tax.service.feign.DataReceiveFeignService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TaxDataLoadAndSaveService(
    private val dataReceiveFeignService: DataReceiveFeignService,
    private val medicalExamRepository: MedicalExamRepository,
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val carInsuranceRepository: CarInsuranceRepository,
    private val employeeIndustryRepository: EmployeeIndustryRepository,
    private val salesCreditCardRepository: SalesCreditCardRepository,
    private val salesCashReceiptRepository: SalesCashReceiptRepository,
    private val salesElecInvoiceRepository: SalesElecInvoiceRepository,
) {

    private val log = KotlinLogging.logger {}

    fun getBenefitsData() {
        medicalBenefitsRepository.saveAll(dataReceiveFeignService.getMedicalBenefits())
    }

    fun getCarInsuranceData() {
        carInsuranceRepository.saveAll(dataReceiveFeignService.getCarInsurance())
    }

    fun getMedicalExamData() {
        medicalExamRepository.saveAll(dataReceiveFeignService.getMedicalExam())
    }

    fun getEmployeeIndustryData() {
        employeeIndustryRepository.saveAll(dataReceiveFeignService.getEmployeeIndustry())
    }

    fun getSalesCreditCardData() {
        salesCreditCardRepository.saveAll(dataReceiveFeignService.getSalesCreditCard())
    }

    fun getSalesCashReceiptData() {
        salesCashReceiptRepository.saveAll(dataReceiveFeignService.getCashReceipt())
    }

    fun getSalesElecTaxInvoiceData() {
        salesElecInvoiceRepository.saveAll(dataReceiveFeignService.getElecTaxInvoice())
    }

    @Throws(Exception::class)
    @Scheduled(cron = "0 * * * * *")
    fun callScheduleModule() {
        log.info { "Tax Data Save In Progress ------------" }
        medicalBenefitsRepository.saveAll(dataReceiveFeignService.getMedicalBenefits())
        carInsuranceRepository.saveAll(dataReceiveFeignService.getCarInsurance())
        medicalExamRepository.saveAll(dataReceiveFeignService.getMedicalExam())
        employeeIndustryRepository.saveAll(dataReceiveFeignService.getEmployeeIndustry())
        salesCreditCardRepository.saveAll(dataReceiveFeignService.getSalesCreditCard())
        salesCashReceiptRepository.saveAll(dataReceiveFeignService.getCashReceipt())
        salesElecInvoiceRepository.saveAll(dataReceiveFeignService.getElecTaxInvoice())
        log.info { "End Of Scheduled ------------" }
    }

}