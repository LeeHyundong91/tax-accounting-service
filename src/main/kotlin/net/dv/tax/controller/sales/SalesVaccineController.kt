package net.dv.tax.controller.sales

import mu.KotlinLogging
import net.dv.tax.domain.sales.SalesVaccineEntity
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
    fun vaccineList(@PathVariable year: Int, @PathVariable hospitalId: String): List<SalesVaccineEntity>? {
        log.error { "$year /$hospitalId" }
        return salesVaccineService.vaccineYearList(hospitalId, year)
    }

    @PostMapping("/save/{hospitalId}")
    fun vaccineSave(@PathVariable hospitalId: String, @RequestBody vaccineSalesInfo: List<SalesVaccineEntity>): ResponseEntity<Any>{
        return salesVaccineService.vaccineSave(hospitalId, vaccineSalesInfo)
    }

//    @GetMapping("/download/{hospitalId}")
//    fun vaccineYearListExcelDownload(@PathVariable hospitalId: String, response: HttpServletResponse){
//        salesVaccineService.vaccineListMakeExcel(hospitalId, response)
//    }



}