package net.dv.tax.enum.employee

//연봉 세후, 세전
enum class RequestState {

    RequestState_P("P", "대기"),
    RequestState_C("C", "완료"),
    RequestState_D("D", "완료 및 요청목록삭제");

    var requestStateCode: String
    var requestStateName: String

    constructor( requestStateCode: String ,requestStateName: String ) {
        this.requestStateCode = requestStateCode
        this.requestStateName = requestStateName
    }
}

fun getRequestStateName(requestStateCode: String): String{
    var requestStateName = "";

    RequestState.values().filter {
        it.requestStateCode.equals(requestStateCode)
    }?.also{
        if ( it.size > 0 ) requestStateName = it.get(0).requestStateName
    }
    return requestStateName
}







