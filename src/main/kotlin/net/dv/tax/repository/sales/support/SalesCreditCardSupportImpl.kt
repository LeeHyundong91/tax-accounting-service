package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.QSalesCreditCardEntity.salesCreditCardEntity
import net.dv.tax.domain.sales.SalesCreditCardEntity
import net.dv.tax.dto.sales.SalesCreditCardListDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class SalesCreditCardSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(SalesCreditCardEntity::class.java),
    SalesCreditCardSupport {
    override fun groupingList(hospitalId: String, year: String): List<SalesCreditCardListDto> {

        return query.select(
            Projections.bean(
                SalesCreditCardListDto::class.java,
                salesCreditCardEntity.salesCount.sum().`as`("salesCount"),
                salesCreditCardEntity.totalSales.sum().`as`("totalSales"),
                salesCreditCardEntity.creditCardSalesAmount.sum().`as`("creditCardSalesAmount"),
                salesCreditCardEntity.purchaseCardSalesAmount.sum().`as`("purchaseCardSalesAmount"),
                salesCreditCardEntity.taxFreeAmount.sum().`as`("taxFreeAmount"),
                salesCreditCardEntity.dataPeriod,
                salesCreditCardEntity.cardCategory
            )
        ).from(salesCreditCardEntity)
            .where(
                salesCreditCardEntity.hospitalId.eq(hospitalId),
                salesCreditCardEntity.dataPeriod.startsWith(year)
            ).groupBy(salesCreditCardEntity.dataPeriod, salesCreditCardEntity.cardCategory)
            .fetch()

    }
}