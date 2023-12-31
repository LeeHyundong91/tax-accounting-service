package net.dv.tax.infra.endpoint

import mu.KotlinLogging
import net.dv.tax.app.common.TaxDataLoadAndSaveService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CallDataController(
    private val taxDataLoadService: TaxDataLoadAndSaveService,
) {
    private val log = KotlinLogging.logger {}

    @GetMapping("/get/data")
    fun getTaxDataFromReceiveService(): ResponseEntity<HttpStatus> {
        taxDataLoadService.getCarInsuranceData()
        taxDataLoadService.getMedicalBenefitsData()
        taxDataLoadService.getMedicalCareData()
        taxDataLoadService.getHealthCareData()
        taxDataLoadService.getEmployeeIndustryData()
        taxDataLoadService.getSalesCreditCardData()
        taxDataLoadService.getSalesCashReceiptData()
        taxDataLoadService.getSalesElecTaxInvoiceData()
        taxDataLoadService.getSalesAgentData()
        taxDataLoadService.getSalesElecInvoiceData()

        return ResponseEntity(HttpStatus.OK)
    }

}