package net.dv.tax.app.purchase

import net.dv.tax.app.AbstractSearchQueryDto
import net.dv.tax.app.enums.purchase.PurchaseType
import java.time.LocalDateTime


data class PurchaseBooks<T>(
    val list: List<T>,
    val summary: BookSummary,
    val total: Long,
)

data class PurchaseBookDto(
    override val id: Long,
    override val type: PurchaseType
): PurchaseBook

data class PurchaseQueryDto (
    val name: String? = null,
    val from: String? = null,
    val to: String? = null,
    val offset: Long? = 0,
    val size: Long? = 30,
    var deduction: Long? = null,
    var isTax: Boolean = false
)

class JournalEntryQueryDto: AbstractSearchQueryDto()

data class JournalEntryReqDto(
    override var merchant: String = "",
    override var note: String,
    override var checkExpense: Boolean,
    override var accountingItem: String? = null,
    override var status: String? = null,
    override var requester: String? = null,
    override var committer: String? = null,
    override var requestedAt: LocalDateTime? = null,
    override var committedAt: LocalDateTime? = null
) : JournalEntry

data class JournalEntryHistoryDto(
    val writer: String,
    val accountingItem: String?,
    val expense: Boolean?,
    val note: String,
    val writtenAt: LocalDateTime,
)