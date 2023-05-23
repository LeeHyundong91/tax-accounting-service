package net.dv.tax.enums.consulting

enum class TaxExemptionItem(
    var value: String,
) {
    SMALL_TOTAL("소계"),
    TAX("과세"),
    TAX_FREE("면세"),
    COMPLEX("공단"),
    CARD("카드"),
    CASH_RECEIPT("현금영수증"),
    CASH("현금");
}
