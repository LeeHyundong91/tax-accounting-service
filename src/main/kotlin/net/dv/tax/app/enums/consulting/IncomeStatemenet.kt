package net.dv.tax.app.enums.consulting

enum class IncomeStatementCategory(
    var code: String,
    var value: String,
) {
    SALES_REVENUE("SALES_REVENUE", "매출액"),
    COST_OF_GOODS_SOLD("COST_OF_GOODS_SOLD", "매출원가"),
    GROSS_PROFIT("GROSS_PROFIT", "매출총이익"),
    SGA_EXPENSES("SGA_EXPENSES", "판매비와 관리비"),
    OPERATING_PROFIT("OPERATING_PROFIT", "영업이익"),
    NON_OPERATING_REVENUE("NON_OPERATING_REVENUE", "영업외 수익"),
    NON_OPERATING_EXPENSE("NON_OPERATING_EXPENSE", "영업외 비용"),
    PROFIT_BEFORE_INCOME_TAX("PROFIT_BEFORE_INCOME_TAX", "소득세 차감전 이익"),
    INCOME_TAX("INCOME_TAX", "소득세 등"),
    NET_PROFIT("NET_PROFIT", "당기순이익");
}

enum class IncomeStatementItem(
    var value: String,
) {
    SALES_REVENUE_TOTAL("매출액"),
    COST_OF_GOODS_SOLD_TOTAL("매출원가"),
    GROSS_PROFIT_TOTAL("매출총이익"),
    SGA_EXPENSES_TOTAL("판매비와 관리비"),
    OPERATING_PROFIT_TOTAL("영업이익"),
    NON_OPERATING_REVENUE_TOTAL("영업외 수익"),
    NON_OPERATING_EXPENSE_TOTAL("영업외 비용"),
    PROFIT_BEFORE_INCOME_TAX_TOTAL("소득세 차감전 이익"),
    INCOME_TAX_TOTAL("소득세 등"),
    NET_PROFIT_TOTAL("당기순이익");
}