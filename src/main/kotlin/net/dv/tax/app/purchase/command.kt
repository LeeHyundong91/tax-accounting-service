package net.dv.tax.app.purchase

import net.dv.tax.app.AbstractSearchQueryDto
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
    fun expenseByHospital(hospitalId: String, filter: ExpenseFilter, pageable: Pageable): Page<PurchaseBookOverview>
    fun expenseByHospital(hospitalId: String, option:FilterOption.() -> Unit): Page<PurchaseBookOverview> {
        return FilterOption(hospitalId).apply(option).let {
            expenseByHospital(hospitalId, it, it.pageable)
        }
    }
    fun get(purchase: PurchaseBookIdentity): JournalEntryOverview
    fun request(purchase: PurchaseBookIdentity, je: JournalEntry): JournalEntry
    fun confirm(purchase: PurchaseBookIdentity, je: JournalEntry): JournalEntry
    fun history(purchase: PurchaseBookDto): PurchaseBookOverview
    fun processingState(type: PurchaseType, hospitalId: String, pageable: Pageable): Page<out JournalEntryStatus>

    enum class Category(val code: String) {
        ALL("ALL"),
        REQUIRE_CHECK("RC"),
        CATEGORIZED("CG"),
        NOT_CATEGORIZED("NC")
    }

    data class FilterOption(
        override val hospitalId: String,
        override var category: Category = Category.NOT_CATEGORIZED,
        override var period: Period = Period(null, null),
        override var types: List<PurchaseType>? = null,
    ): ExpenseFilter, AbstractSearchQueryDto()
}
