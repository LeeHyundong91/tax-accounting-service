package net.dv.tax.infra.orm

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.purchase.PurchaseBook
import net.dv.tax.app.purchase.PurchaseData
import net.dv.tax.app.purchase.PurchaseJournalEntryHistoryQuery
import net.dv.tax.app.purchase.PurchaseJournalEntryQuery
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import net.dv.tax.domain.purchase.QJournalEntryEntity.journalEntryEntity
import net.dv.tax.domain.purchase.QJournalEntryHistoryEntity.journalEntryHistoryEntity
import net.dv.tax.domain.purchase.QPurchaseCreditCardEntity.purchaseCreditCardEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository


@Repository
class PurchaseJournalEntryRepositoryImpl(
    private val factory: JPAQueryFactory
): PurchaseJournalEntryQuery {
    override fun search(): List<PurchaseData> {
        val cc = factory
            .from(purchaseCreditCardEntity)
            .where(purchaseCreditCardEntity.hospitalId.eq("test-id"))

        val cr = factory
            .from(purchaseCreditCardEntity)

        return listOf()
    }

    override fun find(purchase: PurchaseBook): JournalEntryEntity? {
        return factory
            .select(journalEntryEntity)
            .from(journalEntryEntity)
            .where(
                journalEntryEntity.purchaseId.eq(purchase.id),
                journalEntryEntity.purchaseType.eq(purchase.type.code)
            )
            .fetchOne()
    }
}

@Repository
class PurchaseJournalEntryHistoryRepositoryImpl(
    private val factory: JPAQueryFactory
): PurchaseJournalEntryHistoryQuery {
    override fun find(purchase: PurchaseBook): List<JournalEntryHistoryEntity> {
        return factory
            .select(journalEntryHistoryEntity)
            .from(journalEntryHistoryEntity)
            .where(
                journalEntryHistoryEntity.purchaseId.eq(purchase.id),
                journalEntryHistoryEntity.purchaseType.eq(purchase.type.code)
            )
            .fetch()
    }
}