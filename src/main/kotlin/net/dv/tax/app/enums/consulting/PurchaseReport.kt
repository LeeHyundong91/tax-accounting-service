package net.dv.tax.app.enums.consulting

enum class PurchaseCategory(
    var code: String,
    var label: String,
) {
    SALES_OF_COST("SALES_OF_COST", "매출원가"),
    SGA_EXPENSE("SGA_EXPENSE", "판관비"),
    NON_OPERATING_EXPENSES("NON_OPERATING_EXPENSES", "영업외비용"),
    NON_OPERATING_INCOME("NON_OPERATING_INCOME", "영업외수익"),
    DEPRECIATION("DEPRECIATION", "감가비");

    companion object {
        operator fun get(key: String) = values().first { it.code == key.uppercase() || it.name == key.uppercase() }
    }
}

enum class PurchaseTitleItem(
    var value: String,
) {
    BASIC_INVENTORY("기초재고"),
    CURRENT_PURCHASE("당기매입"),
    ENDING_STOCK("기말재고"),
    COST_OF_SALES("매출원가"),

    SGA_EXPENSE_TOTAL("판관비합계"),

    INTEREST_EXPENSE("이자비용"),
    LEASE_INTEREST("리스이자"),
    DONATION_EXPENSE("기부금"),
    SETTLEMENT_AMOUNT("합의금"),
    MISCELLANEOUS_LOSS("잡손실"),
    LOSS_ON_DISPOSAL_OF_FIXED_ASSETS("유형자산 처분손실"),
    ADDITIONAL_NON_OPERATING_EXPENSES("영업외 비용 추가항목"),
    NON_OPERATING_EXPENSES_TOTAL("영업외비용 소계"),


    SUPPORT_FUNDS("지원금"),
    MISCELLANEOUS_PROFIT("잡이익"),
    INCOME_SETTLEMENT_AMOUNT("합의금"),
    GAIN_ON_DISPOSAL_OF_FIXED_ASSETS("유형자산 처분이익"),
    ADDITIONAL_NON_OPERATING_REVENUES("영업외 수익 추가항목"),
    NON_OPERATING_INCOME_TOTAL("영업외수익 소계"),

    NON_OPERATING_TOTAL("영업외 소계"),

    DEPRECIABLE_AMOUNT("상각대상금액"),
    DEPRECIATION_LIMIT("감가비 한도액"),
    DEPRECIATION_EXPENSE("감가비 계상액"),
    VEHICLE_LIMIT_EXCESS_AMOUNT("차량 한도초과액"),
    RECOGNIZED_DEPRECIATION_AMOUNT("감가비 인정액");


}
