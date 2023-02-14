package net.dv.tax.controller

import mu.KotlinLogging
import net.dv.tax.dto.sales.MedicalBenefitsListDto
import net.dv.tax.service.common.TaxDataLoadAndSaveService
import net.dv.tax.service.sales.MedicalBenefitsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CallDataController(private val taxDataLoadService: TaxDataLoadAndSaveService,
    private val medicalBenefitsService: MedicalBenefitsService) {
    private val log = KotlinLogging.logger {}

    @GetMapping("/get/data")
    fun getTaxDataFromReceiveService(): List<MedicalBenefitsListDto>{
//        taxDataLoadService.getBenefitsData()
        log.error { medicalBenefitsService.getData() }
        return medicalBenefitsService.getData()
    }

}