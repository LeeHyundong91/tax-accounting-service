package net.dv.tax.app.purchase

import net.dv.tax.app.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.app.dto.purchase.PurchaseQueryDto


interface PurchaseQueryCommand {
    fun creditCard(hospitalId: String, query: PurchaseQueryDto): PurchaseCreditCardListDto
}