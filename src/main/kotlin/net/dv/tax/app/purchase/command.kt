package net.dv.tax.app.purchase

import net.dv.tax.app.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.app.enums.purchase.PurchaseType


interface PurchaseQueryCommand {
    fun creditCard(hospitalId: String, query: PurchaseQueryDto): PurchaseCreditCardListDto
}

interface JournalEntryCommand {
    fun get(book: PurchaseBook): JournalEntry
    fun request(purchase: PurchaseBook, data: JournalEntry): JournalEntry
    fun confirm(purchase: PurchaseBook, data: JournalEntry): JournalEntry
    fun history(purchaseBookDto: PurchaseBookDto): List<JournalEntryHistoryDto>
}
