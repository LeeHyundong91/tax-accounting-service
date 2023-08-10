package net.dv.tax.app.purchase

import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface PurchaseJournalEntryRepository:
    JpaRepository<JournalEntryEntity, Long>,
    PurchaseJournalEntryQuery

@Repository
interface PurchaseJournalEntryHistoryRepository:
    JpaRepository<JournalEntryHistoryEntity, Long>,
    PurchaseJournalEntryHistoryQuery

interface PurchaseJournalEntryQuery {
    fun search(): List<PurchaseData>

    fun find(purchase: PurchaseBook): JournalEntryEntity?
}

interface PurchaseJournalEntryHistoryQuery {
    fun find(purchase: PurchaseBook): List<JournalEntryHistoryEntity>
}

class PurchaseData