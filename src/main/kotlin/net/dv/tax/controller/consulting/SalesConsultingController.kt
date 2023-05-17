package net.dv.tax.controller.consulting

import net.dv.tax.domain.consulting.SalesPaymentMethodEntity
import net.dv.tax.service.consulting.SalesPaymentMethodService
import net.dv.tax.service.consulting.SalesTypeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/sales")
class SalesConsultingController(private val salesPaymentMethodService: SalesPaymentMethodService,
    private val salesTypeService: SalesTypeService) {

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


}