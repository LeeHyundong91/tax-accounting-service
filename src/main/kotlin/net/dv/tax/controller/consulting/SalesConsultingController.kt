package net.dv.tax.controller.consulting

import net.dv.tax.domain.consulting.SalesPaymentMethodEntity
import net.dv.tax.service.consulting.SalesPaymentMethodService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/sales")
class SalesConsultingController(private val salesPaymentMethodService: SalesPaymentMethodService) {

    @GetMapping("/payment-method/{year}/{hospitalId}")
    fun getList(@PathVariable hospitalId: String, @PathVariable year: String): SalesPaymentMethodEntity? {
        return salesPaymentMethodService.getData(hospitalId, year)
    }

    @GetMapping("/payment-method/test/{year}/{hospitalId}")
    fun getMakeList(@PathVariable hospitalId: String, @PathVariable year: String) {
        return salesPaymentMethodService.makeData(hospitalId, year)
    }

    @PostMapping("/payment-method/save")
    fun vaccineSave(
        @RequestBody data: SalesPaymentMethodEntity,
    ): ResponseEntity<Any> {
        return salesPaymentMethodService.saveData(data)
    }

}