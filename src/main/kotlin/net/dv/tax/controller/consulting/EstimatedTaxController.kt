package net.dv.tax.controller.consulting

import net.dv.tax.domain.consulting.EstimatedTaxEntity
import net.dv.tax.service.consulting.EstimatedTaxService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/consulting/estimated-tax")
class EstimatedTaxController(private val estimatedTaxService: EstimatedTaxService) {

    /*for test*/
    @GetMapping("/test/{year}/{hospitalId}")
    fun makeData(@PathVariable hospitalId: String, @PathVariable year: String) {
        estimatedTaxService.makeData(hospitalId, year)
    }
    @GetMapping("/{year}/{hospitalId}")
    fun getData(@PathVariable hospitalId: String, @PathVariable year: String) : EstimatedTaxEntity{
        return estimatedTaxService.getData(hospitalId, year)
    }

}