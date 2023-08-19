package net.dv.tax.app

import java.time.LocalDate


enum class Deduction(val label: String) {
    TAX_DEDUCTION("세액 공제"),
    TAX_NON_DEDUCTION("세액 불공제"),
}

enum class AccountingItemCategory(val label: String, val keyword: String) {
    LABOR_COST("인건비", "인건"),
    PAYMENT_FEE("지급수수료", "수수료"),
    VEHICLE_MAINTENANCE("차량유지비", "차량"),
    CONSUMABLES("소모품비", "소모품"),
    ADVERTISING("광고선전비", "광고"),
    BENEFITS("복리후생비", "복리후생"),
    ENTERTAINMENT("접대비", "접대"),
    NONE_CATEGORIZED("미분류", "미분류"),
    REQUIRE_CHECK("확인필요", "확인필요"),
    OTHERS("기타경비", "OTHER");

    companion object {
        fun category(keyword: String?): AccountingItemCategory {
            return keyword
                ?.takeIf { it.isNotEmpty() }
                ?.let {key ->
                    values().firstOrNull { key.contains(it.keyword) } ?: OTHERS
                }?: NONE_CATEGORIZED
        }
    }
}

data class AccountingItem(
    val code: String,
    val label: String,
)

data class Period(
    val begin: LocalDate? = null,
    val end: LocalDate? = null,
)