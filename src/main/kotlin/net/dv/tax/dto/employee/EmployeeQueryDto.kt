package net.dv.tax.dto.employee


data class EmployeeQueryDto (

    //이름
    val name: String? = null,

    //주민번호
    val regidentNumber: String? = null,

    //입사일
    val joinAt: String? = null,

    //사원번호
    val employeeCode: String? = null,

    //시작페이지
    val offset: Long? = 0,

    //조회갯수
    val size: Long? = 30,

    //재직구분
    val jobClass: String? = null,

    //승인구분
    val apprCode: String? = null,

    //확정구분
    val fixedCode: String? = null,

    //귀속년도
    val year: String? = null,

    //최근전송일
    val sendDate: String? = null

)

