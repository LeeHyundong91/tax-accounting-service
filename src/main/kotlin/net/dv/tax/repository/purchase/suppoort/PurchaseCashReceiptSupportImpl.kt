package net.dv.tax.repository.purchase.suppoort

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.purchase.PurchaseCashReceiptEntity
import net.dv.tax.domain.purchase.QPurchaseCashReceiptEntity.purchaseCashReceiptEntity
import net.dv.tax.dto.purchase.PurchaseCashReceiptTotal
import net.dv.tax.dto.purchase.PurchaseCashReceiptTotalSearch
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.enums.purchase.Deduction
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository


@Repository
class PurchaseCashReceiptSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), PurchaseCashReceiptSupport {

    override fun purchaseCashReceiptList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean
    ): List<PurchaseCashReceiptEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!;

        val builder = getBuilder(hospitalId, purchaseQueryDto)

        var res: List<PurchaseCashReceiptEntity>

        if( isExcel ) {
            res = query
                .select(purchaseCashReceiptEntity)
                .from(purchaseCashReceiptEntity)
                .where(builder)
                .orderBy(purchaseCashReceiptEntity.billingDate.desc())
                .fetch()
        } else {
            res = query
                .select(purchaseCashReceiptEntity)
                .from(purchaseCashReceiptEntity)
                .where(builder)
                .orderBy(purchaseCashReceiptEntity.billingDate.desc())
                .offset(realOffset)
                .limit(purchaseQueryDto.size)
                .fetch()
        }

        return res

    }

    override fun purchaseCashReceiptListCnt(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long {
        val builder = getBuilder(hospitalId, purchaseQueryDto)

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

        val builder = getBuilder(hospitalId, purchaseQueryDto)

        var total = query
            .select(
                Projections.constructor(
                    PurchaseCashReceiptTotalSearch::class.java,
                    purchaseCashReceiptEntity.supplyPrice.sum().`as`("totalSupplyPrice"),
                    purchaseCashReceiptEntity.taxAmount.sum().`as`("totalTaxAmount"),
                    purchaseCashReceiptEntity.serviceCharge.sum().`as`("totalServiceCharge"),
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
                    purchaseCashReceiptEntity.serviceCharge.sum().`as`("totalServiceCharge"),
                    purchaseCashReceiptEntity.totalAmount.sum().`as`("totalAmount"),
                )
            )
            .from(purchaseCashReceiptEntity)
            .where(builder)
            .where(purchaseCashReceiptEntity.isDeduction.eq(false))
            .fetchFirst()

        return PurchaseCashReceiptTotal(
            totalSupplyPrice = total.totalSupplyPrice?: 0,
            totalTaxAmount = total.totalTaxAmount?: 0,
            totalServiceCharge = total.totalServiceCharge?: 0,
            totalAmount = total.totalAmount?: 0,
            totalNonSupplyPrice = nonTotal.totalSupplyPrice?: 0,
            totalNonTaxAmount = nonTotal.totalTaxAmount?: 0,
            totalNonServiceCharge = nonTotal.totalServiceCharge?: 0,
            totalNonAmount = nonTotal.totalAmount?: 0
        )
    }

    fun getBuilder(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): BooleanBuilder{

        val builder = BooleanBuilder()
        builder.and(purchaseCashReceiptEntity.hospitalId.eq(hospitalId))

        //공제 불공제 전체
        when(purchaseQueryDto.deduction){
            1L -> builder.and(purchaseCashReceiptEntity.isDeduction.eq(Deduction.Deduction_1.isDeduction))
            2L -> builder.and(purchaseCashReceiptEntity.isDeduction.eq(Deduction.Deduction_2.isDeduction))
            else -> null
        }

        return builder
    }
}