package net.dv.tax.infra.orm

import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.purchase.PurchaseData
import net.dv.tax.app.purchase.PurchaseJournalEntryQueryRepository
import net.dv.tax.domain.purchase.QPurchaseCreditCardEntity.purchaseCreditCardEntity


class PurchaseJournalEntryRepositoryImpl(
    private val factory: JPAQueryFactory
): PurchaseJournalEntryQueryRepository {
    override fun search(): List<PurchaseData> {
        val cc = factory
            .from(purchaseCreditCardEntity)
            .where(purchaseCreditCardEntity.hospitalId.eq("test-id"))

        val cr = factory
            .from(purchaseCreditCardEntity)

        return listOf()
    }
}