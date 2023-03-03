package net.dv.tax.repository.purchase.suppoort


import net.dv.tax.domain.purchase.PurchaseCashReceiptEntity
import net.dv.tax.dto.purchase.PurchaseCashReceiptTotal
import net.dv.tax.dto.purchase.PurchaseQueryDto

interface PurchaseCashReceiptSupport {

    fun purchaseCashReceiptList( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): List<PurchaseCashReceiptEntity>

    fun purchaseCashReceiptListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long

    fun purchaseCashReceiptTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCashReceiptTotal
}