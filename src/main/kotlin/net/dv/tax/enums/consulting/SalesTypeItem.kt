package net.dv.tax.enums.consulting

enum class SalesTypeItem(
    var value: String
) {
    SMOKING_TREATMENT("금연치료"),
    WRITTEN_OPINION("소견서"),
    RARITY("희귀"),
    OTHER_COSTS("기타"),
    CAR_INSURANCE("자동차보험"),
    HEALTH_CARE("건강검진"),
    VACCINE("예방접종"),
    EMPLOYEE("고용산재"),
    NORMAL("일반매출"),
    MEDICAL_BENEFITS("요양급여"),
    MEDICAL_CARE("의료급여");
}
enum class SalesDivideType(
    var value: String
) {
    OWN_CHARGE("(본인)"),
    CORP_CHARGE("(공단)");
}