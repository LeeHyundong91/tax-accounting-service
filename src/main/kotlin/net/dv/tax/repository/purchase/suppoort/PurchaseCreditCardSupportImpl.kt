package net.dv.tax.repository.purchase.suppoort

import com.querydsl.core.BooleanBuilder

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.domain.purchase.QPurchaseCreditCardEntity.purchaseCreditCardEntity
import net.dv.tax.dto.purchase.PurchaseCreditCardTotal
import net.dv.tax.dto.purchase.PurchaseCreditCardTotalSearch
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository


@Repository
class PurchaseCreditCardSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), PurchaseCreditCardSupport {

    override fun purchaseCreditCardList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto
    ): List<PurchaseCreditCardEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!;

        val builder = BooleanBuilder()
        builder.and(purchaseCreditCardEntity.hospitalId.eq(hospitalId))

        return query
            .select(purchaseCreditCardEntity)
            .from(purchaseCreditCardEntity)
            .where(builder)
            .offset(realOffset)
            .limit(purchaseQueryDto.size)
            .fetch()

    }

    override fun purchaseCreditCardListCnt(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long {
        val builder = BooleanBuilder()
        builder.and(purchaseCreditCardEntity.hospitalId.eq(hospitalId))

        return query
            .select(purchaseCreditCardEntity.count())
            .from(purchaseCreditCardEntity)
            .where(builder)
            .fetchFirst()
    }

    override fun purchaseCreditCardTotal(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto
    ): PurchaseCreditCardTotal {

        val builder = BooleanBuilder()
        builder.and(purchaseCreditCardEntity.hospitalId.eq(hospitalId))

        var total = query
            .select(
                Projections.constructor(
                    PurchaseCreditCardTotalSearch::class.java,
                    purchaseCreditCardEntity.supplyPrice.sum().`as`("totalSupplyPrice"),
                    purchaseCreditCardEntity.taxAmount.sum().`as`("totalTaxAmount"),
                    purchaseCreditCardEntity.totalAmount.sum().`as`("totalAmount"),
                )
            )
            .from(purchaseCreditCardEntity)
            .where(builder)
            .where(purchaseCreditCardEntity.isDeduction.eq(true))
            .fetchFirst()

        var nonTotal = query
            .select(
                Projections.constructor(
                    PurchaseCreditCardTotalSearch::class.java,
                    purchaseCreditCardEntity.supplyPrice.sum().`as`("totalSupplyPrice"),
                    purchaseCreditCardEntity.taxAmount.sum().`as`("totalTaxAmount"),
                    purchaseCreditCardEntity.totalAmount.sum().`as`("totalAmount"),
                )
            )
            .from(purchaseCreditCardEntity)
            .where(builder)
            .where(purchaseCreditCardEntity.isDeduction.eq(false))
            .fetchFirst()

        return PurchaseCreditCardTotal(
            totalSupplyPrice = total.totalSupplyPrice,
            totalTaxAmount = total.totalTaxAmount,
            totalAmount = total.totalAmount,
            totalNonSupplyPrice = nonTotal.totalSupplyPrice,
            totalNonTaxAmount = nonTotal.totalTaxAmount,
            totalNonAmount = nonTotal.totalAmount
        )
    }
}