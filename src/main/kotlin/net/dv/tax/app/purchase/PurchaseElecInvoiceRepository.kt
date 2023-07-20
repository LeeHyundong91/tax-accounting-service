package net.dv.tax.app.purchase

import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity
import net.dv.tax.app.purchase.suppoort.PurchaseElecInvoiceSupport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/*PurchaseElecInvoice*/
interface PurchaseElecInvoiceRepository : JpaRepository<PurchaseElecInvoiceEntity?, Int>,
    JpaSpecificationExecutor<PurchaseElecInvoiceEntity?>, PurchaseElecInvoiceSupport {
}