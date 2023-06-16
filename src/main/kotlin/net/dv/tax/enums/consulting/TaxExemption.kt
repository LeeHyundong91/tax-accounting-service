package net.dv.tax.enums.consulting

enum class TaxExemptionCategory(
    var code: String,
    var value: String,
) {
    CARD("CARD","카드"),
    CASH_RECEIPT("CASH_RECEIPT","현금영수증"),
    CASH("CASH","현금");
}


enum class TaxExemptionItem(
    var code: String,
    var value: String,
) {
    SMALL_TOTAL("SMALL_TOTAL","소계"),

    TAX_CARD("TAX_CARD","과세카드"),
    TAX_FREE_CARD("TAX_FREE_CARD","면세카드"),

    TAX_CASH_RECEIPT("TAX_CASH_RECEIPT","과세현영"),
    TAX_FREE_CASH_RECEIPT("TAX_FREE_CASH_RECEIPT","면세현영"),

    TAX_CASH("TAX_CASH","과세현금"),
    TAX_FREE_CASH("TAX_FREE_CASH","면세현금"),
    TAX_FREE_COMPLEX("TAX_FREE_COMPLEX","면세공단"),

}


