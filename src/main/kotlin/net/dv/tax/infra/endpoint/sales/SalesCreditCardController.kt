package net.dv.tax.infra.endpoint.sales

import net.dv.tax.app.dto.sales.SalesCreditCardListDto
import net.dv.tax.app.sales.SalesCreditCardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/credit-card")
class SalesCreditCardController(private val creditCardService: SalesCreditCardService) {


    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
    ): SalesCreditCardListDto {
        return creditCardService.getList(hospitalId, year)
    }

//    @GetMapping("list/{year}/{hospitalId}")
//    fun getCreditCardList(@PathVariable hospitalId: String, @PathVariable year: String): List<SalesCreditCardListDto> {
//        return creditCardService.getListData(hospitalId, year)
//    }

}