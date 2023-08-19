package net.dv.tax.infra.orm.purchase

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.AccountingItemCategory
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.*
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import net.dv.tax.domain.purchase.QJournalEntryEntity.journalEntryEntity
import net.dv.tax.domain.purchase.QJournalEntryHistoryEntity.journalEntryHistoryEntity
import net.dv.tax.domain.purchase.QPurchaseCashReceiptEntity.purchaseCashReceiptEntity
import net.dv.tax.domain.purchase.QPurchaseCreditCardEntity.purchaseCreditCardEntity
import net.dv.tax.domain.purchase.QPurchaseElecInvoiceEntity.purchaseElecInvoiceEntity
import net.dv.tax.domain.purchase.QPurchaseHandwrittenEntity.purchaseHandwrittenEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
class PurchaseJournalEntryRepositoryImpl(
    private val factory: JPAQueryFactory,
    private val jdbcTemplate: NamedParameterJdbcTemplate,
): PurchaseJournalEntryQuery {
    override fun find(purchase: PurchaseBookIdentity): JournalEntryEntity? {
        return factory
            .select(journalEntryEntity)
            .from(journalEntryEntity)
            .where(
                journalEntryEntity.purchaseId.eq(purchase.id),
                journalEntryEntity.purchaseType.eq(purchase.type.code)
            )
            .fetchOne()
    }

    override fun expense(hospitalId: String, filter: ExpenseFilter, pageable: Pageable): Page<PurchaseBookOverview> {
        val params = MapSqlParameterSource().apply {
            addValue("hospitalId", hospitalId)
            addValue("begin", filter.period.begin)
            addValue("end", filter.period.end)
            addValue("category", filter.category.code)
            addValue("offset", pageable.offset)
            addValue("size", pageable.pageSize)
            addValue("bookTypes", filter.types?.map { it.code } ?: PurchaseType.values().map { it.code })
        }

        val query =
            """
                ${QS.CREDIT_CARD_BOOKS}    
                union all
                ${QS.CASH_RECEIPT_BOOKS}    
                union all
                ${QS.E_INVOICE_BOOKS}    
                union all
                ${QS.HANDWRITTEN_PURCHASE_BOOKS}    
            """.trimIndent()

        val unionQuery = QS.UNION_QUERY.format(query)

        val list = jdbcTemplate.query(unionQuery, params) { rs, _ ->
            object: PurchaseBookOverview {
                override val id: Long = rs.getLong("ID")
                override val merchant: String? = rs.getString("FRANCHISEE_NAME")
                override val item: String? = rs.getString("ITEM_NAME")
                override val transactionDate: String = rs.getString("BILLING_DATE")
                override val amount: Long = rs.getLong("TOTAL_AMOUNT")
                override val type: PurchaseType = rs.getString("PURCHASE_TYPE").let {PurchaseType[it]}
                val debtorAccount get() = _accountingItem ?: _debtorAccount
                val category get() = AccountingItemCategory.category(debtorAccount)

                private val _debtorAccount = rs.getString("DEBTOR_ACCOUNT")
                private val _accountingItem = rs.getString("ACCOUNTING_ITEM")
            }
        } as List<PurchaseBookOverview>

        val countQuery = QS.COUNT_QUERY.format(query)
        val (count, amount) = jdbcTemplate.queryForObject(countQuery, params) { rs, _ ->
            listOf(rs.getLong("TOTAL_COUNT"), rs.getLong("TOTAL_AMOUNT"))
        } ?: listOf(0L, 0L)

        return object: Page<PurchaseBookOverview> by PageImpl(list, pageable, count) {
            val totalAmount: Long = amount
        }
    }

    override fun overview(purchase: PurchaseBookIdentity): JournalEntryOverview? {
        val m = when(purchase.type) {
                PurchaseType.CREDIT_CARD -> {
                    Mappings(
                        entity = purchaseCreditCardEntity,
                        join_predicate = purchaseCreditCardEntity.id.eq(journalEntryEntity.purchaseId),
                        predicates = purchaseCreditCardEntity.id.eq(purchase.id),
                        columns = arrayOf(
                            purchaseCreditCardEntity.id,
                            purchaseCreditCardEntity.franchiseeName.`as`("merchant"),
                            purchaseCreditCardEntity.itemName.`as`("item"),
                            purchaseCreditCardEntity.billingDate.`as`("transactionDate"),
                            purchaseCreditCardEntity.taxAmount.`as`("amount"),
                            Expressions.`as`(Expressions.constant(purchase.type.code), "_type"),
                        )
                    )
                }
                PurchaseType.CASH_RECEIPT -> {
                    Mappings(
                        entity = purchaseCashReceiptEntity,
                        join_predicate = purchaseCashReceiptEntity.id.eq(journalEntryEntity.purchaseId),
                        predicates = purchaseCashReceiptEntity.id.eq(purchase.id),
                        columns = arrayOf(
                            purchaseCashReceiptEntity.id,
                            purchaseCashReceiptEntity.franchiseeName.`as`("merchant"),
                            purchaseCashReceiptEntity.itemName.`as`("item"),
                            purchaseCashReceiptEntity.billingDate.`as`("transactionDate"),
                            purchaseCashReceiptEntity.taxAmount.`as`("amount"),
                        )
                    )
                }
                PurchaseType.INVOICE,
                PurchaseType.TAX_INVOICE -> {
                    Mappings(
                        entity = purchaseElecInvoiceEntity,
                        join_predicate = purchaseElecInvoiceEntity.id.eq(journalEntryEntity.purchaseId)
                            .and(purchaseElecInvoiceEntity.type.stringValue().eq(purchase.type.code))
                        ,
                        predicates = purchaseElecInvoiceEntity.id.eq(purchase.id),
                        columns = arrayOf(
                            purchaseElecInvoiceEntity.id,
                            purchaseElecInvoiceEntity.franchiseeName.`as`("merchant"),
                            purchaseElecInvoiceEntity.itemName.`as`("item"),
                            purchaseElecInvoiceEntity.sendDate.`as`("transactionDate"),
                            purchaseElecInvoiceEntity.taxAmount.`as`("amount"),
                            purchaseElecInvoiceEntity.type.stringValue().`as`("_type"),
                        ),
                    )
                }
                PurchaseType.HANDWRITTEN_INVOICE,
                PurchaseType.BASIC_RECEIPT -> {
                    Mappings(
                        entity = purchaseHandwrittenEntity,
                        join_predicate = purchaseHandwrittenEntity.id.eq(journalEntryEntity.purchaseId)
                            .and(purchaseHandwrittenEntity.type.stringValue().eq(purchase.type.code)),
                        predicates = purchaseHandwrittenEntity.id.eq(purchase.id),
                        columns = arrayOf(
                            purchaseHandwrittenEntity.id,
                            purchaseHandwrittenEntity.supplier.`as`("merchant"),
                            purchaseHandwrittenEntity.itemName.`as`("item"),
                            purchaseHandwrittenEntity.issueDate.`as`("transactionDate"),
                            purchaseHandwrittenEntity.taxAmount.`as`("amount"),
                            purchaseHandwrittenEntity.type.stringValue().`as`("_type"),
                        )
                    )
                }
            }

        return factory
            .select(
                Projections.fields(
                    JournalEntryDto::class.java,
                    *m.columns,
                    journalEntryEntity.status.`as`("_status"),
                    journalEntryEntity.requestedAt,
                    journalEntryEntity.committedAt,
                    journalEntryEntity.requestNote,
                    journalEntryEntity.checkExpense,
                    journalEntryEntity.accountingItem,
                    journalEntryEntity.committer,
                )
            )
            .from(m.entity)
            .leftJoin(journalEntryEntity)
            .on(m.join_predicate)
            .where(m.predicates)
            .fetchOne()
    }

    @Suppress("PropertyName")
    private class Mappings(
        val entity: EntityPathBase<out Any>,
        val join_predicate: BooleanExpression,
        val predicates: BooleanExpression,
        val columns: Array<Expression<*>>,
    )
}

