package net.dv.tax.app.purchase

import net.dv.tax.app.enums.purchase.PurchaseType


interface PurchaseBook {
    val id: Long
    val type: PurchaseType
}

interface JournalEntry {
    var merchant: String
    var note: String
    var checkExpense: Boolean
    var accountingItem: String
    var status: String?
    var requester: String?
    var committer: String?
}