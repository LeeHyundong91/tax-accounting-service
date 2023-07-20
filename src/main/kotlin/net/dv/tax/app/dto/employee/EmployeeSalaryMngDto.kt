package net.dv.tax.app.dto.employee


import java.time.LocalDateTime

data class EmployeeSalaryMngDto (

    val id: Long? = null,

    var hospitalId: String? = null,

    var hospitalName: String? = null,

    var paymentsAt: String? = null,

    var employeeCnt: String? = null,

    var createdAt: LocalDateTime? = null,

    var payrollCreatedAt: LocalDateTime? = null,

    var apprState: String? = null,

    var apprStateName: String? = null,

    var fixedState: String? = null,

    var fixedStateName: String? = null,

)

