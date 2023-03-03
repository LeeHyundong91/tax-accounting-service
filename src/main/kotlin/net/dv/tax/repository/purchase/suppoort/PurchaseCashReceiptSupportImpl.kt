package net.dv.tax.repository.purchase.suppoort

import com.querydsl.core.BooleanBuilder

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.domain.purchase.PurchaseCashReceiptEntity
import net.dv.tax.domain.purchase.QPurchaseCashReceiptEntity.purchaseCashReceiptEntity
import net.dv.tax.dto.purchase.PurchaseCashReceiptTotal
import net.dv.tax.dto.purchase.PurchaseCashReceiptTotalSearch
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository


@Repository
class PurchaseCashReceiptSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), PurchaseCashReceiptSupport {

    override fun purchaseCashReceiptList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto
    ): List<PurchaseCashReceiptEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!;

        val builder = BooleanBuilder()
        builder.and(purchaseCashReceiptEntity.hospitalId.eq(hospitalId))

        return query
            .select(purchaseCashReceiptEntity)
            .from(purchaseCashReceiptEntity)
            .where(builder)
            .offset(realOffset)
            .limit(purchaseQueryDto.size)
            .fetch()

    }

    override fun purchaseCashReceiptListCnt(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long {
        val builder = BooleanBuilder()
        builder.and(purchaseCashReceiptEntity.hospitalId.eq(hospitalId))

        return query
            .select(purchaseCashReceiptEntity.count())
            .from(purchaseCashReceiptEntity)
            .where(builder)
            .fetchFirst()
    }

    override fun purchaseCashReceiptTotal(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto
    ): PurchaseCashReceiptTotal {

        val builder = BooleanBuilder()
        builder.and(purchaseCashReceiptEntity.hospitalId.eq(hospitalId))

        var total = query
            .select(
                Projections.constructor(
                    PurchaseCashReceiptTotalSearch::class.java,
                    purchaseCashReceiptEntity.supplyPrice.sum().`as`("totalSupplyPrice"),
                    purchaseCashReceiptEntity.taxAmount.sum().`as`("totalTaxAmount"),
                    purchaseCashReceiptEntity.totalAmount.sum().`as`("totalAmount"),
                )
            )
            .from(purchaseCashReceiptEntity)
            .where(builder)
            .where(purchaseCashReceiptEntity.isDeduction.eq(true))
            .fetchFirst()

        var nonTotal = query
            .select(
                Projections.constructor(
                    PurchaseCashReceiptTotalSearch::class.java,
                    purchaseCashReceiptEntity.supplyPrice.sum().`as`("totalSupplyPrice"),
                    purchaseCashReceiptEntity.taxAmount.sum().`as`("totalTaxAmount"),
                    purchaseCashReceiptEntity.totalAmount.sum().`as`("totalAmount"),
                )
            )
            .from(purchaseCashReceiptEntity)
            .where(builder)
            .where(purchaseCashReceiptEntity.isDeduction.eq(false))
            .fetchFirst()

        return PurchaseCashReceiptTotal(
            totalSupplyPrice = total.totalSupplyPrice,
            totalTaxAmount = total.totalTaxAmount,
            totalAmount = total.totalAmount,
            totalNonSupplyPrice = nonTotal.totalSupplyPrice,
            totalNonTaxAmount = nonTotal.totalTaxAmount,
            totalNonAmount = nonTotal.totalAmount
        )
    }
}