package net.dv.tax.infra.endpoint.consulting

import net.dv.tax.domain.consulting.TaxExemptionEntity
import net.dv.tax.domain.consulting.TaxExemptionItemEntity
import net.dv.tax.app.consulting.TaxExemptionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/tax-exemption")
class TaxExemptionController(
    private val taxExemptionService: TaxExemptionService,
    ) {
    @GetMapping("/{year}/{hospitalId}")
    fun make(@PathVariable hospitalId: String, @PathVariable year: String): TaxExemptionEntity? {
        return taxExemptionService.makeData(hospitalId, year)
    }

    @PutMapping("/cash/{year}/{hospitalId}")
    fun taxExemptionCashUpdate(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @RequestBody
        itemList: MutableList<TaxExemptionItemEntity>,
    ): TaxExemptionEntity {
        return taxExemptionService.updateCashData(hospitalId, year, itemList) ?: TaxExemptionEntity()
    }

    @PutMapping("/ratio/{year}/{hospitalId}")
    fun taxExemptionRatioUpdate(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @RequestBody
        itemList: MutableList<TaxExemptionItemEntity>,
    ): TaxExemptionEntity {
        return taxExemptionService.updateRatioData(hospitalId, year, itemList) ?: TaxExemptionEntity()
    }

    @GetMapping("/cash/{year}/{hospitalId}")
    fun taxExemptionList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
    ): TaxExemptionEntity {
        return taxExemptionService.getData(hospitalId, year) ?: TaxExemptionEntity()
    }
}