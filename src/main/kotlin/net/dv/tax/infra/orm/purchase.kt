package net.dv.tax.infra.orm

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.common.CustomQuerydslRepositorySupport
import net.dv.tax.app.dto.purchase.PurchaseCreditCardDto
import net.dv.tax.app.dto.purchase.PurchaseCreditCardTotal
import net.dv.tax.app.dto.purchase.PurchaseCreditCardTotalSearch
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.app.enums.purchase.Deduction
import net.dv.tax.app.purchase.*
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.domain.purchase.QJournalEntryEntity.journalEntryEntity
import net.dv.tax.domain.purchase.QJournalEntryHistoryEntity.journalEntryHistoryEntity
import net.dv.tax.domain.purchase.QPurchaseCreditCardEntity.purchaseCreditCardEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
class PurchaseCreditCardRepositoryImpl(private val factory: JPAQueryFactory) : PurchaseCreditCardQuery {

    override fun purchaseBooks(hospitalId: String, query: PurchaseQueryDto): List<PurchaseCreditCardDto> {
        val realOffset = query.offset!! * query.size!!;
        TODO()
    }

    override fun purchaseCreditCardList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean
    ): List<PurchaseCreditCardEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!;

        val builder = getBuilder( hospitalId, purchaseQueryDto)

        var res: List<PurchaseCreditCardEntity>

        if( isExcel ) {
            res = factory
                .select(purchaseCreditCardEntity)
                .from(purchaseCreditCardEntity)
                .where(builder)
                .orderBy(purchaseCreditCardEntity.billingDate.desc())
                .fetch()
        } else {
            res = factory
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
        return factory
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
        var total = factory
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

        var nonTotal = factory
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

    fun getBuilder(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): BooleanBuilder {
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


@Repository
class PurchaseJournalEntryRepositoryImpl(
    private val factory: JPAQueryFactory
): PurchaseJournalEntryQuery {
    override fun search(): List<PurchaseData> {
        val cc = factory
            .from(purchaseCreditCardEntity)
            .where(purchaseCreditCardEntity.hospitalId.eq("test-id"))

        val cr = factory
            .from(purchaseCreditCardEntity)

        return listOf()
    }

    override fun find(purchase: PurchaseBook): JournalEntryEntity? {
        return factory
            .select(journalEntryEntity)
            .from(journalEntryEntity)
            .where(
                journalEntryEntity.purchaseId.eq(purchase.id),
                journalEntryEntity.purchaseType.eq(purchase.type.code)
            )
            .fetchOne()
    }
}

@Repository
class PurchaseJournalEntryHistoryRepositoryImpl(
    private val factory: JPAQueryFactory
): PurchaseJournalEntryHistoryQuery {
    override fun find(purchase: PurchaseBook): List<JournalEntryHistoryEntity> {
        return factory
            .select(journalEntryHistoryEntity)
            .from(journalEntryHistoryEntity)
            .where(
                journalEntryHistoryEntity.purchaseId.eq(purchase.id),
                journalEntryHistoryEntity.purchaseType.eq(purchase.type.code)
            )
            .fetch()
    }
}