package net.dv.tax.app.purchase

import net.dv.tax.app.Period
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
    fun expenseByHospital(hospitalId: String, query: Query, pageable: Pageable): Page<JournalEntry>
    fun expenseByHospital(hospitalId: String, option:Query.() -> Unit): Page<JournalEntry>
    fun get(purchase: PurchaseBookIdentity): JournalEntry
    fun request(purchase: PurchaseBookIdentity, je: JournalEntry): JournalEntry
    fun confirm(purchase: PurchaseBookIdentity, je: JournalEntry): JournalEntry
    fun history(purchase: PurchaseBookDto): PurchaseBookOverview
    fun processingState(type: PurchaseType, hospitalId: String, pageable: Pageable): Page<out JournalEntryStatus>

    interface Query {
        val category: String
        val period: Period
        val type: PurchaseType
    }
}
