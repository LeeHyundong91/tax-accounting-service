package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.CarInsuranceEntity
import net.dv.tax.domain.sales.QSalesElecInvoiceEntity.salesElecInvoiceEntity
import net.dv.tax.dto.sales.SalesElecInvoiceListDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class SalesElecInvoiceSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(CarInsuranceEntity::class.java), SalesElecInvoiceSupport {
    override fun groupingList(hospitalId: String, year: String): List<SalesElecInvoiceListDto> {

        return query.select(
            Projections.bean(
                SalesElecInvoiceListDto::class.java,
                salesElecInvoiceEntity.supplyPrice.sum().`as`("supplyPrice"),
                salesElecInvoiceEntity.taxAmount.sum().`as`("taxAmount"),
                salesElecInvoiceEntity.totalAmount.sum().`as`("totalAmount"),
                salesElecInvoiceEntity.dataPeriod.count().`as`("count"),
                salesElecInvoiceEntity.dataPeriod.substring(0, 7).`as`("dataPeriod"),
            )
        ).from(salesElecInvoiceEntity)
            .where(
                salesElecInvoiceEntity.hospitalId.eq(hospitalId),
                salesElecInvoiceEntity.dataPeriod.startsWith(year)
            ).groupBy(salesElecInvoiceEntity.dataPeriod.substring(0, 7))
            .fetch()


    }
}