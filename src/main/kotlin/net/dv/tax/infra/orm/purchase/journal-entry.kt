package net.dv.tax.infra.orm.purchase

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.*
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import net.dv.tax.domain.purchase.QJournalEntryEntity
import net.dv.tax.domain.purchase.QJournalEntryHistoryEntity
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
            .select(QJournalEntryEntity.journalEntryEntity)
            .from(QJournalEntryEntity.journalEntryEntity)
            .where(
                QJournalEntryEntity.journalEntryEntity.purchaseId.eq(purchase.id),
                QJournalEntryEntity.journalEntryEntity.purchaseType.eq(purchase.type.code)
            )
            .fetchOne()
    }

    override fun expense(hospitalId: String, query: JournalEntryCommand.Query, pageable: Pageable): Page<PurchaseBookOverview> {
        val params = MapSqlParameterSource().apply {
            addValue("purchaseType", "CC")
            addValue("hospitalId", hospitalId)
            addValue("begin", "2022-01-01")
            addValue("end", "2023-12-31")
            addValue("category", "ALL")
            addValue("offset", pageable.offset)
            addValue("size", pageable.pageSize)
        }

        val list = jdbcTemplate.query(JournalEntryQueries.CREDIT_CARD_BOOKS, params) { rs, _ ->
            object: PurchaseBookOverview {
                override val id: Long = rs.getLong("ID")
                override val merchant: String? = rs.getString("FRANCHISEE_NAME")
                override val item: String? = rs.getString("ITEM_NAME")
                override val transactionDate: String = rs.getString("BILLING_DATE")
                override val amount: Long = rs.getLong("TOTAL_AMOUNT")
                override val type: PurchaseType = PurchaseType.CREDIT_CARD
            }
        } as List<PurchaseBookOverview>

        val cq = JournalEntryQueries.COUNT_QUERY.format(JournalEntryQueries.CREDIT_CARD_BOOKS)

        val count = jdbcTemplate.queryForObject(cq, params) { rs, _ -> rs.getLong(1) }

        return PageImpl(list, pageable, count ?: 0)
    }
}

@Repository
class PurchaseJournalEntryHistoryRepositoryImpl(
    private val factory: JPAQueryFactory
): PurchaseJournalEntryHistoryQuery {
    override fun find(purchase: PurchaseBookIdentity): List<JournalEntryHistoryEntity> {
        return factory
            .select(QJournalEntryHistoryEntity.journalEntryHistoryEntity)
            .from(QJournalEntryHistoryEntity.journalEntryHistoryEntity)
            .where(
                QJournalEntryHistoryEntity.journalEntryHistoryEntity.purchaseId.eq(purchase.id),
                QJournalEntryHistoryEntity.journalEntryHistoryEntity.purchaseType.eq(purchase.type.code)
            )
            .fetch()
    }
}

data class JournalEntryDto(
    override val id: Long = 0,
    override val type: PurchaseType,
    override var status: String? = null,
    override var requestedAt: LocalDateTime? = null,
    override var committedAt: LocalDateTime? = null,
    override var merchant: String = "",
    override var note: String = "",
    override var checkExpense: Boolean = false,
    override var accountingItem: String? = null,
    override var requester: String? = null,
    override var committer: String? = null,
): JournalEntry, PurchaseBookIdentity


object JournalEntryQueries {
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
       ON C.ID = J.PURCHASE_ID AND J.PURCHASE_TYPE = :purchaseType
       WHERE C.HOSPITAL_ID = :hospitalId
       AND C.BILLING_DATE BETWEEN :begin AND :end
       AND (
         ('ALL' = :category AND 1 = 1)
         OR ('NC' = :category AND C.DEBTOR_ACCOUNT IS NULL)
         OR ('NN' = :category AND C.DEBTOR_ACCOUNT = '확인필요')
         OR ('CC' = :category AND C.DEBTOR_ACCOUNT <> '확인필요' AND C.DEBTOR_ACCOUNT IS NOT NULL)
       )
       ORDER BY BILLING_DATE desc
       LIMIT :offset, :size
    """

    const val CASH_RECEIPT_BOOKS = """
       SELECT R.FRANCHISEE_NAME 
             ,R.BILLING_DATE
             ,R.TOTAL_AMOUNT
             ,R.DEBTOR_ACCOUNT
             ,J.ACCOUNTING_ITEM
             ,J.PURCHASE_TYPE
       FROM PURCHASE_CASH_RECEIPT R 
       LEFT OUTER JOIN JOURNAL_ENTRY J
       ON R.ID = J.PURCHASE_ID AND J.PURCHASE_TYPE = ?
       WHERE R.HOSPITAL_ID = ?
       AND R.BILLING_DATE BETWEEN ? AND ?
       AND (
         ('ALL' = ? AND 1 = 1)
         OR ('미분류' = ? AND R.DEBTOR_ACCOUNT IS NULL)
         OR ('확인필요' = ? AND R.DEBTOR_ACCOUNT = '확인필요')
         OR ('분류완료' = ? AND R.DEBTOR_ACCOUNT <> '확인필요' AND R.DEBTOR_ACCOUNT IS NOT NULL)
       )  
    """

    const val E_INVOICE_BOOKS = """
        SELECT I.FRANCHISEE_NAME 
              ,I.SEND_DATE
              ,I.TOTAL_AMOUNT
              ,I.DEBTOR_ACCOUNT
              ,J.ACCOUNTING_ITEM
              ,J.PURCHASE_TYPE
        FROM PURCHASE_ELEC_INVOICE I
        LEFT OUTER JOIN JOURNAL_ENTRY J
        ON I.ID = J.PURCHASE_ID AND J.PURCHASE_TYPE = ? 
        WHERE I.HOSPITAL_ID = ?
        AND I.SEND_DATE BETWEEN ? AND ?
        AND (
          ('ALL' = ? AND 1 = 1)
          OR ('미분류' = ? AND I.DEBTOR_ACCOUNT IS NULL)
          OR ('확인필요' = ? AND I.DEBTOR_ACCOUNT = '확인필요')
          OR ('분류완료' = ? AND I.DEBTOR_ACCOUNT <> '확인필요' AND I.DEBTOR_ACCOUNT IS NOT NULL)
        )  
    """

    const val HANDWRITTEN_PURCHASE_BOOKS = """
        SELECT 
        FROM PURCHASE_HANDWRITTEN H
        LEFT OUTER JOIN JOURNAL_ENTRY J
        ON H.ID = J.PURCHASE_ID AND J.PURCHASE_TYPE = ? 
        WHERE H.HOSPITAL_ID = ?
        AND H.ISSUE_DATE BETWEEN ? AND ?
        AND (
          ('ALL' = ? AND 1 = 1)
          OR ('미분류' = ? AND H.DEBIT_ACCOUNT IS NULL)
          OR ('확인필요' = ? AND H.DEBIT_ACCOUNT = '확인필요')
          OR ('분류완료' = ? AND H.DEBIT_ACCOUNT <> '확인필요' AND H.DEBIT_ACCOUNT IS NOT NULL)
        )
    """

    const val UNION_QUERY = """
        
    """

    const val COUNT_QUERY = """
        SELECT COUNT(*)
        FROM ( 
          %s 
        ) AS U
    """
}