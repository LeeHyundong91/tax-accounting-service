package net.dv.tax.controller

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.domain.sales.VaccineSalesEntity
import net.dv.tax.service.VaccineSalesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sales/vaccine")
class VaccineController(
    private val vaccineSalesService: VaccineSalesService,
) {

    private val log = KotlinLogging.logger {}


    @GetMapping("/list/{year}/{hospitalId}")
    fun vaccineList(@PathVariable year: Int, @PathVariable hospitalId: Int): List<VaccineSalesEntity>? {
        log.error { "$year /$hospitalId" }
        return vaccineSalesService.vaccineYearList(hospitalId, year)
    }

    @PostMapping("/save/{hospitalId}")
    fun vaccineSave(@PathVariable hospitalId: Int, @RequestBody vaccineSalesInfos: List<VaccineSalesEntity>): ResponseEntity<Any>{
        return vaccineSalesService.vaccineSave(hospitalId, vaccineSalesInfos)
    }

    @GetMapping("/download/{hospitalId}")
    fun vaccineYearListExcelDownload(@PathVariable hospitalId: Int, response: HttpServletResponse){
        vaccineSalesService.vaccineListMakeExcel(hospitalId, response)
    }



}