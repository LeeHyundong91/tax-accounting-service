package net.dv.tax.infra.endpoint.purchase

import mu.KotlinLogging
import net.dv.tax.Application
import net.dv.tax.app.dto.purchase.PurchaseElecInvoiceListDto
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.app.purchase.PurchaseElecInvoiceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/${Application.VERSION}/purchase/elec-invoice")
class PurchaseElecInvoiceController(
    private val purchaseElecInvoiceService: PurchaseElecInvoiceService,
) {

    private val log = KotlinLogging.logger {}

    //전자 세금계산서 매입관리 항목 조회
    @GetMapping("/{hospitalId}/list")
    fun getPurchaseElecInvoice(@PathVariable hospitalId: String, purchaseQueryDto: PurchaseQueryDto): ResponseEntity<PurchaseElecInvoiceListDto> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res = purchaseElecInvoiceService.getPurchaseElecInvoice(hospitalId, purchaseQueryDto)
        return ResponseEntity.ok(res)
    }
}