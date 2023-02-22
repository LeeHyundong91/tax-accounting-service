package net.dv.tax.enum.employee

//고용유형
enum class Employeement {

    Employeement_1("1", "정규직"),
    Employeement_2("2", "인턴"),
    Employeement_3("3", "계약직"),
    Employeement_4("4", "프리랜서"),
    Employeement_5("5", "아르바이트"),
    Employeement_6("6", "기간제");

    var employeementCode: String
    var employeementName: String

    constructor( employeementCode: String ,employeementName: String ) {
        this.employeementCode = employeementCode
        this.employeementName = employeementName
    }
}

fun getEmployeementName(employeementCode: String): String{

    var employeementName = "";

    Employeement.values().filter {
        it.employeementCode.equals(employeementCode)
    }?.also{
        if ( it.size > 0 ) employeementName = it.get(0).employeementCode
    }
    return employeementName
}








