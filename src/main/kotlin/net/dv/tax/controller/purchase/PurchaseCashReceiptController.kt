package net.dv.tax.controller.purchase

import mu.KotlinLogging
import net.dv.tax.TaxAccountingApplication
import net.dv.tax.dto.purchase.PurchaseCashReceiptListDto
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.service.purchase.PurchaseCashReceiptService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/${TaxAccountingApplication.VERSION}/purchase/cash-receipt")
class PurchaseCashReceiptController(
    private val purchaseCashReceiptService: PurchaseCashReceiptService,
) {
    private val log = KotlinLogging.logger {}

    //신용카드매입관리 항목 조회
    @GetMapping("{hospitalId}/list")
    fun getCashReceiptInvoice(@PathVariable hospitalId: String, purchaseQueryDto: PurchaseQueryDto): ResponseEntity<PurchaseCashReceiptListDto> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res =  purchaseCashReceiptService.getPurchaseCashReceipt(hospitalId, purchaseQueryDto)

        return ResponseEntity.ok(res)
    }
}