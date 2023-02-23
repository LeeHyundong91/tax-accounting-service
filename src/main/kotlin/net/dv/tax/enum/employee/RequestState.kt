package net.dv.tax.enum.employee

//요청상태
enum class RequestState {

    RequestState_P("P", "대기", "요청 대기 상태"),
    RequestState_C("C", "완료", "요청 완료 상태"),
    RequestState_D("D", "삭제", "요청 완료 후 목록에서 삭제된 상태");

    var requestStateCode: String
    var requestStateName: String
    var requestStateComment: String


    constructor( requestStateCode: String, requestStateName: String, requestStateComment: String ) {
        this.requestStateCode = requestStateCode
        this.requestStateName = requestStateName
        this.requestStateComment = requestStateComment
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






