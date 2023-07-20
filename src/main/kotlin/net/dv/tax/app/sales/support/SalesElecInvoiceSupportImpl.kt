package net.dv.tax.app.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.QSalesElecInvoiceEntity.salesElecInvoiceEntity
import net.dv.tax.domain.sales.SalesElecInvoiceEntity
import net.dv.tax.app.dto.sales.SalesElecInvoiceDto
import net.dv.tax.app.dto.sales.SalesElecTaxInvoiceDto
import net.dv.tax.app.common.CustomQuerydslRepositorySupport

class SalesElecInvoiceSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(SalesElecInvoiceEntity::class.java), SalesElecInvoiceSupport {
    private fun baseQuery(hospitalId: String, writingDate: String, tax: Boolean): JPAQuery<*> {
        val selectClause = when (tax) {
            true -> Projections.bean(
                SalesElecTaxInvoiceDto::class.java,
                salesElecInvoiceEntity.writingDate.substring(0, 7).`as`("writingDate"),
                salesElecInvoiceEntity.writingDate.count().`as`("totalCount"),
                salesElecInvoiceEntity.totalAmount.sum().`as`("totalAmount"),
                salesElecInvoiceEntity.supplyAmount.sum().`as`("supplyAmount"),
                salesElecInvoiceEntity.taxAmount.sum().`as`("taxAmount"),
            )

            false -> Projections.bean(
                SalesElecInvoiceDto::class.java,
                salesElecInvoiceEntity.writingDate.substring(0, 7).`as`("writingDate"),
                salesElecInvoiceEntity.writingDate.count().`as`("totalCount"),
                salesElecInvoiceEntity.supplyAmount.sum().`as`("supplyAmount"),
            )
        }

        return query.select(selectClause)
            .from(salesElecInvoiceEntity)
            .where(
                salesElecInvoiceEntity.hospitalId.eq(hospitalId),
                salesElecInvoiceEntity.writingDate.startsWith(writingDate),
                salesElecInvoiceEntity.tax.eq(tax)
            )
    }

    override fun dataList(hospitalId: String, writingDate: String): List<SalesElecInvoiceDto> {
        return baseQuery(hospitalId, writingDate, false)
            .groupBy(salesElecInvoiceEntity.writingDate.substring(0, 7))
            .fetch() as List<SalesElecInvoiceDto>
    }

    override fun dataListTotal(hospitalId: String, writingDate: String): SalesElecInvoiceDto? {
        return baseQuery(hospitalId, writingDate, false)
            .fetchOne() as SalesElecInvoiceDto
    }

    override fun taxDataList(hospitalId: String, writingDate: String): List<SalesElecTaxInvoiceDto> {
        return baseQuery(hospitalId, writingDate, true)
            .groupBy(salesElecInvoiceEntity.writingDate.substring(0, 7))
            .fetch() as List<SalesElecTaxInvoiceDto>
    }

    override fun taxDataListTotal(hospitalId: String, writingDate: String): SalesElecTaxInvoiceDto? {
        return baseQuery(hospitalId, writingDate, true)
            .fetchOne() as SalesElecTaxInvoiceDto
    }


}