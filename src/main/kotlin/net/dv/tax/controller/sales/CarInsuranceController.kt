package net.dv.tax.controller.sales

import net.dv.tax.dto.sales.CarInsuranceListDto
import net.dv.tax.service.sales.CarInsuranceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/car-insurance")
class CarInsuranceController(private val carInsuranceService: CarInsuranceService) {

    @GetMapping("list/{year}/{hospitalId}")
    fun getCarInsuranceList(@PathVariable hospitalId: String, @PathVariable year: String): List<CarInsuranceListDto> {
        return carInsuranceService.getListData(hospitalId, year)
    }

}