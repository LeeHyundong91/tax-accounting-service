package net.dv.tax.app.enums.purchase

//연봉 세후, 세전
// TODO 사용하는 위치를 찾아서 대체할 필요가 있음. 대체가 완료되면 삭제
enum class RecommendDeduction {

    RecommendDeduction_1(true, "불공제"),
    RecommendDeduction_2(false, "");

    var isRecommendDeduction: Boolean
    var recommendDeductionName: String

    constructor( isRecommendDeduction: Boolean ,recommendDeductionName: String ) {
        this.isRecommendDeduction = isRecommendDeduction
        this.recommendDeductionName = recommendDeductionName
    }
}

fun getRecommendDeductionName(isRecommendDeduction: Boolean?): String{
    var recommendDeductionName = "";

    RecommendDeduction.values().filter {
        it.isRecommendDeduction == isRecommendDeduction
    }?.also{
        if ( it.size > 0 ) recommendDeductionName = it.get(0).recommendDeductionName
    }
    return recommendDeductionName
}







