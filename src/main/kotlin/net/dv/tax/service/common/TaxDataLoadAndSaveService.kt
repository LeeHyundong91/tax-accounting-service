package net.dv.tax.service.common

import mu.KotlinLogging
import net.dv.tax.repository.sales.*
import net.dv.tax.service.feign.DataReceiveFeignService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TaxDataLoadAndSaveService(
    private val dataReceiveFeignService: DataReceiveFeignService,
    private val carInsuranceRepository: CarInsuranceRepository,
    private val employeeIndustryRepository: EmployeeIndustryRepository,
    private val salesCreditCardRepository: SalesCreditCardRepository,
    private val salesCashReceiptRepository: SalesCashReceiptRepository,
    private val salesElecInvoiceRepository: SalesElecInvoiceRepository,
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val medicalCareRepository: MedicalCareRepository,
    private val healthCareRepository: HealthCareRepository,
) {

    private val log = KotlinLogging.logger {}

    fun getMedicalBenefitsData() {
        medicalBenefitsRepository.saveAll(dataReceiveFeignService.getMedicalBenefits())
    }

    fun getMedicalCareData() {
        medicalCareRepository.saveAll(dataReceiveFeignService.getMedicalCare())
    }

    fun getHealthCareData() {
        healthCareRepository.saveAll(dataReceiveFeignService.getHealthCare())
    }

    fun getCarInsuranceData() {
        carInsuranceRepository.saveAll(dataReceiveFeignService.getCarInsurance())
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
        try {
            log.info { "Tax Data Save In Progress ------------" }
            getMedicalBenefitsData()
            getMedicalCareData()
            getHealthCareData()
            getCarInsuranceData()
            getEmployeeIndustryData()
            getSalesCreditCardData()
            getSalesCashReceiptData()
            getSalesElecTaxInvoiceData()
            log.info { "End Of Scheduled ------------" }
        } catch (e: Exception) {
        }
    }

}