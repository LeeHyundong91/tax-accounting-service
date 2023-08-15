package net.dv.tax.app.purchase

import net.dv.tax.app.dto.purchase.PurchaseCashReceiptTotal
import net.dv.tax.app.dto.purchase.PurchaseCreditCardTotal
import net.dv.tax.app.dto.purchase.PurchaseElecInvoiceTotal
import net.dv.tax.domain.purchase.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


/** 신용카드 매입 자료 Repository */
interface PurchaseCreditCardRepository: JpaRepository<PurchaseCreditCardEntity?, Int>,
                                        JpaSpecificationExecutor<PurchaseCreditCardEntity?>,
                                        PurchaseCreditCardQuery

interface PurchaseCreditCardQuery {
    fun purchaseBooks(hospitalId: String, query: PurchaseQueryDto): List<CreditCardBook>
    fun summary(hospitalId: String, query: PurchaseQueryDto): BookSummary
    fun journalEntryProcessing(hospitalId: String, pageable: Pageable): Page<CreditCardBook>

    fun purchaseCreditCardList(hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseCreditCardEntity>
    fun purchaseCreditCardListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long
    fun purchaseCreditCardTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCreditCardTotal
}

/** 현금영수증 매입 자료 Repository */
interface PurchaseCashReceiptRepository: JpaRepository<PurchaseCashReceiptEntity?, Int>,
                                         JpaSpecificationExecutor<PurchaseCashReceiptEntity?>,
                                         PurchaseCashReceiptQuery

interface PurchaseCashReceiptQuery {
    fun purchaseBooks(hospitalId: String, query: PurchaseQueryDto): List<CashReceiptBook>
    fun summary(hospitalId: String, query: PurchaseQueryDto): BookSummary
    fun journalEntryProcessing(hospitalId: String, pageable: Pageable): Page<out JournalEntryStatus>

    fun purchaseCashReceiptList(hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseCashReceiptEntity>
    fun purchaseCashReceiptListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long
    fun purchaseCashReceiptTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCashReceiptTotal
}

/** 전자 (세금) 계산서 매입 자료 Repository */
interface PurchaseElecInvoiceRepository : JpaRepository<PurchaseElecInvoiceEntity?, Int>,
                                          JpaSpecificationExecutor<PurchaseElecInvoiceEntity?>,
                                          PurchaseEInvoiceQuery

interface PurchaseEInvoiceQuery {
    fun purchaseBooks(hospitalId: String, bookType: String, query: PurchaseQueryDto): List<ETaxInvoiceBook>
    fun summary(hospitalId: String, bookType: String, query: PurchaseQueryDto): BookSummary
    fun bookCount(hospitalId: String, bookType: String, query: PurchaseQueryDto): Long
    fun journalEntryProcessing(hospitalId: String, bookType: String, pageable: Pageable): Page<out JournalEntryStatus>

    fun purchaseElecInvoiceList(hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseElecInvoiceEntity>
    fun purchaseElecInvoiceListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long
    fun purchaseElecInvoiceTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseElecInvoiceTotal
}

@Repository
interface PurchaseJournalEntryRepository:
    JpaRepository<JournalEntryEntity, Long>,
    PurchaseJournalEntryQuery

interface PurchaseJournalEntryQuery {
    fun search(): List<PurchaseData>
    fun find(purchase: PurchaseBook): JournalEntryEntity?
}

@Repository
interface PurchaseJournalEntryHistoryRepository:
    JpaRepository<JournalEntryHistoryEntity, Long>,
    PurchaseJournalEntryHistoryQuery

interface PurchaseJournalEntryHistoryQuery {
    fun find(purchase: PurchaseBook): List<JournalEntryHistoryEntity>
}

class PurchaseData