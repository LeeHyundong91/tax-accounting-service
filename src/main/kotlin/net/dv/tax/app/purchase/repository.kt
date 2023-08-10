package net.dv.tax.app.purchase

import net.dv.tax.app.dto.purchase.PurchaseCreditCardDto
import net.dv.tax.app.dto.purchase.PurchaseCreditCardTotal
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.domain.purchase.JournalEntryEntity
import net.dv.tax.domain.purchase.JournalEntryHistoryEntity
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


/**
 *  신용카드 매입 자료 Repository
 */
interface PurchaseCreditCardRepository: JpaRepository<PurchaseCreditCardEntity?, Int>, JpaSpecificationExecutor<PurchaseCreditCardEntity?>, PurchaseCreditCardQuery

interface PurchaseCreditCardQuery {
    fun purchaseBooks(hospitalId: String, query: PurchaseQueryDto): List<PurchaseCreditCardDto>
    fun purchaseCreditCardList( hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseCreditCardEntity>
    fun purchaseCreditCardListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long
    fun purchaseCreditCardTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCreditCardTotal
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