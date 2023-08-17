package net.dv.tax.infra.orm.purchase

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.JournalEntry
import net.dv.tax.app.purchase.PurchaseBookIdentity
import net.dv.tax.app.purchase.PurchaseJournalEntryHistoryQuery
import net.dv.tax.app.purchase.PurchaseJournalEntryQuery
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import net.dv.tax.domain.purchase.QJournalEntryEntity
import net.dv.tax.domain.purchase.QJournalEntryHistoryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
class PurchaseJournalEntryRepositoryImpl(
    private val factory: JPAQueryFactory,
    private val jdbcTemplate: JdbcTemplate,
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

    override fun expense(hospitalId: String, pageable: Pageable): Page<JournalEntry> {
        val list = jdbcTemplate.query(JournalEntryQueries.E_INVOICE_BOOKS) { rs, _ ->
            JournalEntryDto(
                id = rs.getLong("PURCHASE_ID"),
                type = PurchaseType[rs.getString("PURCHASE_TYPE")],
                status = rs.getString("STATUS"),
                merchant = "",
                note = rs.getString("REQ_NOTE"),
                checkExpense = rs.getBoolean("REQ_EXPENSE"),
            )
        } as List<JournalEntry>

        val count = jdbcTemplate.queryForObject(EXPENSE_COUNT_QUERY) { rs, _ ->
            rs.getLong(1)
        }

        return PageImpl(list, pageable, count ?: 0)
    }

    private val EXPENSE_COUNT_QUERY = """
        SELECT COUNT(*)
          FROM JOURNAL_ENTRY J;
    """.trimIndent()
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
       SELECT C.FRANCHISEE_NAME 
             ,C.BILLING_DATE
             ,C.TOTAL_AMOUNT
             ,C.DEBTOR_ACCOUNT
             ,J.ACCOUNTING_ITEM
             ,J.PURCHASE_TYPE
       FROM PURCHASE_CREDIT_CARD C 
       LEFT OUTER JOIN JOURNAL_ENTRY J
       ON C.ID = J.PURCHASE_ID AND J.PURCHASE_TYPE = ?
       WHERE C.HOSPITAL_ID = ?
       AND C.BILLING_DATE BETWEEN ? AND ?
       AND (
         ('ALL' = ? AND 1 = 1)
         OR ('미분류' = ? AND C.DEBTOR_ACCOUNT IS NULL)
         OR ('확인필요' = ? AND C.DEBTOR_ACCOUNT = '확인필요')
         OR ('분류완료' = ? AND C.DEBTOR_ACCOUNT <> '확인필요' AND C.DEBTOR_ACCOUNT IS NOT NULL)
       )        
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
}