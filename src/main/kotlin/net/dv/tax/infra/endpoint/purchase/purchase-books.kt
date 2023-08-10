package net.dv.tax.infra.endpoint.purchase

import mu.KotlinLogging
import net.dv.tax.Application
import net.dv.tax.app.dto.purchase.PurchaseCashReceiptListDto
import net.dv.tax.app.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.app.dto.purchase.PurchaseElecInvoiceListDto
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.app.purchase.*
import net.dv.tax.domain.purchase.PurchaseHandwrittenEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/${Application.VERSION}/purchase")
class PurchaseBooksEndpoints(
    private val purchaseCashReceiptService: PurchaseCashReceiptService,
    private val purchaseCreditCardService: PurchaseCreditCardService,
    private val purchaseElecInvoiceService: PurchaseElecInvoiceService,
    private val handwrittenService: PurchaseHandwrittenService,
    private val command: PurchaseQueryCommand,
) {
    private val log = KotlinLogging.logger {}

    /** 신용카드매입관리 항목 조회 */
    @GetMapping("/credit-card/{hospitalId}/list", "/credit-card/{hospitalId}")
    fun getCreditCardInvoice(@PathVariable hospitalId: String,
                             query: PurchaseQueryDto): ResponseEntity<PurchaseCreditCardListDto> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res =  purchaseCreditCardService.getPurchaseCreditCard(hospitalId, query)

//        val re = command.creditCard(hospitalId, query)

        return ResponseEntity.ok(res)
    }

    /** 매입 현금 영수증 항목 조회 */
    @GetMapping("/cash-receipt/{hospitalId}/list", "/cash-receipt/{hospitalId}")
    fun getCashReceiptInvoice(@PathVariable hospitalId: String,
                              purchaseQueryDto: PurchaseQueryDto): ResponseEntity<PurchaseCashReceiptListDto> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res =  purchaseCashReceiptService.getPurchaseCashReceipt(hospitalId, purchaseQueryDto)

        return ResponseEntity.ok(res)
    }

    /** 전자(세금)계산서 매입관리 항목 조회 */
    @GetMapping("/elec-invoice/{hospitalId}/list", "/e-invoice/{bookType}/{hospitalId}")
    fun getPurchaseInvoice(@PathVariable hospitalId: String,
                           @PathVariable bookType: String?,
                           purchaseQueryDto: PurchaseQueryDto): ResponseEntity<PurchaseElecInvoiceListDto> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res = purchaseElecInvoiceService.getPurchaseElecInvoice(hospitalId, purchaseQueryDto)
        return ResponseEntity.ok(res)
    }

    /** 수기 매입자료 조회 (수기 세금계산서 / 간이 영수증) */
    @GetMapping("/handwritten/{bookType}/{hospitalId}/{year}")
    fun dataList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @PathVariable bookType: String,
        @PageableDefault(size = 30) page: Pageable,
    ): List<PurchaseHandwrittenEntity> {
        return handwrittenService.dataList(hospitalId, year, page)
    }

    /** 수기 매입자료 등록 (수기 세금계산서 / 간이 영수증) */
    @PutMapping("/handwritten/{bookType}/{hospitalId}")
    fun saveList(
        @RequestBody dataList: List<PurchaseHandwrittenEntity>,
        @PathVariable hospitalId: String,
        @PathVariable bookType: String,
    ): ResponseEntity<HttpStatus> {
        return handwrittenService.dataSave(dataList)
    }
}