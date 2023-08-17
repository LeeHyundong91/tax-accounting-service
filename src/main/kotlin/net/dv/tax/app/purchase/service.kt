package net.dv.tax.app.purchase

import com.amazonaws.services.kms.model.UnsupportedOperationException
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import net.dv.tax.domain.purchase.PurchaseHandwrittenEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime


@Service
class PurchaseManagementService(
    private val creditCardRepository: PurchaseCreditCardRepository,
    private val cashReceiptRepository: PurchaseCashReceiptRepository,
    private val invoiceRepository: PurchaseElecInvoiceRepository,
    private val handwrittenRepository: PurchaseHandwrittenRepository,
    private val journalEntryRepository: PurchaseJournalEntryRepository,
    private val journalEntryHistoryRepository: PurchaseJournalEntryHistoryRepository,
): PurchaseQueryCommand, PurchaseOperationCommand, JournalEntryCommand {
    private val now: LocalDateTime  get() = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()

    /**
     * PurchaseQueryCommand member methods...
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T> purchaseBooks(type: PurchaseType,
                                   hospitalId: String,
                                   query: PurchaseQueryDto): PurchaseBooks<T> {
        return when(type) {
            PurchaseType.CREDIT_CARD -> creditCard(hospitalId, query)
            PurchaseType.CASH_RECEIPT -> cashReceiptBooks(hospitalId, query)
            PurchaseType.TAX_INVOICE,
            PurchaseType.INVOICE -> eInvoiceBooks(hospitalId, type, query)
            PurchaseType.HANDWRITTEN_INVOICE -> empty()
            PurchaseType.BASIC_RECEIPT -> empty()
        } as PurchaseBooks<T>
    }

    /** 신용카드 매입 목록 조회 */
    private fun creditCard(hospitalId: String, query: PurchaseQueryDto): PurchaseBooks<CreditCardBook> {
        val list = creditCardRepository.purchaseBooks(hospitalId, query)
        val summary = creditCardRepository.summary(hospitalId, query)
        val count = creditCardRepository.purchaseCreditCardListCnt(hospitalId, query)

        return PurchaseBooks(list, summary, count)
    }

    private fun cashReceiptBooks(hospitalId: String, query: PurchaseQueryDto): PurchaseBooks<CashReceiptBook> {
        val list = cashReceiptRepository.purchaseBooks(hospitalId, query)
        val summary = cashReceiptRepository.summary(hospitalId, query)
        val count = cashReceiptRepository.purchaseCashReceiptListCnt(hospitalId, query)

        return PurchaseBooks(list = list, summary = summary, total = count)
    }

    private fun eInvoiceBooks(hospitalId: String, bookType: PurchaseType, query: PurchaseQueryDto): PurchaseBooks<ETaxInvoiceBook> {
        val list = invoiceRepository.purchaseBooks(hospitalId, bookType.code, query)
        val summary = invoiceRepository.summary(hospitalId, bookType.code, query)
        val count = invoiceRepository.bookCount(hospitalId, bookType.code, query)
        return PurchaseBooks(list, summary, count)
    }

    override fun handwrittenBooks(type: PurchaseType, hospitalId: String, year: Int): List<HandwrittenBook> {
        return handwrittenRepository.find {
            this.hospitalId = hospitalId
            this.year = year
            this.type = type.code
        }.map {entity ->
            object: HandwrittenBook {
                val id: Long? = entity.id
                val hospitalId: String = entity.hospitalId!!
                val type: String? = entity.type?.name
                override val issueDate: String? = entity.issueDate
                override val supplier: String? = entity.supplier
                override val itemName: String? = entity.itemName
                override val supplyPrice: Long? = entity.supplyPrice
                override val debitAccount: String? = entity.debitAccount
                override val taxAmount: Long? = entity.taxAmount
                override val writer: String? = entity.writer
                val createdAt: LocalDateTime = entity.createdAt
            }
        }
    }

    /**
     * PurchaseOperationCommand member methods...
     */
    override fun writeBooks(hospitalId: String,
                            type: PurchaseType,
                            books: List<HandwrittenBook>): BookSummary {
        val entities = books.map {
            PurchaseHandwrittenEntity(
                type = PurchaseHandwrittenEntity.Type[type.code],
                hospitalId = hospitalId,
                issueDate = it.issueDate,
                supplier = it.supplier,
                itemName = it.itemName,
                supplyPrice = it.supplyPrice,
                debitAccount = it.debitAccount,
                taxAmount = it.taxAmount,
                writer = it.writer,
                createdAt = now
            )
        }

        handwrittenRepository.saveAll(entities)

        return object: BookSummary {
            val hospitalId: String = hospitalId
            val type: String = type.label
            val books: Int = entities.size
        }
    }

    /** Journal Entry member methods... */

    override fun expenseByHospital(hospitalId: String, pageable: Pageable): Page<JournalEntry> {
        return journalEntryRepository.expense(hospitalId, pageable)
    }

    override fun get(purchase: PurchaseBookIdentity): JournalEntry {
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
                    it.requestedAt,
                    it.committedAt
                )
            } ?: throw RuntimeException("no journal entry")
    }

    override fun request(purchase: PurchaseBookIdentity, je: JournalEntry): JournalEntry {
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

    override fun confirm(purchase: PurchaseBookIdentity, je: JournalEntry): JournalEntry {
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

    // TODO ( Repository ID 및 PurchaseBook type 개선 필요)
    override fun history(purchase: PurchaseBookDto): PurchaseBookSummary {
        val (id, merchant, date, item, amount) = when (purchase.type) {
            PurchaseType.CREDIT_CARD -> {
                creditCardRepository.findById(purchase.id.toInt()).get().let {
                    listOf(it.id, it.franchiseeName, it.billingDate, it.itemName, it.totalAmount)
                }
            }
            PurchaseType.CASH_RECEIPT -> {
                cashReceiptRepository.findById(purchase.id.toInt()).get().let {
                    listOf(it.id, it.franchiseeName, it.billingDate, it.itemName, it.totalAmount)
                }
            }
            PurchaseType.INVOICE, PurchaseType.TAX_INVOICE -> {
                invoiceRepository.findById(purchase.id.toInt()).get().let {
                    listOf(it.id, it.franchiseeName, it.sendDate, it.itemName, it.totalAmount)
                }
            }
            PurchaseType.BASIC_RECEIPT, PurchaseType.HANDWRITTEN_INVOICE -> {
                handwrittenRepository.findById(purchase.id).get().let {
                    listOf(it.id, it.supplier, it.issueDate, it.itemName, it.taxAmount)
                }
            }
        }

        val history = journalEntryHistoryRepository.find(purchase)
            .map{
                JournalEntryHistoryDto(
                    it.writer!!,
                    it.accountingItem,
                    it.checkExpense,
                    it.note!!,
                    it.writtenAt
                )
            }

        return object: PurchaseBookSummary {
            override val id: Long = id as Long
            override val type: PurchaseType = purchase.type
            override val merchant: String? = merchant as String?
            override val item: String? = item as String?
            override val transactionDate: String = date as String
            override val amount: Long = amount as Long

            val history: List<JournalEntryHistoryDto> = history
        }
    }

    override fun processingState(type: PurchaseType, hospitalId: String, pageable: Pageable): Page<out JournalEntryStatus> {
        return when(type) {
            PurchaseType.CREDIT_CARD -> creditCardRepository.journalEntryProcessing(hospitalId, pageable)
            PurchaseType.CASH_RECEIPT -> cashReceiptRepository.journalEntryProcessing(hospitalId, pageable)
            PurchaseType.TAX_INVOICE,
            PurchaseType.INVOICE -> invoiceRepository.journalEntryProcessing(hospitalId, type.code, pageable)
            else -> empty()
        }
    }

    private fun empty(): Nothing = throw UnsupportedOperationException("empty")
}