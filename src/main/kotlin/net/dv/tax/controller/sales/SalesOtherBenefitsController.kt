package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.SalesOtherBenefitsEntity
import net.dv.tax.service.sales.SalesOtherBenefitsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SalesOtherBenefitsController(private val salesOtherBenefitsService: SalesOtherBenefitsService) {

    @GetMapping("/other-benefits/{hospitalId}/{dataPeriod}")
    fun dataList(@PathVariable dataPeriod: String, @PathVariable hospitalId: String) {
        salesOtherBenefitsService.getList(hospitalId, dataPeriod)
    }

    @PostMapping("/other-benefits")
    fun saveData(@RequestBody dataList: List<SalesOtherBenefitsEntity>): ResponseEntity<HttpStatus> {
        return salesOtherBenefitsService.saveData(dataList)
    }


}