package net.dv.tax.dto

import com.fasterxml.jackson.annotation.JsonProperty
import net.dv.tax.domain.purchase.PurchaseCashReceiptEntity
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity


data class QueueDto(
    @JsonProperty("menu")
    var menu: String? = null,

    @JsonProperty("creditCard")
    var creditCard: MutableList<PurchaseCreditCardEntity>? = null,

    @JsonProperty("elecInvoice")
    var elecInvoice: MutableList<PurchaseElecInvoiceEntity>? = null,

    @JsonProperty("cashReceipt")
    var cashReceipt: MutableList<PurchaseCashReceiptEntity>? = null,

    )

