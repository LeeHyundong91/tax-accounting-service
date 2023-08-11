package net.dv.tax.app.purchase

import net.dv.tax.app.enums.purchase.PurchaseType
import java.time.LocalDateTime


interface BookSummary

interface PurchaseBook {
    val id: Long
    val type: PurchaseType
}

interface JournalEntryStatus {
    var status: String?
    var requestedAt: LocalDateTime?
    var committedAt: LocalDateTime?
}

interface JournalEntry: JournalEntryStatus {
    var merchant: String
    var note: String
    var checkExpense: Boolean
    var accountingItem: String?
    var requester: String?
    var committer: String?
}

interface CashReceiptBook: JournalEntryStatus {
    val id: Long
    val hospitalId: String
    val dataFileId: Long?
    val billingDate: String?
    val accountCode: String?
    val franchiseeName: String?
    val corporationType: String?
    val itemName: String?
    val supplyPrice: Long
    val taxAmount: Long
    val serviceCharge: Long
    val totalAmount: Long
    val deductionName: String?
    val recommendDeductionName: String?
    val statementType1: String?
    val statementType2: String?
    val debtorAccount: String?
    val creditAccount: String?
    val separateSend: String?
    val department: String?
    val statementStatus: String?
    val writer: String?
}
