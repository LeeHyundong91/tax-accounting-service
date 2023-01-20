package net.dv.tax.repository.purchase

import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/*PurchaseElecInvoice*/
interface PurchaseElecInvoiceRepository : JpaRepository<PurchaseElecInvoiceEntity?, Int>,
    JpaSpecificationExecutor<PurchaseElecInvoiceEntity?> {
}