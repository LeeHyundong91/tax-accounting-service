package net.dv.tax.domain.purchase

class JournalEntryEntity (
    val id: Long = 0,
    var note: String? = null,
    var checkExpense: Boolean? = null,
    var accountItem: String? = null,
    var status: String? = null,
    var requester: String? = null,
    var committer: String? = null,
)