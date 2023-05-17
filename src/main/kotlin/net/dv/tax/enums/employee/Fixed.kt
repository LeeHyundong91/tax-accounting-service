package net.dv.tax.enums.employee

//노무직원급여관리 확정처리상태
enum class Fixed {

    Fixed_1("1", "확정"),
    Fixed_2("2", "검토"),
    Fixed_3("3", "확정됨");

    var fixedCode: String
    var fixedName: String

    constructor( fixedCode: String ,fixedName: String ) {
        this.fixedCode = fixedCode
        this.fixedName = fixedName
    }
}

fun getFixedName(fixedCode: String): String{
    var fixedName = "";

    Fixed.values().filter {
        it.fixedCode.equals(fixedCode)
    }?.also{
        if ( it.size > 0 ) fixedName = it.get(0).fixedName
    }

    return fixedName
}







