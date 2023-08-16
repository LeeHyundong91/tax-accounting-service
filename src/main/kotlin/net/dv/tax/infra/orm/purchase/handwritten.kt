package net.dv.tax.infra.orm.purchase

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.purchase.PurchaseHandwrittenQuery
import net.dv.tax.domain.purchase.PurchaseHandwrittenEntity
import net.dv.tax.domain.purchase.QPurchaseHandwrittenEntity.purchaseHandwrittenEntity
import org.springframework.stereotype.Repository

@Repository
class PurchaseHandwrittenRepositoryImpl(private val factory: JPAQueryFactory): PurchaseHandwrittenQuery {
    override fun find(query: PurchaseHandwrittenQuery.Query.() -> Unit): List<PurchaseHandwrittenEntity> {
        val q = PurchaseHandwrittenQuery.Query().apply(query)

        val predicate = BooleanBuilder().apply {
            q.hospitalId?.also { and(purchaseHandwrittenEntity.hospitalId.eq(it)) }
            q.year?.also { and(purchaseHandwrittenEntity.issueDate.startsWith(it.toString())) }
            q.type?.also { and(purchaseHandwrittenEntity.type.eq(PurchaseHandwrittenEntity.Type[it])) }
        }

        return factory
            .select(purchaseHandwrittenEntity)
            .from(purchaseHandwrittenEntity)
            .where(
                predicate
            )
            .fetch()
    }

}
