package net.dv.tax.app.purchase

import net.dv.tax.app.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.app.enums.purchase.PurchaseType


interface PurchaseQueryCommand {
    fun creditCard(hospitalId: String, query: PurchaseQueryDto): PurchaseCreditCardListDto
    fun purchaseBooks(type: PurchaseType, hospitalId: String, query: PurchaseQueryDto): PurchaseBooks<*>
}

interface JournalEntryCommand {
    fun get(purchase: PurchaseBook): JournalEntry
    fun request(purchase: PurchaseBook, data: JournalEntry): JournalEntry
    fun confirm(purchase: PurchaseBook, data: JournalEntry): JournalEntry
    fun history(purchase: PurchaseBookDto): List<JournalEntryHistoryDto>
}
