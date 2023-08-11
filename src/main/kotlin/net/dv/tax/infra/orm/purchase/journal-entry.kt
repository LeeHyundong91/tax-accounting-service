package net.dv.tax.infra.orm.purchase

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.purchase.PurchaseBook
import net.dv.tax.app.purchase.PurchaseData
import net.dv.tax.app.purchase.PurchaseJournalEntryHistoryQuery
import net.dv.tax.app.purchase.PurchaseJournalEntryQuery
import net.dv.tax.domain.purchase.*
import org.springframework.stereotype.Repository


@Repository
class PurchaseJournalEntryRepositoryImpl(
    private val factory: JPAQueryFactory
): PurchaseJournalEntryQuery {
    override fun search(): List<PurchaseData> {
        val cc = factory
            .from(QPurchaseCreditCardEntity.purchaseCreditCardEntity)
            .where(QPurchaseCreditCardEntity.purchaseCreditCardEntity.hospitalId.eq("test-id"))

        val cr = factory
            .from(QPurchaseCreditCardEntity.purchaseCreditCardEntity)

        return listOf()
    }

    override fun find(purchase: PurchaseBook): JournalEntryEntity? {
        return factory
            .select(QJournalEntryEntity.journalEntryEntity)
            .from(QJournalEntryEntity.journalEntryEntity)
            .where(
                QJournalEntryEntity.journalEntryEntity.purchaseId.eq(purchase.id),
                QJournalEntryEntity.journalEntryEntity.purchaseType.eq(purchase.type.code)
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
            .select(QJournalEntryHistoryEntity.journalEntryHistoryEntity)
            .from(QJournalEntryHistoryEntity.journalEntryHistoryEntity)
            .where(
                QJournalEntryHistoryEntity.journalEntryHistoryEntity.purchaseId.eq(purchase.id),
                QJournalEntryHistoryEntity.journalEntryHistoryEntity.purchaseType.eq(purchase.type.code)
            )
            .fetch()
    }
}