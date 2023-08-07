package net.dv.tax.app.purchase


interface PurchaseJournalEntryEntityRepository

interface PurchaseJournalEntryQueryRepository {
    fun search(): List<PurchaseData>
}

class PurchaseData