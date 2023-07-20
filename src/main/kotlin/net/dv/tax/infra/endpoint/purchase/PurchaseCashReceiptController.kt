package net.dv.tax.infra.endpoint.purchase

import mu.KotlinLogging
import net.dv.tax.Application
import net.dv.tax.app.dto.purchase.PurchaseCashReceiptListDto
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.app.purchase.PurchaseCashReceiptService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/${Application.VERSION}/purchase/cash-receipt")
class PurchaseCashReceiptController(
    private val purchaseCashReceiptService: PurchaseCashReceiptService,
) {
    private val log = KotlinLogging.logger {}

    //매입 현금 영수증 항목 조회
    @GetMapping("{hospitalId}/list")
    fun getCashReceiptInvoice(@PathVariable hospitalId: String, purchaseQueryDto: PurchaseQueryDto): ResponseEntity<PurchaseCashReceiptListDto> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res =  purchaseCashReceiptService.getPurchaseCashReceipt(hospitalId, purchaseQueryDto)

        return ResponseEntity.ok(res)
    }
}