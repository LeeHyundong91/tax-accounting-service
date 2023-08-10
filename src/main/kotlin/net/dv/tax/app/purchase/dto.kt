package net.dv.tax.app.purchase

import net.dv.tax.app.enums.purchase.PurchaseType
import java.time.LocalDateTime

interface JournalEntryDto {
    var status: String?
    var requestedAt: LocalDateTime?
    var committedAt: LocalDateTime?
}

data class PurchaseBookDto(
    override val id: Long,
    override val type: PurchaseType
): PurchaseBook

data class JournalEntryReqDto(
    override var merchant: String = "",
    override var note: String,
    override var checkExpense: Boolean,
    override var accountingItem: String? = null,
    override var status: String? = null,
    override var requester: String? = null,
    override var committer: String? = null,
) : JournalEntry

data class JournalEntryHistoryDto(
    val writer: String,
    val accountingItem: String?,
    val expense: Boolean?,
    val note: String,
    val writtenAt: LocalDateTime,
)