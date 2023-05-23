package net.dv.tax.controller.consulting

import net.dv.tax.domain.consulting.InsuranceClaimEntity
import net.dv.tax.domain.consulting.SalesPaymentMethodEntity
import net.dv.tax.service.consulting.InsuranceClaimService
import net.dv.tax.service.consulting.SalesPaymentMethodService
import net.dv.tax.service.consulting.SalesTypeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/sales")
class SalesConsultingController(
    private val salesPaymentMethodService: SalesPaymentMethodService,
    private val salesTypeService: SalesTypeService,
    private val insuranceClaimService: InsuranceClaimService,
) {

    @GetMapping("/payment-method/{year}/{hospitalId}")
    fun getPaymentMethodList(@PathVariable hospitalId: String, @PathVariable year: String): SalesPaymentMethodEntity? {
        return salesPaymentMethodService.getData(hospitalId, year)
    }

    @GetMapping("/payment-method/test/{year}/{hospitalId}")
    fun makePaymentMethodList(@PathVariable hospitalId: String, @PathVariable year: String) {
        return salesPaymentMethodService.makeData(hospitalId, year)
    }

//    @PostMapping("/payment-method/save")
//    fun paymentMethodSave(
//        @RequestBody data: SalesPaymentMethodEntity,
//    ): ResponseEntity<Any> {
//        return salesPaymentMethodService.saveData(data)
//    }

    @GetMapping("/sales-type/test/{year}/{hospitalId}")
    fun makeSalesTypeList(@PathVariable hospitalId: String, @PathVariable year: String) {
        return salesTypeService.makeData(hospitalId, year)
    }

    @GetMapping("/insurance-claim/test/{year}/{hospitalId}")
    fun makeInsuranceClaimList(@PathVariable hospitalId: String, @PathVariable year: String) {
        return insuranceClaimService.makeData(hospitalId, year)
    }

    @GetMapping("/insurance-claim/{year}/{hospitalId}")
    fun getInsuranceClaimList(@PathVariable hospitalId: String, @PathVariable year: String): InsuranceClaimEntity? {
        return insuranceClaimService.getData(hospitalId, year)
    }


}