package net.dv.tax.infra.endpoint.sales

import net.dv.tax.domain.sales.MedicalBenefitsEntity
import net.dv.tax.app.dto.sales.MedicalBenefitsListDto
import net.dv.tax.app.sales.MedicalBenefitsService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/medical-benefits")
class MedicalBenefitsController(private val medicalBenefitsService: MedicalBenefitsService) {


    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
    ): MedicalBenefitsListDto {
        return medicalBenefitsService.getList(hospitalId, year)
    }

    @GetMapping("/{yearMonth}/{hospitalId}")
    fun getDetailList(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        @PageableDefault(size = 30) page: Pageable,
    ): Page<MedicalBenefitsEntity> {
        return medicalBenefitsService.getDetailList(hospitalId, yearMonth, page)

    }
}