package net.dv.tax.repository.purchase

import net.dv.tax.domain.purchase.PurchaseCashReceiptEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/*PurchaseCashReceipt*/

interface PurchaseCashReceiptRepository: JpaRepository<PurchaseCashReceiptEntity?, Int>,
    JpaSpecificationExecutor<PurchaseCashReceiptEntity?> {
}