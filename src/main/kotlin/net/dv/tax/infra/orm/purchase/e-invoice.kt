package net.dv.tax.infra.orm.purchase

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.core.types.QBean
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.dto.purchase.PurchaseElecInvoiceTotal
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.*
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity
import net.dv.tax.domain.purchase.QJournalEntryEntity.journalEntryEntity
import net.dv.tax.domain.purchase.QPurchaseElecInvoiceEntity.purchaseElecInvoiceEntity
import org.hibernate.annotations.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
class PurchaseElecInvoiceRepositoryImpl(private val factory: JPAQueryFactory): PurchaseEInvoiceQuery {
    override fun purchaseBooks(hospitalId: String, bookType: String, query: PurchaseQueryDto): List<ETaxInvoiceBook> {
        val type = PurchaseElecInvoiceEntity.Type[bookType]
        val offset = query.offset!! * query.size!!
        val predicates = BooleanBuilder().apply {
            and(purchaseElecInvoiceEntity.hospitalId.eq(hospitalId))
            and(purchaseElecInvoiceEntity.type.eq(type))
        }

        return factory
            .select(mapping)
            .from(purchaseElecInvoiceEntity)
            .leftJoin(journalEntryEntity)
            .on(
                purchaseElecInvoiceEntity.id.eq(journalEntryEntity.purchaseId),
                journalEntryEntity.purchaseType.eq(bookType)
            )
            .where(predicates)
            .offset(offset)
            .limit(query.size)
            .fetch()
    }

    override fun summary(hospitalId: String, bookType: String,query: PurchaseQueryDto): BookSummary {
        val type = PurchaseElecInvoiceEntity.Type[bookType]
        val predicates = BooleanBuilder().apply {
            and(purchaseElecInvoiceEntity.hospitalId.eq(hospitalId))
            and(purchaseElecInvoiceEntity.type.eq(type))
        }

        return factory
            .select(
                Projections.constructor(
                    ETaxInvoiceSummary::class.java,
                    purchaseElecInvoiceEntity.totalAmount.sum().`as`("amount"),
                    purchaseElecInvoiceEntity.supplyPrice.sum().`as`("supplyPrice"),
                    purchaseElecInvoiceEntity.taxAmount.sum().`as`("taxAmount"),
                )
            )
            .from(purchaseElecInvoiceEntity)
            .where(predicates)
            .fetchFirst()
    }

    override fun bookCount(hospitalId: String, bookType: String, query: PurchaseQueryDto): Long {
        val type = PurchaseElecInvoiceEntity.Type[bookType]
        val predicates = BooleanBuilder().apply {
            and(purchaseElecInvoiceEntity.hospitalId.eq(hospitalId))
            and(purchaseElecInvoiceEntity.type.eq(type))
        }

        return factory
            .select(purchaseElecInvoiceEntity.count())
            .from(purchaseElecInvoiceEntity)
            .where(predicates)
            .fetchFirst()
    }

    override fun journalEntryProcessing(hospitalId: String, bookType: String, pageable: Pageable): Page<out JournalEntryStatus> {
        val type = PurchaseType[bookType]
        val query = factory
            .from(journalEntryEntity)
            .join(purchaseElecInvoiceEntity)
            .on(
                journalEntryEntity.purchaseId.eq(purchaseElecInvoiceEntity.id),
                journalEntryEntity.purchaseType.eq(type.code)
            )
            .where(
                purchaseElecInvoiceEntity.hospitalId.eq(hospitalId)
            )

        val list = query
            .select(mapping)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = query
            .select(journalEntryEntity.count())
            .fetchOne()

        return PageImpl(list, pageable, count ?: 0)
    }

