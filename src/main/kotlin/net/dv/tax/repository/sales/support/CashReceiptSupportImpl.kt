package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.QSalesCashReceiptEntity.salesCashReceiptEntity
import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.dto.sales.SalesCashReceiptListDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class CashReceiptSupportImpl(private val query: JPAQueryFactory) :
    CustomQuerydslRepositorySupport(SalesCashReceiptEntity::class.java), CashReceiptSupport {
    override fun groupingList(hospitalId: String, year: String): List<SalesCashReceiptListDto> {
        return query.select(
            Projections.bean(
                SalesCashReceiptListDto::class.java,
                salesCashReceiptEntity.totalAmount.sum().`as`("totalAmount"),
                salesCashReceiptEntity.supplyPrice.sum().`as`("supplyPrice"),
                salesCashReceiptEntity.vat.sum().`as`("vat"),
                salesCashReceiptEntity.serviceCharge.sum().`as`("serviceCharge"),
                salesCashReceiptEntity.dataPeriod.count().`as`("count"),
                salesCashReceiptEntity.dataPeriod.substring(0, 7).`as`("dataPeriod"),
            )
        ).from(salesCashReceiptEntity)
            .where(
                salesCashReceiptEntity.hospitalId.eq(hospitalId),
                salesCashReceiptEntity.dataPeriod.startsWith(year),
                salesCashReceiptEntity.dealType.eq("승인거래")
            ).groupBy(salesCashReceiptEntity.dataPeriod.substring(0, 7))
            .fetch()
    }
}