package net.dv.tax.controller.purchase

import mu.KotlinLogging
import net.dv.tax.TaxAccountingApplication
import net.dv.tax.dto.purchase.PurchaseElecInvoiceListDto
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.service.purchase.PurchaseElecInvoiceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/${TaxAccountingApplication.VERSION}/purchase/tax-invoice")
class PurchaseElecInvoiceController(
    private val purchaseElecInvoiceService: PurchaseElecInvoiceService,
) {

    private val log = KotlinLogging.logger {}

    //전자 세금계산서 매입관리 항목 조회
    @GetMapping("{hospitalId}/list")
    fun getPurchaseElecInvoice(@PathVariable hospitalId: String, purchaseQueryDto: PurchaseQueryDto): ResponseEntity<PurchaseElecInvoiceListDto> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res = purchaseElecInvoiceService.getPurchaseElecInvoice(hospitalId, purchaseQueryDto)
        return ResponseEntity.ok(res)
    }
}