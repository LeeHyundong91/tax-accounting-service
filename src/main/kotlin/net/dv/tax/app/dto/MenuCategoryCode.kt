package net.dv.tax.app.dto

enum class MenuCategoryCode(
    val code: String,
    val fileName: String,
    val purchaseFileName: String,
) {
    MEDICAL_BENEFITS("medical-benefits", "요양급여매출", ""),
    CAR_INSURANCE("car-insurance", "자동차보험매춞", ""),
    VACCINE("vaccine", "예방접종매출", ""),
    MEDICAL_EXAM("medical-exam", "건강검진매출", ""),
    EMPLOYEE_INDUSTRY("employee-industry", "고용산재매출", ""),
    HOSPITAL_CHART("hospital-chart", "병원차트", ""),
    CREDIT_CARD("credit-card", "신용카드매출", "신용카드매입"),
    CASH_RECEIPT("cash-receipt", "현금영수증매출", "현금영수증매입"),
    ELEC_INVOICE("elec-invoice", "전자계산서매출", "전자계산서매입"),
    ELEC_TAX_INVOICE("elec-tax-invoice", "전자세금계산서매출", "전자세금계산서매입"),
    HAND_INVOICE("hand-invoice", "수기세금계산서매출", ""),
    PASSBOOK("passbook","통장매입관리","통장매입관리"),
    HANDWRITTEN("handwritten", "수기매입대상관리","수기매입대상관리");

    companion object {
        fun convert(value: String): String {
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

