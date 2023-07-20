package net.dv.tax.infra.endpoint.purchase

import mu.KotlinLogging
import net.dv.tax.Application
import net.dv.tax.app.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.app.purchase.PurchaseCreditCardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/${Application.VERSION}/purchase/credit-card")
class PurchaseCreditCardController(
    private val purchaseCreditCardService: PurchaseCreditCardService,
) {
    private val log = KotlinLogging.logger {}

    //신용카드매입관리 항목 조회
    @GetMapping("{hospitalId}/list")
    fun getCreditCardInvoice(@PathVariable hospitalId: String, purchaseQueryDto: PurchaseQueryDto): ResponseEntity<PurchaseCreditCardListDto> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res =  purchaseCreditCardService.getPurchaseCreditCard(hospitalId, purchaseQueryDto)

        return ResponseEntity.ok(res)
    }
}