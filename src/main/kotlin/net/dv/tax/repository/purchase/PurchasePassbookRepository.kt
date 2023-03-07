package net.dv.tax.repository.purchase

import net.dv.tax.domain.purchase.PurchasePassbookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/*PurchaseCashReceipt*/

interface PurchasePassbookRepository: JpaRepository<PurchasePassbookEntity, Long>,
    JpaSpecificationExecutor<PurchasePassbookEntity> {
}