package net.dv.tax.app.purchase

import net.dv.tax.app.enums.purchase.PurchaseType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PurchaseQueryCommand {
    fun <T> purchaseBooks(type: PurchaseType, hospitalId: String, query: PurchaseQueryDto): PurchaseBooks<T>
}

interface JournalEntryCommand {
    fun get(purchase: PurchaseBook): JournalEntry
    fun request(purchase: PurchaseBook, je: JournalEntry): JournalEntry
    fun confirm(purchase: PurchaseBook, je: JournalEntry): JournalEntry
    fun history(purchase: PurchaseBookDto): List<JournalEntryHistoryDto>
    fun processingState(type: PurchaseType, hospitalId: String, pageable: Pageable): Page<out JournalEntryStatus>
}
