package net.dv.tax.service.feign

import net.dv.tax.domain.sales.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "data-receive-service", url = "localhost:8090/send")
interface DataReceiveFeignService {

    @GetMapping("/medical-benefits")
    fun getMedicalBenefits(): List<MedicalBenefitsEntity>

    @GetMapping("/car-insurance")
    fun getCarInsurance(): List<CarInsuranceEntity>

    @GetMapping("/medical-care")
    fun getMedicalExam(): List<MedicalExamEntity>

    @GetMapping("/industry-insurance")
    fun getEmployeeIndustry(): List<EmployeeIndustryEntity>

    @GetMapping("/credit-card")
    fun getSalesCreditCard(): List<SalesCreditCardEntity>

    @GetMapping("/cash-receipt")
    fun getCashReceipt(): List<SalesCashReceiptEntity>

    @GetMapping("/elec-invoice/ELEC_TAX_SALES_INVOICE")
    fun getElecTaxInvoice(): List<SalesElecInvoiceEntity>

}