package net.dv.tax.app.purchase

import net.dv.tax.app.enums.purchase.PurchaseType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PurchaseQueryCommand {
    fun <T> purchaseBooks(type: PurchaseType, hospitalId: String, query: PurchaseQueryDto): PurchaseBooks<T>
    fun handwrittenBooks(type: PurchaseType, hospitalId: String, year: Int): List<HandwrittenBook>
}

interface PurchaseOperationCommand {
    fun writeBooks(hospitalId: String, type: PurchaseType, books: List<HandwrittenBook>): BookSummary
}

interface JournalEntryCommand {
    fun get(purchase: PurchaseBook): JournalEntry
    fun request(purchase: PurchaseBook, je: JournalEntry): JournalEntry
    fun confirm(purchase: PurchaseBook, je: JournalEntry): JournalEntry
    fun history(purchase: PurchaseBookDto): PurchaseBookSummary
    fun processingState(type: PurchaseType, hospitalId: String, pageable: Pageable): Page<out JournalEntryStatus>
}
