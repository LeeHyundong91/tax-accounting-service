package net.dv.tax.app.enums.purchase

import net.dv.tax.domain.Code


enum class PurchaseType(override val code: String,
                         override val label: String): Code {
    CREDIT_CARD("CC", "신용카드"),
    CASH_RECEIPT("CR", "현금영수증"),
    TAX_INVOICE("TI", "전자세금계산서"),
    INVOICE("II", "전자계산서"),
    HANDWRITTEN_INVOICE("HI", "수기세금계산서"),
    BASIC_RECEIPT("BR", "간이영수증"),;

    companion object {
        fun of(type: String): PurchaseType = values().first { it.code == type }
    }
}