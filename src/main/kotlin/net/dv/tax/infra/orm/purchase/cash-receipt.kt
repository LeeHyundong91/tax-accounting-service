package net.dv.tax.infra.orm.purchase

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.dto.purchase.PurchaseCashReceiptTotal
import net.dv.tax.app.dto.purchase.PurchaseCashReceiptTotalSearch
import net.dv.tax.app.enums.purchase.Deduction
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.*
import net.dv.tax.domain.purchase.*
import net.dv.tax.domain.purchase.QJournalEntryEntity.journalEntryEntity
import net.dv.tax.domain.purchase.QPurchaseCashReceiptEntity.purchaseCashReceiptEntity
import org.hibernate.annotations.Comment
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
class PurchaseCashReceiptRepositoryImpl(private val factory: JPAQueryFactory): PurchaseCashReceiptQuery {
    override fun purchaseBooks(hospitalId: String, query: PurchaseQueryDto): List<CashReceiptBook> {
        val realOffset = query.offset!! * query.size!!
        val predicates = BooleanBuilder().apply {
            and(purchaseCashReceiptEntity.hospitalId.eq(hospitalId))
            //공제 불공제 전체
            when (query.deduction) {
                1L -> and(purchaseCashReceiptEntity.isDeduction.eq(true))
                2L -> and(purchaseCashReceiptEntity.isDeduction.eq(false))
            }
        }

        return factory
            .select(
                Projections.fields(
                    CashReceiptBookDto::class.java,
                    purchaseCashReceiptEntity.id,
                    purchaseCashReceiptEntity.hospitalId,
                    purchaseCashReceiptEntity.dataFileId,
                    purchaseCashReceiptEntity.accountCode,
                    purchaseCashReceiptEntity.franchiseeName,
                    purchaseCashReceiptEntity.corporationType,
                    purchaseCashReceiptEntity.itemName,
                    purchaseCashReceiptEntity.supplyPrice,
                    purchaseCashReceiptEntity.taxAmount,
                    purchaseCashReceiptEntity.serviceCharge,
                    purchaseCashReceiptEntity.totalAmount,
                    purchaseCashReceiptEntity.isDeduction,
                    purchaseCashReceiptEntity.isRecommendDeduction,
                    purchaseCashReceiptEntity.statementType1,
                    purchaseCashReceiptEntity.statementType2,
                    purchaseCashReceiptEntity.debtorAccount,
                    purchaseCashReceiptEntity.creditAccount,
                    purchaseCashReceiptEntity.separateSend,
                    purchaseCashReceiptEntity.department,
                    purchaseCashReceiptEntity.statementStatus,
                    purchaseCashReceiptEntity.writer,
                    journalEntryEntity.accountingItem.`as`("_accountingItem"),
                    journalEntryEntity.status.`as`("_status"),
                    journalEntryEntity.requestedAt,
                    journalEntryEntity.committedAt,
                )
            )
            .from(purchaseCashReceiptEntity)
            .leftJoin(journalEntryEntity)
            .on(
                purchaseCashReceiptEntity.id.eq(journalEntryEntity.purchaseId),
                journalEntryEntity.purchaseType.eq(PurchaseType.CASH_RECEIPT.code)
            )
            .where(predicates)
            .orderBy(purchaseCashReceiptEntity.billingDate.desc())
            .offset(realOffset)
            .limit(query.size)
            .fetch()
    }

    override fun summary(hospitalId: String, query: PurchaseQueryDto): BookSummary {
        val predicates = BooleanBuilder().apply {
            and(purchaseCashReceiptEntity.hospitalId.eq(hospitalId))
        }
        val deduction = factory
            .select(
                Projections.constructor(
                    CashReceiptSummary.DS::class.java,
                    purchaseCashReceiptEntity.supplyPrice.sum(),
                    purchaseCashReceiptEntity.taxAmount.sum(),
                    purchaseCashReceiptEntity.serviceCharge.sum(),
                    purchaseCashReceiptEntity.totalAmount.sum(),
                )

            )
            .from(purchaseCashReceiptEntity)
            .where(
                predicates,
                purchaseCashReceiptEntity.isDeduction.eq(true)
            )
            .fetchFirst()

        val nonDeduction = factory
            .select(
                Projections.constructor(
                    CashReceiptSummary.DS::class.java,
                    purchaseCashReceiptEntity.supplyPrice.sum(),
                    purchaseCashReceiptEntity.taxAmount.sum(),
                    purchaseCashReceiptEntity.serviceCharge.sum(),
                    purchaseCashReceiptEntity.totalAmount.sum(),
                )
            )
            .from(purchaseCashReceiptEntity)
            .where(
                predicates,
                purchaseCashReceiptEntity.isDeduction.eq(false)
            )
            .fetchFirst()

        return CashReceiptSummary(deduction, nonDeduction)
    }

    override fun purchaseCashReceiptList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean
    ): List<PurchaseCashReceiptEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!
        val builder = getBuilder(hospitalId, purchaseQueryDto)
        val res: List<PurchaseCashReceiptEntity>

        if( isExcel ) {
            res = factory
                .select(purchaseCashReceiptEntity)
                .from(purchaseCashReceiptEntity)
                .where(builder)
                .orderBy(purchaseCashReceiptEntity.billingDate.desc())
                .fetch()
        } else {
            res = factory
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

        return factory
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

        val total = factory
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

        val nonTotal = factory
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

    private fun getBuilder(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): BooleanBuilder {
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


data class CashReceiptBookDto(
    override val id: Long = 0,
    override var hospitalId: String = "",
): CashReceiptBook {
    @Comment("업로드 파일 ID") override var dataFileId: Long? = null
    @Comment("일자") override var billingDate: String? = null
    @Comment("회계코드") override var accountCode: String? = null
    @Comment("거래처") override var franchiseeName: String? = null
    @Comment("구분") override var corporationType: String? = null
    @Comment("품명") override var itemName: String? = null
    @Comment("공급가액") override var supplyPrice: Long = 0
    @Comment("세액") override var taxAmount: Long = 0
    @Comment("봉사료") override var serviceCharge: Long = 0
    @Comment("합계") override var totalAmount: Long = 0
    @get:Comment("국세청(공제여부)명") override val deductionName: String
        get() = _deduction?.takeIf { it }?.let { "공제" }?: "불공제"
    @get:Comment("추천유형(불공제)명") override val recommendDeductionName: String
        get() = _recommendDeduction?.takeIf { it }?.let { "불공제" } ?: ""
    @Comment("전표유형 1") override var statementType1: String? = null
    @Comment("전표유형 2") override var statementType2: String? = null
    @Comment("차변계정")
    override var debtorAccount: String? = null
        get() = _accountingItem ?: field
    @Comment("대변계정") override var creditAccount: String? = null
    @Comment("분개전송") override var separateSend: String? = null
    @Comment("부서") override var department: String? = null
    @Comment("전표상태") override var statementStatus: String? = null
    @Comment("작성자") override var writer: String? = null
    override var status: String?
        get() = _status?.name
        set(value) {}

    override var requestedAt: LocalDateTime? = null
    override var committedAt: LocalDateTime? = null

    private var _deduction: Boolean? = null
    private var _recommendDeduction: Boolean? = null
    private var _status: JournalEntryEntity.Status? = null
    private var _accountingItem: String? = null
}

data class CashReceiptSummary(
    @Comment("공제") val deduction: DS,
    @Comment("불공제") val nonDeduction: DS,
): BookSummary {
    data class DS(
        val supplyPrice: Long?,
        val taxAmount: Long?,
        val serviceCharge: Long?,
        val amount: Long?,
    )
}
