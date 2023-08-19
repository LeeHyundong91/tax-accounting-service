package net.dv.tax.app.purchase

import net.dv.tax.app.Period
import net.dv.tax.app.enums.purchase.PurchaseType
import java.time.LocalDateTime


interface BookSummary

interface PurchaseBookIdentity {
    val id: Long
    val type: PurchaseType
}

interface PurchaseBookOverview: PurchaseBookIdentity {
    val merchant: String?
    val item: String?
    val transactionDate: String
    val amount: Long
}

interface JournalEntryStatus {
    val status: String?
    val requestedAt: LocalDateTime?
    val committedAt: LocalDateTime?
}

interface JournalEntry: JournalEntryStatus {
    val note: String
    val checkExpense: Boolean
    val accountingItem: String?
    val requester: String?
    val committer: String?
}

interface JournalEntryOverview: JournalEntry, PurchaseBookOverview

interface CreditCardBook: JournalEntryStatus {
    val id: Long
    val hospitalId: String
    val dataFileId: Long?
    val billingDate: String?
    val accountCode: String?
    val franchiseeName: String?
    val corporationType: String?
    val itemName: String?
    val supplyPrice: Long?
    val taxAmount: Long?
    val nonTaxAmount: Long?
    val totalAmount: Long?
    val deductionName: String?
    val recommendDeductionName: String?
    val statementType1: String?
    val statementType2: String?
    val debtorAccount: String?
    val creditAccount: String?
    val separateSend: String?
    val statementStatus: String?
    val writer: String?
    val createdAt: LocalDateTime?
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

interface ETaxInvoiceBook: JournalEntryStatus {
    val id: Long?
    val hospitalId: String?
    val issueDate: String?
    val sendDate: String?
    val accountCode: String?
    val franchiseeName: String?
    val itemName: String?
    val supplyPrice: Long?
    val taxAmount: Long?
    val totalAmount: Long?
    val isDeduction: String?
    val debtorAccount: String?
    val creditAccount: String?
    val separateSend: String?
    val statementStatus: String?
    val taskType: String?
    val approvalNo: String?
    val invoiceType: String?
    val billingType: String?
    val issueType: String?
    val writer: String?
}

interface HandwrittenBook {
    val issueDate: String?
    val supplier: String?
    val itemName: String?
    val supplyPrice: Long?
    val debitAccount: String?
    val taxAmount: Long?
    val writer: String?
}

interface ExpenseFilter {
    val hospitalId: String
    val category: JournalEntryCommand.Category
    val period: Period
    val types: List<PurchaseType>?
}
