package net.dv.tax.controller

import net.dv.tax.domain.sales.HospitalChartEntity
import net.dv.tax.service.HospitalChartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sales/hospital-chart")
class HospitalChartController(private val hospitalChartService: HospitalChartService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun hospitalChartYearList(@PathVariable hospitalId: Int, @PathVariable year: Int): List<HospitalChartEntity>? {
        return hospitalChartService.hospitalChartYearList(hospitalId, year)
    }

    @PostMapping("/save")
    fun hospitalChartSave(@RequestBody hospitalChartList: List<HospitalChartEntity>): ResponseEntity<Any>{
        return hospitalChartService.hospitalChartSave(hospitalChartList)
    }

}