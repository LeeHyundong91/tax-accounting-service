package net.dv.tax.enums.consulting

enum class EstimatedTaxCategory(
    var code: String,
    var value: String,
) {
    INCOME_TAX("INCOME_TAX", "소득세"),
    LOCAL_INCOME_TAX("LOCAL_INCOME_TAX", "지방 소득세"),
    SPECIAL_TAX("SPECIAL_TAX", "농어촌특별세"),
    PRE_PAID_TAX_AMOUNT("PRE_PAID_TAX_AMOUNT", "기납부세액"),
}


enum class EstimatedTaxItem(
    var value: String,
) {
    OTHER_INCOME("타소득"),
    TOTAL_INCOME("종합소득금액"),
    INCOME_DEDUCTIONS("소득공제계"),
    TAXABLE_BASE("과세표준"),
    TAX_RATE("세율"),
    CALCULATED_TAX("산출세액"),
    TAX_REDUCTION("세액감면"),
    TAX_DEDUCTION("세액공제"),
    COMPREHENSIVE_TAX("결정세엑-종합과세"),
    SEPARATE_TAXATION("결정세엑-분리과세"),
    TOTAL_DETERMINED_TAX("결정세엑-합계"),
    ADDITIONAL_TAX("가산세"),
    ADDITIONAL_PAYMENT_TAX("추가납부세액"),
    TOTAL_INCOME_TAX("소득세 합계"),
    TOTAL_LOCAL_INCOME_TAX("지방소득세 합계"),

    PREPAID_TAX("기납부세액"),
    TOTAL_TAX_PAYABLE("납부 할 총세액"),
    SPECIAL_DEDUCTION_TAX_DEDUCTION("납부특례세액-차감"),
    SPECIAL_DEDUCTION_TAX_ADDITIONAL("납부특례세액-가산"),
    INSTALMENT_TAX("분납세액"),

    INTERIM_PREPAID_TAX("중간예납세액"),
    ANTICIPATED_DECLARATION_TAX("토지 등 매매차익 예정신고납부세액"),
    ANTICIPATED_NOTIFICATION_TAX("토지 등 매매차익 예정고지세액"),
    ADDITIONAL_TAX_ASSESSED("수시부과세액"),
    INTEREST_INCOME("이자소득"),
    DIVIDEND_INCOME("배당소득"),
    BUSINESS_INCOME("사업소득"),
    LABOR_INCOME("근로소득"),
    PENSION_INCOME("연금소득"),
    ETC_INCOME("기타소득");


}