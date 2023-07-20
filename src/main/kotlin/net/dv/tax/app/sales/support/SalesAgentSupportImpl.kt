package net.dv.tax.app.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.QSalesAgentEntity.salesAgentEntity
import net.dv.tax.domain.sales.SalesAgentEntity
import net.dv.tax.app.dto.sales.SalesAgentDto
import net.dv.tax.app.common.CustomQuerydslRepositorySupport

class SalesAgentSupportImpl(private val query: JPAQueryFactory) : CustomQuerydslRepositorySupport(
    SalesAgentEntity::class.java
), SalesAgentSupport {
    override fun dataList(hospitalId: String, yearMonth: String): List<SalesAgentDto> {

        return query
            .select(
                Projections.bean(
                    SalesAgentDto::class.java,
                    salesAgentEntity.approvalYearMonth.`as`("approvalYearMonth"),
                    salesAgentEntity.salesCount.sum().`as`("salesCount"),
                    salesAgentEntity.totalSales.sum().`as`("totalSales"),
                )
            ).from(salesAgentEntity)
            .where(
                salesAgentEntity.hospitalId.eq(hospitalId),
                salesAgentEntity.approvalYearMonth.startsWith(yearMonth)
            )
            .groupBy(salesAgentEntity.approvalYearMonth)
            .fetch()
    }

    override fun monthlySumAmount(hospitalId: String, yearMonth: String): Long? {
        return query
            .select(
                salesAgentEntity.totalSales.sum()
            ).from(salesAgentEntity)
            .where(
                salesAgentEntity.hospitalId.eq(hospitalId),
                salesAgentEntity.approvalYearMonth.startsWith(yearMonth)
            )
            .fetchOne()
    }

}