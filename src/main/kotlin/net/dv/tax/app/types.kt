package net.dv.tax.app


enum class Deduction(val label: String) {
    TAX_DEDUCTION("세액 공제"),
    TAX_NON_DEDUCTION("세액 불공제"),
}

data class AccountingItem(
    val code: String,
    val label: String,
)