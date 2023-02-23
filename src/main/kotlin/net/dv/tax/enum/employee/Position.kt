package net.dv.tax.enum.employee

//직위
enum class Position {

    POSITION_1("1", "사원"),
    POSITION_2("2", "대리"),
    POSITION_3("3", "과장"),
    POSITION_4("4", "차장"),
    POSITION_5("5", "부장"),
    POSITION_6("6", "상무"),
    POSITION_7("7", "이사"),
    POSITION_8("8", "대표");

    var positionCode: String
    var positionName: String

    constructor( positionCode: String ,positionName: String ) {
        this.positionCode = positionCode
        this.positionName = positionName
    }

}

fun getPositionName(positionCode: String): String {

    var positionName = "";

    Position.values().filter {
        it.positionCode.equals(positionCode)
    }?.also{
        if ( it.size > 0 ) positionName = it.get(0).positionName
    }

    return positionName
}









