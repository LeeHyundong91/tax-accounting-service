package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.QSalesCreditCardEntity.salesCreditCardEntity
import net.dv.tax.domain.sales.SalesCreditCardEntity
import net.dv.tax.dto.sales.SalesCreditCardDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class SalesCreditCardSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(SalesCreditCardEntity::class.java),
    SalesCreditCardSupport {
    private fun baseQuery(hospitalId: String, year: String): JPAQuery<SalesCreditCardDto> {
        return query.select(
            Projections.bean(
                SalesCreditCardDto::class.java,
                salesCreditCardEntity.salesCount.sum().`as`("salesCount"),
                salesCreditCardEntity.totalSales.sum().`as`("totalSales"),
                salesCreditCardEntity.creditCardSalesAmount.sum().`as`("creditCardSalesAmount"),
                salesCreditCardEntity.zeroPaySalesAmount.sum().`as`("zeroPaySalesAmount"),
                salesCreditCardEntity.taxFreeAmount.sum().`as`("taxFreeAmount"),
                salesCreditCardEntity.approvalYearMonth,
            )
        ).from(salesCreditCardEntity)
            .where(
                salesCreditCardEntity.hospitalId.eq(hospitalId),
                salesCreditCardEntity.approvalYearMonth.startsWith(year)
            )
    }

    override fun dataList(hospitalId: String, yearMonth: String): List<SalesCreditCardDto> {
        return baseQuery(hospitalId, yearMonth)
            .groupBy(salesCreditCardEntity.approvalYearMonth)
            .fetch()

    }

    override fun dataListTotal(hospitalId: String, yearMonth: String): SalesCreditCardDto? {
        return baseQuery(hospitalId, yearMonth)
            .fetchOne()
    }

    override fun monthlySumAmount(hospitalId: String, yearMonth: String): Long? {
        return query.select(
            salesCreditCardEntity.totalSales.sum()
        )
            .from(salesCreditCardEntity)
            .where(
                salesCreditCardEntity.hospitalId.eq(hospitalId),
                salesCreditCardEntity.approvalYearMonth.startsWith(yearMonth)
            )
            .fetchOne()

    }
}