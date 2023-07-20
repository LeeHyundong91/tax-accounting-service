package net.dv.tax.app.purchase.suppoort


import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.app.dto.purchase.PurchaseCreditCardTotal
import net.dv.tax.app.dto.purchase.PurchaseQueryDto

interface PurchaseCreditCardSupport {

    fun purchaseCreditCardList( hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseCreditCardEntity>

    fun purchaseCreditCardListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long

    fun purchaseCreditCardTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCreditCardTotal
}