    private val mapping: QBean<ETaxInvoiceBook> get() = Projections.fields(
        ETaxInvoiceBookDto::class.java,
        purchaseElecInvoiceEntity.id,
        purchaseElecInvoiceEntity.hospitalId,
        purchaseElecInvoiceEntity.issueDate,
        purchaseElecInvoiceEntity.sendDate,
        purchaseElecInvoiceEntity.accountCode,
        purchaseElecInvoiceEntity.franchiseeName,
        purchaseElecInvoiceEntity.itemName,
        purchaseElecInvoiceEntity.supplyPrice,
        purchaseElecInvoiceEntity.taxAmount,
        purchaseElecInvoiceEntity.totalAmount,
        purchaseElecInvoiceEntity.isDeduction,
        purchaseElecInvoiceEntity.debtorAccount,
        purchaseElecInvoiceEntity.separateSend,
        purchaseElecInvoiceEntity.statementStatus,
        purchaseElecInvoiceEntity.taskType,
        purchaseElecInvoiceEntity.approvalNo,
        purchaseElecInvoiceEntity.invoiceType,
        purchaseElecInvoiceEntity.billingType,
        purchaseElecInvoiceEntity.issueType,
        purchaseElecInvoiceEntity.writer,
        journalEntryEntity.accountingItem.`as`("_accountingItem"),
        journalEntryEntity.status.`as`("_status"),
        journalEntryEntity.requestedAt,
        journalEntryEntity.committedAt,
    )

    override fun purchaseElecInvoiceList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean
    ): List<PurchaseElecInvoiceEntity> {

        val realOffset = purchaseQueryDto.offset!! * purchaseQueryDto.size!!

        val builder = getBuilder( hospitalId, purchaseQueryDto)

        var res: List<PurchaseElecInvoiceEntity>

        if( isExcel ) {
            res =  factory
                .select(purchaseElecInvoiceEntity)
                .from(purchaseElecInvoiceEntity)
                .where(builder)
                .fetch()
        } else{
            res =  factory
                .select(purchaseElecInvoiceEntity)
                .from(purchaseElecInvoiceEntity)
                .where(builder)
                .offset(realOffset)
                .limit(purchaseQueryDto.size)
                .fetch()
        }

        return res
    }

    override fun purchaseElecInvoiceListCnt(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long {
        val builder = getBuilder( hospitalId, purchaseQueryDto)

        return factory
            .select(purchaseElecInvoiceEntity.count())
            .from(purchaseElecInvoiceEntity)
            .where(builder)
            .fetchFirst()
    }

    override fun purchaseElecInvoiceTotal(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto
    ): PurchaseElecInvoiceTotal {

        val builder = getBuilder( hospitalId, purchaseQueryDto)

        return factory
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

    fun getBuilder(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): BooleanBuilder {

        val builder = BooleanBuilder()
        builder.and(purchaseElecInvoiceEntity.hospitalId.eq(hospitalId))
        builder.and(purchaseElecInvoiceEntity.tax.eq(purchaseQueryDto.isTax))

        return builder
    }
}

data class ETaxInvoiceBookDto(
    override val id: Long = 0,
    override val hospitalId: String = "",
): ETaxInvoiceBook {
    @Comment("발급 일자") override var issueDate: String? = null
    @Comment("전송 일자") override var sendDate: String? = null
    @Comment("코드") override var accountCode: String? = null
    @Comment("거래처") override var franchiseeName: String? = null
    @Comment("품명") override var itemName: String? = null
    @Comment("공급가액") override var supplyPrice: Long? = null
    @Comment("세액") override var taxAmount: Long? = null
    @Comment("합계") override var totalAmount: Long? = null
    @Comment("유형(공제여부)") override var isDeduction: String? = null
    @Comment("차변계정")
    override var debtorAccount: String? = null
        get() = _accountingItem ?: field
    @Comment("대변계정") override var creditAccount: String? = null
    @Comment("분개전송") override var separateSend: String? = null
    @Comment("전표상태") override var statementStatus: String? = null
    @Comment("작업상태") override var taskType: String? = null
    @Comment("승인번호") override var approvalNo: String? = null
    @Comment("종류(계산서)") override var invoiceType: String? = null
    @Comment("구분(청구)") override var billingType: String? = null
    @Comment("발급유형") override var issueType: String? = null
    @Comment("작성자") override var writer: String? = null
    override var status: String?
        get() = _status?.name
        set(value) {}

    override var requestedAt: LocalDateTime? = null
    override var committedAt: LocalDateTime? = null

    private var _status: JournalEntryEntity.Status? = null
    private var _accountingItem: String? = null
}

data class ETaxInvoiceSummary(
    val supplyPrice: Long? = 0,
    val taxAmount: Long? = 0,
    val amount: Long? = 0,
): BookSummary