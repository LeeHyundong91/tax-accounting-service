package net.dv.tax.enums.consulting

enum class ExpectedIncomeCategory(
    var code: String,
    var value: String,
) {
    ANNUALIZED_AMOUNT("ANNUALIZED_AMOUNT", "연환산금액"),
    EXPECTED_ADDITIONAL("EXPECTED_ADDITIONAL", "추가손익예상"),
    TARGET_AMOUNT("TARGET_AMOUNT", "목표금액"),
}


enum class ExpectedIncomeItem(
    var value: String,
) {
    ANNUAL_SALES("연매출"),
    ANNUAL_COST("연비용"),
    ANNUAL_INCOME("연소득"),
    INCOME_RATE("소득률"),

    ADDITIONAL_EXPECTED_SALES("추가 예상매출"),
    ADDITIONAL_ESTIMATED_COSTS("추가 예상비용"),
    EXTRA_EXPECTED_INCOME("추가 예상소득"),

    TARGET_ANNUAL_SALES("연매출"),
    TARGET_ANNUAL_COST("연비용"),
    TARGET_ANNUAL_INCOME("연소득"),
    TARGET_INCOME_RATE("소득률"),
}
