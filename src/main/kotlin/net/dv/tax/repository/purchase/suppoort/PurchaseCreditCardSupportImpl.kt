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
import net.dv.tax.enum.purchase.Deduction
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository


@Repository
class PurchaseCreditCardSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), PurchaseCreditCardSupport {

    override fun purchaseCreditCardList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean
    ): List<PurchaseCreditCardEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!;

        val builder = getBuilder( hospitalId, purchaseQueryDto)

        var res: List<PurchaseCreditCardEntity>

        if( isExcel ) {
           res = query
                .select(purchaseCreditCardEntity)
                .from(purchaseCreditCardEntity)
                .where(builder)
                .orderBy(purchaseCreditCardEntity.billingDate.desc())
                .fetch()
        } else {
            res = query
                .select(purchaseCreditCardEntity)
                .from(purchaseCreditCardEntity)
                .where(builder)
                .orderBy(purchaseCreditCardEntity.billingDate.desc())
                .offset(realOffset)
                .limit(purchaseQueryDto.size)
                .fetch()
        }

        return res

    }

    override fun purchaseCreditCardListCnt(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long {

        val builder = getBuilder( hospitalId, purchaseQueryDto)

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

        val builder = getBuilder( hospitalId, purchaseQueryDto)

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
            totalSupplyPrice = total.totalSupplyPrice?: 0,
            totalTaxAmount = total.totalTaxAmount?: 0,
            totalAmount = total.totalAmount?: 0,
            totalNonSupplyPrice = nonTotal.totalSupplyPrice?: 0,
            totalNonTaxAmount = nonTotal.totalTaxAmount?: 0,
            totalNonAmount = nonTotal.totalAmount?: 0
        )
    }

    fun getBuilder(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): BooleanBuilder{

        val builder = BooleanBuilder()
        builder.and(purchaseCreditCardEntity.hospitalId.eq(hospitalId))


        //공제 불공제 전체
        when(purchaseQueryDto.deduction){
            1L -> builder.and(purchaseCreditCardEntity.isDeduction.eq(Deduction.Deduction_1.isDeduction))
            2L -> builder.and(purchaseCreditCardEntity.isDeduction.eq(Deduction.Deduction_2.isDeduction))
            else -> null
        }

        return builder
    }
}