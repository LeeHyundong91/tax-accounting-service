package net.dv.tax.enums.purchase

//연봉 세후, 세전
enum class Deduction {

    Deduction_1(true, "공제"),
    Deduction_2(false, "불공제");

    var isDeduction: Boolean
    var deductionName: String

    constructor( isDeduction: Boolean ,deductionName: String ) {
        this.isDeduction = isDeduction
        this.deductionName = deductionName
    }
}

fun getDeductionName(isDeduction: Boolean): String{
    var deductionName = "";

    Deduction.values().filter {
        it.isDeduction.equals(isDeduction)
    }?.also{
        if ( it.size > 0 ) deductionName = it.get(0).deductionName
    }
    return deductionName
}







