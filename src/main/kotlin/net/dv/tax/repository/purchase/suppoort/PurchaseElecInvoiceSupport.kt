package net.dv.tax.repository.purchase.suppoort

import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity
import net.dv.tax.dto.purchase.PurchaseElecInvoiceTotal
import net.dv.tax.dto.purchase.PurchaseQueryDto

interface PurchaseElecInvoiceSupport {

    fun purchaseElecInvoiceList( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): List<PurchaseElecInvoiceEntity>

    fun purchaseElecInvoiceListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long

    fun purchaseElecInvoiceTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseElecInvoiceTotal
}