@Repository
class PurchaseJournalEntryHistoryRepositoryImpl(
    private val factory: JPAQueryFactory
): PurchaseJournalEntryHistoryQuery {
    override fun find(purchase: PurchaseBookIdentity): List<JournalEntryHistoryEntity> {
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

data class JournalEntryDto(
    override var id: Long = 0,
    override var merchant: String? = null,
    override var item: String? = null,
    override var transactionDate: String = "",
    override var amount: Long = 0,
    override var requestedAt: LocalDateTime? = null,
    override var committedAt: LocalDateTime? = null,
    override var note: String = "",
    override var checkExpense: Boolean = false,
    override var accountingItem: String? = null,
    override var requester: String? = null,
    override var committer: String? = null,
): JournalEntryOverview {
    override val type: PurchaseType get() = PurchaseType[_type]
    override val status: String? get() = _status?.name

    private var _status: JournalEntryEntity.Status? = null
    private var _type: String = ""
}


private object QS {
    const val CREDIT_CARD_BOOKS = """
       SELECT C.ID
             ,C.FRANCHISEE_NAME 
             ,C.BILLING_DATE
             ,C.ITEM_NAME
             ,C.TOTAL_AMOUNT
             ,C.DEBTOR_ACCOUNT
             ,J.ACCOUNTING_ITEM
             ,'CC' AS PURCHASE_TYPE
       FROM PURCHASE_CREDIT_CARD C 
       LEFT OUTER JOIN JOURNAL_ENTRY J
       ON C.ID = J.PURCHASE_ID AND J.PURCHASE_TYPE = 'CC'
       WHERE C.HOSPITAL_ID = :hospitalId
       AND 'CC' IN (:bookTypes)
       AND C.BILLING_DATE BETWEEN :begin AND :end
       AND (
         ('ALL' = :category AND 1 = 1)
         OR ('NC' = :category AND C.DEBTOR_ACCOUNT IS NULL)
         OR ('RC' = :category AND C.DEBTOR_ACCOUNT = '확인필요')
         OR ('CG' = :category AND C.DEBTOR_ACCOUNT <> '확인필요' AND C.DEBTOR_ACCOUNT IS NOT NULL)
       )
    """

    const val CASH_RECEIPT_BOOKS = """
       SELECT R.ID
             ,R.FRANCHISEE_NAME 
             ,R.BILLING_DATE
             ,R.ITEM_NAME
             ,R.TOTAL_AMOUNT
             ,R.DEBTOR_ACCOUNT
             ,J.ACCOUNTING_ITEM
             ,'CR' AS PURCHASE_TYPE
       FROM PURCHASE_CASH_RECEIPT R 
       LEFT OUTER JOIN JOURNAL_ENTRY J
       ON R.ID = J.PURCHASE_ID AND J.PURCHASE_TYPE = 'CR'
       WHERE R.HOSPITAL_ID = :hospitalId
       AND 'CR' IN (:bookTypes)
       AND R.BILLING_DATE BETWEEN :begin AND :end
       AND (
         ('ALL' = :category AND 1 = 1)
         OR ('NC' = :category AND R.DEBTOR_ACCOUNT IS NULL)
         OR ('RC' = :category AND R.DEBTOR_ACCOUNT = '확인필요')
         OR ('CG' = :category AND R.DEBTOR_ACCOUNT <> '확인필요' AND R.DEBTOR_ACCOUNT IS NOT NULL)
       )  
    """

    const val E_INVOICE_BOOKS = """
        SELECT I.ID
              ,I.FRANCHISEE_NAME 
              ,I.SEND_DATE
              ,I.ITEM_NAME
              ,I.TOTAL_AMOUNT
              ,I.DEBTOR_ACCOUNT
              ,J.ACCOUNTING_ITEM
              ,I.BOOK_TYPE AS PURCHASE_TYPE
        FROM PURCHASE_ELEC_INVOICE I
        LEFT OUTER JOIN JOURNAL_ENTRY J
        ON I.ID = J.PURCHASE_ID AND I.BOOK_TYPE = J.PURCHASE_TYPE
        WHERE I.HOSPITAL_ID = :hospitalId
        AND I.BOOK_TYPE IN (:bookTypes)
        AND I.SEND_DATE BETWEEN :begin AND :end
        AND (
          ('ALL' = :category AND 1 = 1)
          OR ('NC' = :category AND I.DEBTOR_ACCOUNT IS NULL)
          OR ('RC' = :category AND I.DEBTOR_ACCOUNT = '확인필요')
          OR ('CG' = :category AND I.DEBTOR_ACCOUNT <> '확인필요' AND I.DEBTOR_ACCOUNT IS NOT NULL)
        )  
    """

    const val HANDWRITTEN_PURCHASE_BOOKS = """
        SELECT H.ID
              ,H.SUPPLIER 
              ,H.ISSUE_DATE
              ,H.ITEM_NAME
              ,H.SUPPLY_PRICE + H.TAX_AMOUNT
              ,H.DEBIT_ACCOUNT
              ,J.ACCOUNTING_ITEM
              ,H.BOOK_TYPE AS PURCHASE_TYPE
        FROM PURCHASE_HANDWRITTEN H
        LEFT OUTER JOIN JOURNAL_ENTRY J
        ON H.ID = J.PURCHASE_ID AND H.BOOK_TYPE = J.PURCHASE_TYPE 
        WHERE H.HOSPITAL_ID = :hospitalId
        AND H.BOOK_TYPE IN (:bookTypes)
        AND H.ISSUE_DATE BETWEEN :begin AND :end
        AND (
          ('ALL' = :category AND 1 = 1)
          OR ('NC' = :category AND H.DEBIT_ACCOUNT IS NULL)
          OR ('RC' = :category AND H.DEBIT_ACCOUNT = '확인필요')
          OR ('CG' = :category AND H.DEBIT_ACCOUNT <> '확인필요' AND H.DEBIT_ACCOUNT IS NOT NULL)
        )
    """

    const val UNION_QUERY = """
        SELECT * 
        FROM (
          %s
        ) AS U
       ORDER BY BILLING_DATE DESC
       LIMIT :offset, :size
    """

    const val COUNT_QUERY = """
        SELECT  COUNT(*) as TOTAL_COUNT
               ,SUM(TOTAL_AMOUNT) as TOTAL_AMOUNT
        FROM ( 
          %s 
        ) AS U
    """
}