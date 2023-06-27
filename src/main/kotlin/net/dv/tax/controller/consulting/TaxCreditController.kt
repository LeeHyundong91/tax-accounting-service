package net.dv.tax.controller.consulting

import net.dv.tax.dto.consulting.TaxCreditDto
import net.dv.tax.service.consulting.TaxCreditService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/tax-credit")
class TaxCreditController(private val taxCreditService: TaxCreditService) {

    /*for test*/
    @GetMapping("/test/{year}/{hospitalId}")
    fun makeData(@PathVariable hospitalId: String, @PathVariable year: String) {
        taxCreditService.saveData(hospitalId, year)
    }

    @GetMapping("/{year}/{hospitalId}")
    fun getData(@PathVariable hospitalId: String, @PathVariable year: String): TaxCreditDto {
        return taxCreditService.getData(hospitalId, year)
    }

    @PatchMapping("/{year}/{hospitalId}/{option}")
    fun patchOption(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @PathVariable option: String,
    ) {
        return taxCreditService.patchItemOption(hospitalId, year, option)
    }


}