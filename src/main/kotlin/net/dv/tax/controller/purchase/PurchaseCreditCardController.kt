package net.dv.tax.controller.purchase

import mu.KotlinLogging
import net.dv.tax.TaxAccountingApplication
import net.dv.tax.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.service.purchase.PurchaseCreditCardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/${TaxAccountingApplication.VERSION}/purchase/credit-card")
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