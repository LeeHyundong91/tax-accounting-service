package net.dv.tax.controller

import mu.KotlinLogging
import net.dv.tax.service.common.TaxDataLoadAndSaveService
import net.dv.tax.service.sales.MedicalBenefitsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CallDataController(
    private val taxDataLoadService: TaxDataLoadAndSaveService,
    private val medicalBenefitsService: MedicalBenefitsService,
) {
    private val log = KotlinLogging.logger {}

    @GetMapping("/get/data")
    fun getTaxDataFromReceiveService(): String {
        taxDataLoadService.getBenefitsData()
        return taxDataLoadService.getBenefitsData().toString()

    }

}