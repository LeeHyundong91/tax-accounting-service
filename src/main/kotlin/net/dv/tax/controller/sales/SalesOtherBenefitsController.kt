package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.SalesOtherBenefitsEntity
import net.dv.tax.dto.sales.SalesOtherBenefitsListDto
import net.dv.tax.service.sales.SalesOtherBenefitsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/sales/other-benefits")
class SalesOtherBenefitsController(private val salesOtherBenefitsService: SalesOtherBenefitsService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun dataList(@PathVariable year: String, @PathVariable hospitalId: String): SalesOtherBenefitsListDto {
        return salesOtherBenefitsService.getList(hospitalId, year)
    }

    @PostMapping("/other-benefits")
    fun saveData(@RequestBody dataList: List<SalesOtherBenefitsEntity>): ResponseEntity<HttpStatus> {
        return salesOtherBenefitsService.saveData(dataList)
    }


}