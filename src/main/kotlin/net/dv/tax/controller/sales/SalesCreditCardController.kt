package net.dv.tax.controller.sales

import net.dv.tax.dto.sales.SalesCreditCardListDto
import net.dv.tax.service.sales.SalesCreditCardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/credit-card")
class SalesCreditCardController(private val creditCardService: SalesCreditCardService) {

    @GetMapping("list/{year}/{hospitalId}")
    fun getCreditCardList(@PathVariable hospitalId: String, @PathVariable year: String): List<SalesCreditCardListDto> {
        return creditCardService.getSalesCreditCardList(hospitalId, year)
    }

}