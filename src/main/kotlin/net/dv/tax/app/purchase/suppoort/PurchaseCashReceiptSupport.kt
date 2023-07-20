package net.dv.tax.app.purchase.suppoort


import net.dv.tax.domain.purchase.PurchaseCashReceiptEntity
import net.dv.tax.app.dto.purchase.PurchaseCashReceiptTotal
import net.dv.tax.app.dto.purchase.PurchaseQueryDto

interface PurchaseCashReceiptSupport {

    fun purchaseCashReceiptList( hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseCashReceiptEntity>

    fun purchaseCashReceiptListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long

    fun purchaseCashReceiptTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCashReceiptTotal
}