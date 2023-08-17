package net.dv.tax.infra.endpoint.purchase

import mu.KotlinLogging
import net.dv.tax.Application
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/${Application.VERSION}/purchase")
class PurchaseBooksEndpoints(
    private val operationCommand: PurchaseOperationCommand,
    private val queryCommand: PurchaseQueryCommand,
) {
    private val log = KotlinLogging.logger {}

    /** 신용카드매입관리 항목 조회 */
    @GetMapping("/credit-card/{hospitalId}")
    fun getCreditCardInvoice(@PathVariable hospitalId: String,
                             query: PurchaseQueryDto): ResponseEntity<PurchaseBooks<CreditCardBook>> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res = queryCommand.purchaseBooks<CreditCardBook>(PurchaseType.CREDIT_CARD, hospitalId, query)

        return ResponseEntity.ok(res)
    }

    /** 매입 현금 영수증 항목 조회 */
    @GetMapping("/cash-receipt/{hospitalId}")
    fun getCashReceiptInvoice(@PathVariable hospitalId: String,
                              query: PurchaseQueryDto): ResponseEntity<PurchaseBooks<CashReceiptBook>> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val res = queryCommand.purchaseBooks<CashReceiptBook>(PurchaseType.CASH_RECEIPT, hospitalId, query)

        return ResponseEntity.ok(res)
    }

    /** 전자(세금)계산서 매입관리 항목 조회 */
    @GetMapping("/e-{bookType}/{hospitalId}")
    fun getPurchaseInvoice(@PathVariable hospitalId: String,
                           @PathVariable bookType: String,
                           query: PurchaseQueryDto ): ResponseEntity<PurchaseBooks<ETaxInvoiceBook>> {

        if( hospitalId.isEmpty() ) throw IllegalArgumentException("hospitalId is empty.")
        val type = bookType.replace("-", "_")
        val res = queryCommand.purchaseBooks<ETaxInvoiceBook>(PurchaseType[type], hospitalId, query)
        return ResponseEntity.ok(res)
    }

    /** 수기 매입자료 조회 (수기 세금계산서 / 간이 영수증) */
    @GetMapping("/{bookType}/{hospitalId}/{year}")
    fun dataList(@PathVariable hospitalId: String,
                 @PathVariable bookType: String,
                 @PathVariable year: Int,
    ): ResponseEntity<List<HandwrittenBook>> {
        val purchaseType = PurchaseType[bookType.replace("-", "_")]
        val res = queryCommand.handwrittenBooks(purchaseType, hospitalId, year)
        return ResponseEntity.ok(res)
    }

    /** 수기 매입자료 등록 (수기 세금계산서 / 간이 영수증) */
    @PatchMapping("/{bookType}/{hospitalId}")
    fun saveList(
        @RequestBody books: List<HandwrittenBookDto>,
        @PathVariable hospitalId: String,
        @PathVariable bookType: String,
    ): ResponseEntity<BookSummary> {
        val purchaseType = PurchaseType[bookType.replace("-", "_")]
        val res = operationCommand.writeBooks(hospitalId, purchaseType, books)
        return ResponseEntity.ok(res)
    }
}

data class HandwrittenBookDto(
    override var issueDate: String?,
    override var supplier: String?,
    override var itemName: String?,
    override var supplyPrice: Long?,
    override var debitAccount: String?,
    override val taxAmount: Long?,
    override val writer: String?
): HandwrittenBook