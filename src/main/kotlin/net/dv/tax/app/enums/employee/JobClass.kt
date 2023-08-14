package net.dv.tax.app.enums.employee

//재직구분
enum class JobClass {

    JobClass_W("W", "재직"),
    JobClass_L("L", "휴직"),
    JobClass_R("R", "퇴직");

    var jobClassCode: String
    var jobClassName: String

    constructor( jobClassCode: String ,jobClassName: String ) {
        this.jobClassCode = jobClassCode
        this.jobClassName = jobClassName
    }
}

fun getJobClassName(jobClassCode: String?): String{

    var jobClassName = "";

    JobClass.values().filter {
        it.jobClassCode.equals(jobClassCode)
    }?.also{
        if ( it.size > 0 ) jobClassName = it.get(0).jobClassName
    }
    return jobClassName

}








