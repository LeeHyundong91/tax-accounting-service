package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.QSalesCashReceiptEntity.salesCashReceiptEntity
import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.dto.sales.SalesCashReceiptDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class CashReceiptSupportImpl(private val query: JPAQueryFactory) :
    CustomQuerydslRepositorySupport(SalesCashReceiptEntity::class.java), CashReceiptSupport {
    private fun baseQuery(hospitalId: String, yearMonth: String): JPAQuery<SalesCashReceiptDto> {
        return query.select(
            Projections.bean(
                SalesCashReceiptDto::class.java,
                salesCashReceiptEntity.salesDate.substring(0, 7).`as`("salesDate"),
                salesCashReceiptEntity.salesDate.count().`as`("totalCount"),
                salesCashReceiptEntity.supplyAmount.sum().`as`("supplyAmount"),
                salesCashReceiptEntity.taxAmount.sum().`as`("taxAmount"),
                salesCashReceiptEntity.serviceFee.sum().`as`("serviceFee"),
                salesCashReceiptEntity.totalAmount.sum().`as`("totalAmount"),
            )
        ).from(salesCashReceiptEntity)
            .where(
                salesCashReceiptEntity.hospitalId.eq(hospitalId),
                salesCashReceiptEntity.transactionType.eq("승인거래"),
                salesCashReceiptEntity.salesDate.startsWith(yearMonth)
            )
    }


    override fun dataList(hospitalId: String, yearMonth: String): List<SalesCashReceiptDto> {
        return baseQuery(hospitalId, yearMonth)
            .groupBy(salesCashReceiptEntity.salesDate.substring(0, 7))
            .fetch()
    }

    override fun dataListTotal(hospitalId: String, yearMonth: String): SalesCashReceiptDto? {
        return baseQuery(hospitalId, yearMonth)
            .fetchOne()
    }

    override fun monthlySumAmount(hospitalId: String, yearMonth: String): Long? {
        return query.select(
            salesCashReceiptEntity.totalAmount.sum()
        ).from(salesCashReceiptEntity)
            .where(
                salesCashReceiptEntity.hospitalId.eq(hospitalId),
                salesCashReceiptEntity.transactionType.eq("승인거래"),
                salesCashReceiptEntity.salesDate.startsWith(yearMonth)
            ).fetchOne()
    }
}