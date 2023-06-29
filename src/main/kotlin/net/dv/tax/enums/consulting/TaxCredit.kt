package net.dv.tax.enums.consulting

enum class TaxCreditCategory(
    var code: String,
    var value: String,
) {
    EMPLOYMENT_INCREASE("EMPLOYMENT_INCREASE", "고용증대세액공제"),
    INTEGRATED_EMPLOYMENT("INTEGRATED_EMPLOYMENT", "통합고용세액공제"),
    DEFAULT("DEFAULT", "기본항목"),
}


enum class TaxCreditItem(
    var value: String,
) {

    EMPLOYMENT_INCREASE("고용증대 세액공제"),
    SOCIAL_INSURANCE("사회보험료 세액공제"),
    CAREER_BREAK_FEMALE("경력단절여성 세액공제"),
    REGULAR_EMPLOYEE_CONVERSION("정규직 전환 세액공제"),
    RETURN_FROM_PARENTAL_LEAVE("육아휴직 복귀자 세액공제"),

    INTEGRATED_EMPLOYMENT("통합고용 세액공제"),
    ADDITIONAL_DEDUCTION("추가공제"),

    SME_PERFORMANCE_SHARING("성과공유중소기업 세액공제"),
    FAITHFUL_DECLARATION_COST("성실신고확인비용 세액공제"),
    SME_SPECIAL_TAX_REDUCTION("중소기업특별세액감면"),
    INTEGRATED_PROJECT("통합투제 세액공제");
}

enum class TaxCreditPersonalCategory(
    var code: String,
    var value: String,
) {
    HOSPITAL("HOSPITAL", "병원 공제"),
    PERSONAL("PERSONAL", "개인 공제"),
    TOTAL("TOTAL","합계")
}

enum class TaxCreditPersonalItem(
    var value: String,
) {
    CHILD("자녀 세액공제"),
    RETIREMENT_PENSION("퇴직연금(IRP) 세액공제"),
    PENSION_SAVINGS("연금저축 세액공제"),
    GUARANTEED_INSURANCE_GENERAL("보장성보험료_일반"),
    GUARANTEED_INSURANCE_DISABILITY("보장성보험료_장애인"),
    MEDICAL_EXPENSES("의료비"),
    EDUCATION_EXPENSES("교육비"),
    DONATION("기부금"),
    STANDARD_DEDUCTION("표준세액공제"),

    PERSONAL_SMALL_AMOUNT("개인 공제 합계"),
    HOSPITAL_SMALL_AMOUNT("병원 공제 합계"),

    TOTAL_AMOUNT("합계"),


}