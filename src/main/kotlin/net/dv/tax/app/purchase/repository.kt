package net.dv.tax.app.purchase

import net.dv.tax.app.dto.purchase.PurchaseCashReceiptTotal
import net.dv.tax.app.dto.purchase.PurchaseCreditCardDto
import net.dv.tax.app.dto.purchase.PurchaseCreditCardTotal
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import net.dv.tax.domain.purchase.PurchaseCashReceiptEntity
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


/** 신용카드 매입 자료 Repository */
interface PurchaseCreditCardRepository: JpaRepository<PurchaseCreditCardEntity?, Int>, JpaSpecificationExecutor<PurchaseCreditCardEntity?>, PurchaseCreditCardQuery

interface PurchaseCreditCardQuery {
    fun purchaseBooks(hospitalId: String, query: PurchaseQueryDto): List<PurchaseCreditCardDto>
    fun purchaseCreditCardList(hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseCreditCardEntity>
    fun purchaseCreditCardListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long
    fun purchaseCreditCardTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCreditCardTotal
}

/** 현금영수증 매입 자료 Repository */
interface PurchaseCashReceiptRepository: JpaRepository<PurchaseCashReceiptEntity?, Int>, JpaSpecificationExecutor<PurchaseCashReceiptEntity?>, PurchaseCashReceiptQuery

interface PurchaseCashReceiptQuery {
    fun purchaseBooks(hospitalId: String, query: PurchaseQueryDto): List<CashReceiptBook>
    fun summary(hospitalId: String, query: PurchaseQueryDto): BookSummary
    fun purchaseCashReceiptList(hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseCashReceiptEntity>
    fun purchaseCashReceiptListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long
    fun purchaseCashReceiptTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCashReceiptTotal
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