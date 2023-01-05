package net.dv.tax.controller

import mu.KotlinLogging
import net.dv.tax.domain.VaccineSalesEntity
import net.dv.tax.service.VaccineSalesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/purchase/vaccine")
class VaccineController(private val vaccineSalesService: VaccineSalesService) {

    private val log = KotlinLogging.logger {}


    @GetMapping("/list/{year}/{hospitalId}")
    fun vaccineList(@PathVariable year: String, @PathVariable hospitalId: Int): List<VaccineSalesEntity>? {
        log.error { "$year /$hospitalId" }
        return vaccineSalesService.vaccineYearList(hospitalId, year)
    }

    @PostMapping("/save")
    fun vaccineSave(@PathVariable hospitalId: Int, @RequestBody vaccineSalesInfos: List<VaccineSalesEntity>): ResponseEntity<Any>{
        return vaccineSalesService.vaccineSave(hospitalId, vaccineSalesInfos)
    }

}