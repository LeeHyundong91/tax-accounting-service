package net.dv.tax.controller

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.domain.sales.SalesVaccineEntity
import net.dv.tax.service.sales.SalesVaccineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sales/vaccine")
class SalesVaccineController(
    private val salesVaccineService: SalesVaccineService,
) {

    private val log = KotlinLogging.logger {}


    @GetMapping("/list/{year}/{hospitalId}")
    fun vaccineList(@PathVariable year: Int, @PathVariable hospitalId: Int): List<SalesVaccineEntity>? {
        log.error { "$year /$hospitalId" }
        return salesVaccineService.vaccineYearList(hospitalId, year)
    }

    @PostMapping("/save/{hospitalId}")
    fun vaccineSave(@PathVariable hospitalId: Int, @RequestBody vaccineSalesInfos: List<SalesVaccineEntity>): ResponseEntity<Any>{
        return salesVaccineService.vaccineSave(hospitalId, vaccineSalesInfos)
    }

    @GetMapping("/download/{hospitalId}")
    fun vaccineYearListExcelDownload(@PathVariable hospitalId: Int, response: HttpServletResponse){
        salesVaccineService.vaccineListMakeExcel(hospitalId, response)
    }



}