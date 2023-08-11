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
    var hospitalId: String
    var dataFileId: Long?
    var billingDate: String?
    var accountCode: String?
    var franchiseeName: String?
    var corporationType: String?
    var itemName: String?
    var supplyPrice: Long
    var taxAmount: Long
    var serviceCharge: Long
    var totalAmount: Long
    var isDeduction: Boolean?
    var deductionName: String?
    var isRecommendDeduction: Boolean?
    var recommendDeductionName: String?
    var statementType1: String?
    var statementType2: String?
    var debtorAccount: String?
    var creditAccount: String?
    var separateSend: String?
    var department: String?
    var statementStatus: String?
    var writer: String?
}
