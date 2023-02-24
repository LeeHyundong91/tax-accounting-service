package net.dv.tax.dto

enum class MenuCategoryCode(
    val code: String,
    val fileName: String,
) {
    MEDICAL_BENEFITS("medical-benefits", "요양급여매출목록"),
    CAR_INSURANCE("car-insurance", "자동차보험매춞목록"),
    VACCINE("vaccine", "예방접종매출목록"),
    MEDICAL_EXAM("medical-exam", "건강검진매출목록"),
    EMPLOYEE_INDUSTRY("employee-industry", "고용산재매출목록"),
    HOSPITAL_CHART("hospital-chart", "병원차트목록"),
    CREDIT_CARD("credit-card", "신용카드매출목록"),
    CASH_RECEIPT("cash-receipt", "현금영수증매출목록"),
    ELEC_INVOICE("elec-invoice", "전자계산서매출목록"),
    ELEC_TAX_INVOICE("elec-tax-invoice", "전자세금계산서매출목록"),
    HAND_INVOICE("hand-invoice", "수기세금계산서매출목록");

    companion object {
        fun getName(value: String): String {
            var codeName: String? = null
            MenuCategoryCode.values().forEach { enums ->
                if (enums.code == value) {
                    codeName = enums.name
                }
            }
            return codeName!!
        }
    }

}

