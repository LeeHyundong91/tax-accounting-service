package net.dv.tax.app.purchase.suppoort

import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity
import net.dv.tax.app.dto.purchase.PurchaseElecInvoiceTotal
import net.dv.tax.app.purchase.PurchaseQueryDto

interface PurchaseElecInvoiceSupport {

    fun purchaseElecInvoiceList(hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseElecInvoiceEntity>

    fun purchaseElecInvoiceListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long

    fun purchaseElecInvoiceTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseElecInvoiceTotal
}