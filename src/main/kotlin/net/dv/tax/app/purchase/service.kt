package net.dv.tax.app.purchase

import net.dv.tax.app.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime


@Service
class PurchaseManagementService(
    private val creditCardRepository: PurchaseCreditCardRepository,
    private val cashReceiptRepository: PurchaseCashReceiptRepository,
    private val journalEntryRepository: PurchaseJournalEntryRepository,
    private val journalEntryHistoryRepository: PurchaseJournalEntryHistoryRepository,
): PurchaseQueryCommand, JournalEntryCommand {
    private val now: LocalDateTime  get() = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()

    /**
     * PurchaseQueryCommand member methods...
     */

    /**
     * 신용카드 매입 목록 조회
     */
    override fun creditCard(hospitalId: String, query: PurchaseQueryDto): PurchaseCreditCardListDto {
        val books = creditCardRepository.purchaseBooks(hospitalId, query)
        val totalCount = creditCardRepository.purchaseCreditCardListCnt(hospitalId, query)
        val summary = creditCardRepository.purchaseCreditCardTotal(hospitalId, query)

        return PurchaseCreditCardListDto(
            listPurchaseCreditCard = books,
            purchaseCreditCardTotal = summary,
            totalCount = totalCount
        )
    }

    override fun purchaseBooks(type: PurchaseType,
                               hospitalId: String,
                               query: PurchaseQueryDto): PurchaseBooks<*> {
        return cashReceiptBooks(hospitalId, query)
    }

    private fun cashReceiptBooks(hospitalId: String, query: PurchaseQueryDto): PurchaseBooks<CashReceiptBook> {
        val list = cashReceiptRepository.purchaseBooks(hospitalId, query)
        val summary = cashReceiptRepository.summary(hospitalId, query)
        val count = cashReceiptRepository.purchaseCashReceiptListCnt(hospitalId, query)

        return PurchaseBooks(list = list, summary = summary, total = count)
    }

    /**
     * Journal Entry member methods...
     */
    override fun get(purchase: PurchaseBook): JournalEntry {
        return journalEntryRepository.find(purchase)
            ?.let {
                JournalEntryReqDto(
                    "Sample!!!",
                    it.requestNote!!,
                    it.checkExpense!!,
                    it.accountingItem,
                    it.status?.name,
                    "",
                    it.committer,
                )
            } ?: throw RuntimeException("no journal entry")
    }

    override fun request(purchase: PurchaseBook, je: JournalEntry): JournalEntry {
        val entity = journalEntryRepository.find(purchase) ?: JournalEntryEntity(
            purchaseId = purchase.id,
            purchaseType = purchase.type.code,
            status = JournalEntryEntity.Status.REQUESTED
        )

        val e = entity
            .takeIf { it.status == JournalEntryEntity.Status.REQUESTED }
            ?.apply {
                requestNote = je.note
                checkExpense = je.checkExpense
                requestedAt = now
            } ?: throw RuntimeException("already been processed")

        val entry =  journalEntryRepository.save(e).let {
            JournalEntryReqDto(
                note = it.requestNote!!,
                checkExpense =  it.checkExpense!!,
                status = it.status?.name,
            )
        }

        val history = JournalEntryHistoryEntity(
            purchaseId = purchase.id,
            purchaseType = purchase.type.code,
            note = je.note,
            checkExpense = je.checkExpense,
            writer = je.requester,
            writtenAt = now
        )
        journalEntryHistoryRepository.save(history)

        return entry
    }

    override fun confirm(purchase: PurchaseBook, je: JournalEntry): JournalEntry {
        val entity = journalEntryRepository.find(purchase) ?: throw RuntimeException("no journal entity")

        entity.apply {
            commitNote = je.note
            accountingItem = je.accountingItem
            status = JournalEntryEntity.Status.CONFIRMED
            committer = je.committer
            committedAt = now
        }

        val history = JournalEntryHistoryEntity(
            purchaseId = purchase.id,
            purchaseType = purchase.type.code,
            note = je.note,
            accountingItem = je.accountingItem,
            writer = je.committer,
            checkExpense = null,
            writtenAt = now
        )

        val entry = journalEntryRepository.save(entity).let {
            JournalEntryReqDto(
                merchant = "Sample!!",
                note = it.requestNote!!,
                checkExpense =  it.checkExpense!!,
                status = it.status?.name,
                accountingItem = it.accountingItem!!,
                committer = it.committer
            )
        }
        journalEntryHistoryRepository.save(history)

        return entry
    }

    override fun history(purchase: PurchaseBookDto): List<JournalEntryHistoryDto> {
        return journalEntryHistoryRepository.find(purchase)
            .map{
                JournalEntryHistoryDto(
                    it.writer!!,
                    it.accountingItem,
                    it.checkExpense,
                    it.note!!,
                    it.writtenAt
                )
            }
    }
}