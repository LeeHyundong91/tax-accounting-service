package net.dv.tax.controller.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.*
import net.dv.tax.service.consulting.InsuranceClaimService
import net.dv.tax.service.consulting.SalesPaymentMethodService
import net.dv.tax.service.consulting.SalesTypeService
import net.dv.tax.service.consulting.TaxExemptionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/sales")
class SalesConsultingController(
    private val salesPaymentMethodService: SalesPaymentMethodService,
    private val salesTypeService: SalesTypeService,
    private val insuranceClaimService: InsuranceClaimService,
    private val taxExemptionService: TaxExemptionService,
) {
    private val log = KotlinLogging.logger {}

    @GetMapping("/payment-method/{year}/{hospitalId}")
    fun getPaymentMethodList(@PathVariable hospitalId: String, @PathVariable year: String): SalesPaymentMethodEntity? {
        return salesPaymentMethodService.getData(hospitalId, year)
    }

    @GetMapping("/payment-method/test/{year}/{hospitalId}")
    fun makePaymentMethodList(@PathVariable hospitalId: String, @PathVariable year: String) {
        return salesPaymentMethodService.saveData(hospitalId, year)
    }

    @GetMapping("/sales-type/{year}/{hospitalId}")
    fun getSalesTypeList(@PathVariable hospitalId: String, @PathVariable year: String): SalesTypeEntity? {
        return salesTypeService.getData(hospitalId, year)
    }

    @GetMapping("/sales-type/test/{year}/{hospitalId}")
    fun makeSalesTypeList(@PathVariable hospitalId: String, @PathVariable year: String) {
        return salesTypeService.saveData(hospitalId, year)
    }

    @GetMapping("/insurance-claim/test/{year}/{hospitalId}")
    fun makeInsuranceClaimList(@PathVariable hospitalId: String, @PathVariable year: String) {
        return insuranceClaimService.makeData(hospitalId, year)
    }

    @GetMapping("/insurance-claim/{year}/{hospitalId}")
    fun getInsuranceClaimList(@PathVariable hospitalId: String, @PathVariable year: String): InsuranceClaimEntity? {
        return insuranceClaimService.getData(hospitalId, year)
    }

    @GetMapping("/tax-exemption/{year}/{hospitalId}")
    fun make(@PathVariable hospitalId: String, @PathVariable year: String): TaxExemptionEntity? {
        return taxExemptionService.makeData(hospitalId, year)
    }


    @PutMapping("/tax-exemption/cash/{year}/{hospitalId}")
    fun taxExemptionCashUpdate(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @RequestBody
        itemList: MutableList<TaxExemptionItemEntity>,
    ): TaxExemptionEntity {
        return taxExemptionService.updateCashData(hospitalId, year, itemList) ?: TaxExemptionEntity()
    }

    @PutMapping("/tax-exemption/ratio/{year}/{hospitalId}")
    fun taxExemptionRatioUpdate(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @RequestBody
        itemList: MutableList<TaxExemptionItemEntity>,
    ): TaxExemptionEntity {
        return taxExemptionService.updateRatioData(hospitalId, year, itemList) ?: TaxExemptionEntity()
    }

    @GetMapping("/tax-exemption/cash/{year}/{hospitalId}")
    fun taxExemptionList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
    ): TaxExemptionEntity {
        return taxExemptionService.getData(hospitalId, year) ?: TaxExemptionEntity()
    }


}