package net.dv.tax.controller.sales

import net.dv.tax.dto.sales.MedicalBenefitsListDto
import net.dv.tax.service.sales.MedicalBenefitsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/medical-benefits")
class MedicalBenefitsController(private val medicalBenefitsService: MedicalBenefitsService) {

    @GetMapping("/list/{hospitalId}/{year}")
    fun getBenefitsList(@PathVariable year: String, @PathVariable hospitalId: String): List<MedicalBenefitsListDto>{
        return medicalBenefitsService.getListData(hospitalId, year)
    }

}