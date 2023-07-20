package net.dv.tax.infra.endpoint.sales

import net.dv.tax.domain.sales.CarInsuranceEntity
import net.dv.tax.app.dto.sales.CarInsuranceListDto
import net.dv.tax.app.sales.CarInsuranceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/car-insurance")
class CarInsuranceController(private val carInsuranceService: CarInsuranceService) {

    @GetMapping("list/{year}/{hospitalId}")
    fun getCarInsuranceList(@PathVariable hospitalId: String, @PathVariable year: String): CarInsuranceListDto {
        return carInsuranceService.getList(hospitalId, year)
    }

    @GetMapping("/{yearMonth}/{hospitalId}")
    fun getDetailList(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        @PageableDefault(size = 30) page: Pageable,
    ): Page<CarInsuranceEntity> {
        return carInsuranceService.getDetailList(hospitalId, yearMonth, page)

    }

}