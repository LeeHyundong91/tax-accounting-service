package net.dv.tax.controller.sales

import mu.KotlinLogging
import net.dv.tax.domain.sales.SalesVaccineEntity
import net.dv.tax.dto.sales.SalesVaccineListDto
import net.dv.tax.service.sales.SalesVaccineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/sales/vaccine")
class SalesVaccineController(
    private val salesVaccineService: SalesVaccineService,
) {

    private val log = KotlinLogging.logger {}


    @GetMapping("/list/{year}/{hospitalId}")
    fun vaccineList(@PathVariable year: String, @PathVariable hospitalId: String): SalesVaccineListDto {
        log.error { "$year /$hospitalId" }
        return salesVaccineService.getList(hospitalId, year)
    }

    @PostMapping("/save/{hospitalId}")
    fun vaccineSave(
        @PathVariable hospitalId: String,
        @RequestBody vaccineSalesInfo: List<SalesVaccineEntity>,
    ): ResponseEntity<Any> {
        return salesVaccineService.vaccineSave(hospitalId, vaccineSalesInfo)
    }


}