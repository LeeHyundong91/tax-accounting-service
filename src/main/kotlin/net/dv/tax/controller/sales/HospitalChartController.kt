package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.HospitalChartEntity
import net.dv.tax.dto.sales.HospitalChartListDto
import net.dv.tax.service.sales.HospitalChartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/sales/hospital-chart")
class HospitalChartController(private val hospitalChartService: HospitalChartService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun hospitalChartYearList(@PathVariable hospitalId: String, @PathVariable year: String): HospitalChartListDto {
        return hospitalChartService.getList(hospitalId, year)
    }

    @PostMapping("/save")
    fun hospitalChartSave(@RequestBody hospitalChartList: List<HospitalChartEntity>): ResponseEntity<Any>{
        return hospitalChartService.hospitalChartSave(hospitalChartList)
    }

}