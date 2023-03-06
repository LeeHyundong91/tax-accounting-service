package net.dv.tax.repository.purchase.suppoort

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity
import net.dv.tax.domain.purchase.QPurchaseElecInvoiceEntity.purchaseElecInvoiceEntity
import net.dv.tax.dto.purchase.PurchaseElecInvoiceTotal
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository


@Repository
class PurchaseElecInvoiceSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), PurchaseElecInvoiceSupport {

    override fun purchaseElecInvoiceList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto
    ): List<PurchaseElecInvoiceEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!;

        val builder = BooleanBuilder()
        builder.and(purchaseElecInvoiceEntity.hospitalId.eq(hospitalId))

        return query
            .select(purchaseElecInvoiceEntity)
            .from(purchaseElecInvoiceEntity)
            .where(builder)
            .offset(realOffset)
            .limit(purchaseQueryDto.size)
            .fetch()

    }

    override fun purchaseElecInvoiceListCnt(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long {
        val builder = BooleanBuilder()
        builder.and(purchaseElecInvoiceEntity.hospitalId.eq(hospitalId))

        return query
            .select(purchaseElecInvoiceEntity.count())
            .from(purchaseElecInvoiceEntity)
            .where(builder)
            .fetchFirst()
    }

    override fun purchaseElecInvoiceTotal(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto
    ): PurchaseElecInvoiceTotal {

        val builder = BooleanBuilder()
        builder.and(purchaseElecInvoiceEntity.hospitalId.eq(hospitalId))

        return query
            .select(
                Projections.constructor(
                    PurchaseElecInvoiceTotal::class.java,
                    purchaseElecInvoiceEntity.totalAmount.sum().`as`("totalAmount"),
                    purchaseElecInvoiceEntity.supplyPrice.sum().`as`("totalSupplyPrice"),
                    purchaseElecInvoiceEntity.taxAmount.sum().`as`("totalTaxAmount"),
                )
            )
            .from(purchaseElecInvoiceEntity)
            .where(builder)
            .fetchFirst()
    }
}