package net.dv.tax.controller

import mu.KotlinLogging
import net.dv.tax.service.common.TaxDataLoadAndSaveService
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
        taxDataLoadService.getBenefitsData()
        taxDataLoadService.getCarInsuranceData()
        taxDataLoadService.getMedicalExamData()
        taxDataLoadService.getEmployeeIndustryData()
        taxDataLoadService.getSalesCreditCardData()
        taxDataLoadService.getSalesCashReceiptData()
        taxDataLoadService.getSalesElecTaxInvoiceData()

        return ResponseEntity(HttpStatus.OK)
    }

}