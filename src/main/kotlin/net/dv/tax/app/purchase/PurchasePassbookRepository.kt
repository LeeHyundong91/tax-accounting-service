package net.dv.tax.app.purchase

import net.dv.tax.domain.purchase.PurchasePassbookEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/*PurchaseCashReceipt*/

interface PurchasePassbookRepository : JpaRepository<PurchasePassbookEntity, Long>,
    JpaSpecificationExecutor<PurchasePassbookEntity> {

    fun findAllByHospitalIdAndTransactionDateStartingWith(
        hospitalId: String,
        transactionDate: String,
        pageable: Pageable,
    ): List<PurchasePassbookEntity>?


}