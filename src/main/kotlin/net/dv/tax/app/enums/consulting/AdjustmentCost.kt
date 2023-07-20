package net.dv.tax.app.enums.consulting

enum class AdjustmentCostCategory(
    var code: String,
    var value: String,
) {
    ADDITIONAL_EXPENSES("ADDITIONAL_EXPENSES", "추가비용"),
    INCOME_ADJUSTMENT("INCOME_ADJUSTMENT", "소득조정"),
}


enum class AdjustmentCostItem(
    var value: String,
) {
    TRIPOD_AMOUNT("삼각대상금액"),
    RETIREMENT_PENSION("퇴직연금"),
    ADDITIONAL_INTEREST_EXPENSE("이자비용"),
    HEALTH_INSURANCE_EXEMPTION("건강보험외"),
    FREELANCER("프리랜서"),
    ADDITIONAL_COMPENSATION("추가급여"),
    ADVERTISING_EXPENSE("광고비"),
    MEDICINE("의약품"),
    CONSUMABLES("소모품"),
    OTHER_TAX_INVOICES("기타 세금계산서"),
    ADDITIONAL_ENTERTAINMENT_EXPENSE("접대비"),
    CARD_COMMISSION("카드수수료"),
    CREDIT_CARD_EXPENSE("신용카드 경비"),
    DONATION("기부금"),
    OTHER("기타"),
    ADDITIONAL_EXPENSES_TOTAL("추가비용 합계"),


    INTEREST_EXPENSE("이자비용"),
    BUSINESS_PRIVATE_VEHICLE_EXPENSE("업무용승용차 사적비용"),
    DEPRECIATION_EXPENSE("감가상각비"),
    ENTERTAINMENT_EXPENSE("접대비"),
    LEASE_PAYMENT("리스료"),
    FRINGE_BENEFIT_EXPENSE("복리후생비"),
    CONSUMABLES_EXPENSE("소모품비"),
    INCOME_TAX("소득세"),
    INCOME_ADJUSTMENT_TOTAL("소득조정 합계");
}

enum class AdjustmentCostItemOption(
    var code: String,
    var value: String,
) {
    FINAL("FINAL", "결산시 반영"),
    MONTH("MONTH", "월별 분할"),
    PERIOD("PERIOD", "기간 분할"),
}
