package net.dv.tax.infra.orm.purchase

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.dto.purchase.PurchaseCreditCardTotal
import net.dv.tax.app.dto.purchase.PurchaseCreditCardTotalSearch
import net.dv.tax.app.enums.purchase.Deduction
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.BookSummary
import net.dv.tax.app.purchase.CreditCardBook
import net.dv.tax.app.purchase.PurchaseCreditCardQuery
import net.dv.tax.app.purchase.PurchaseQueryDto
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.domain.purchase.QJournalEntryEntity.journalEntryEntity
import net.dv.tax.domain.purchase.QPurchaseCreditCardEntity.purchaseCreditCardEntity
import org.hibernate.annotations.Comment
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class PurchaseCreditCardRepositoryImpl(private val factory: JPAQueryFactory) : PurchaseCreditCardQuery {

    override fun purchaseBooks(hospitalId: String, query: PurchaseQueryDto): List<CreditCardBook> {
        val realOffset = query.offset!! * query.size!!
        val predicates = BooleanBuilder()
        predicates.and(purchaseCreditCardEntity.hospitalId.eq(hospitalId))

        //공제 불공제 전체
        when(query.deduction){
            1L -> predicates.and(purchaseCreditCardEntity.isDeduction.eq(true))
            2L -> predicates.and(purchaseCreditCardEntity.isDeduction.eq(false))
        }

        return factory
            .select(Projections.fields(
                CreditCardBookDto::class.java,
                purchaseCreditCardEntity.id,
                purchaseCreditCardEntity.hospitalId,
                purchaseCreditCardEntity.billingDate,
                purchaseCreditCardEntity.accountCode,
                purchaseCreditCardEntity.franchiseeName,
                purchaseCreditCardEntity.corporationType,
                purchaseCreditCardEntity.itemName,
                purchaseCreditCardEntity.supplyPrice,
                purchaseCreditCardEntity.taxAmount,
                purchaseCreditCardEntity.nonTaxAmount,
                purchaseCreditCardEntity.totalAmount,
                purchaseCreditCardEntity.isDeduction.`as`("_deduction"),
                purchaseCreditCardEntity.isRecommendDeduction.`as`("_recommendDeduction"),
                purchaseCreditCardEntity.statementType1,
                purchaseCreditCardEntity.statementType2,
                purchaseCreditCardEntity.debtorAccount,
                purchaseCreditCardEntity.creditAccount,
                purchaseCreditCardEntity.separateSend,
                purchaseCreditCardEntity.statementStatus,
                purchaseCreditCardEntity.writer,
                purchaseCreditCardEntity.createdAt,
                journalEntryEntity.status.`as`("_status"),
                journalEntryEntity.accountingItem.`as`("_accountingItem"),
                journalEntryEntity.requestedAt,
                journalEntryEntity.committedAt,
            ))
            .from(purchaseCreditCardEntity)
            .leftJoin(journalEntryEntity)
            .on(
                purchaseCreditCardEntity.id.eq(journalEntryEntity.purchaseId),
                journalEntryEntity.purchaseType.eq(PurchaseType.CREDIT_CARD.code)
            )
            .where(predicates)
            .orderBy(purchaseCreditCardEntity.billingDate.desc())
            .offset(realOffset)
            .limit(query.size)
            .fetch()
    }

    override fun summary(hospitalId: String, query: PurchaseQueryDto): BookSummary {
        val predicates = BooleanBuilder()
            .and(purchaseCreditCardEntity.hospitalId.eq(hospitalId))
        val deduction = factory
            .select(
                Projections.constructor(
                    CreditCardSummary.DS::class.java,
                    purchaseCreditCardEntity.supplyPrice.sum(),
                    purchaseCreditCardEntity.taxAmount.sum(),
                    purchaseCreditCardEntity.totalAmount.sum(),
                )
            )
            .from(purchaseCreditCardEntity)
            .where(
                predicates,
                purchaseCreditCardEntity.isDeduction.eq(true)
            )
            .fetchFirst()

        val nonDeduction = factory
            .select(
                Projections.constructor(
                    CreditCardSummary.DS::class.java,
                    purchaseCreditCardEntity.supplyPrice.sum(),
                    purchaseCreditCardEntity.taxAmount.sum(),
                    purchaseCreditCardEntity.totalAmount.sum(),
                )
            )
            .from(purchaseCreditCardEntity)
            .where(
                predicates,
                purchaseCreditCardEntity.isDeduction.eq(false)
            )
            .fetchFirst()

        return CreditCardSummary(deduction, nonDeduction)
    }

    override fun purchaseCreditCardList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean
    ): List<PurchaseCreditCardEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!

        val builder = getBuilder( hospitalId, purchaseQueryDto)

        val res: List<PurchaseCreditCardEntity>

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
        val total = factory
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

        val nonTotal = factory
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
        }

        return builder
    }
}

data class CreditCardBookDto(
    override val id: Long = 0,
    override val hospitalId: String = ""
): CreditCardBook {
    @Comment("업로드 파일 ID") override var dataFileId: Long? = null
    @Comment("일자") override var billingDate: String? = null
    @Comment("코드") override var accountCode: String? = null
    @Comment("거래처") override var franchiseeName: String? = null
    @Comment("구분") override var corporationType: String? = null
    @Comment("품명") override var itemName: String? = null
    @Comment("공급가액") override var supplyPrice: Long? = null
    @Comment("세액") override var taxAmount: Long? = 0
    @Comment("비과세") override var nonTaxAmount: Long? = 0
    @Comment("합계") override var totalAmount: Long? = 0
    @get:Comment("국세청(공제여부)명") override val deductionName: String
        get() = _deduction?.takeIf { it }?.let { "공제" }?: "불공제"
    @get:Comment("추천유형(불공제)명") override val recommendDeductionName: String
        get() = _recommendDeduction?.takeIf { it }?.let { "불공제" } ?: ""
    @Comment("전표유형1") override var statementType1: String? = null
    @Comment("전표유형2") override var statementType2: String? = null
    @Comment("차변계정")
    override var debtorAccount: String? = null
        get() = _accountingItem ?: field
    @Comment("대변계정") override var creditAccount: String? = null
    @Comment("분개전송") override var separateSend: String? = null
    @Comment("전표상태") override var statementStatus: String? = null
    @Comment("작성자") override var writer: String? = null
    @Comment("삭제") var isDelete: Boolean? = false
    @Comment("등록일(업로드일시") override val createdAt: LocalDateTime? = null

    override var status: String? = null
        get() = _status?.name ?: field
    override var requestedAt: LocalDateTime? = null
    override var committedAt: LocalDateTime? = null

    private var _deduction: Boolean? = null
    private var _recommendDeduction: Boolean? = null
    private var _status: JournalEntryEntity.Status? = null
    private var _accountingItem: String? = null
}

data class CreditCardSummary(
    @Comment("공제") val deduction: DS,
    @Comment("불공제") val nonDeduction: DS,
): BookSummary {
    data class DS(
        val supplyPrice: Long?,
        val taxAmount: Long?,
        val amount: Long?,
    )
}