package net.dv.tax.app.enums.employee

//노무직원급여관리 승인상태
enum class Approval {

    Approval_1("1", "승인요청"),
    Approval_2("2", "승인대기"),
    Approval_3("3", "변경요청"),
    Approval_4("4", "승인완료");

    var approvalCode: String
    var approvalName: String

    constructor( approvalCode: String ,approvalName: String ) {
        this.approvalCode = approvalCode
        this.approvalName = approvalName
    }
}

fun getApprovalName(approvalCode: String): String{
    var approvalName = "";

    Approval.values().filter {
        it.approvalCode.equals(approvalCode)
    }?.also{
        if ( it.size > 0 ) approvalName = it.get(0).approvalName
    }

    return approvalName
}







