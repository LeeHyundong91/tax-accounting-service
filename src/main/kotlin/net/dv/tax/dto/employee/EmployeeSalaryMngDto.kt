package net.dv.tax.dto.employee


import org.springframework.cglib.core.Local
import java.time.LocalDateTime

data class EmployeeSalaryMngDto (

    val id: Long,

    var hospitalId: String? = null,

    var hospitalName: String? = null,

    var paymentsAt: String? = null,

    var employeeCnt: String? = null,

    var createdAt: LocalDateTime? = null,

    var payrollCreatedAt: LocalDateTime? = null,

    var apprState: String? = null,

    var fixedState: String? = null,

)

