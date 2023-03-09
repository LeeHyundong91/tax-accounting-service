package net.dv.tax.dto.employee


data class EmployeeQueryDto (

    val name: String? = null,

    val regidentNumber: String? = null,

    val from: String? = null,

    val to: String? = null,

    val offset: Long? = 0,

    val size: Long? = 30,

    val jobClass: String? = null,

)

