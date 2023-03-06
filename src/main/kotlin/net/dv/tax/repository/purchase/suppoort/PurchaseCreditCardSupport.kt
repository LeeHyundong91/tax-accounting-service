package net.dv.tax.repository.purchase.suppoort


import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.dto.purchase.PurchaseCreditCardTotal
import net.dv.tax.dto.purchase.PurchaseQueryDto

interface PurchaseCreditCardSupport {

    fun purchaseCreditCardList( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): List<PurchaseCreditCardEntity>

    fun purchaseCreditCardListCnt( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): Long

    fun purchaseCreditCardTotal( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCreditCardTotal
}