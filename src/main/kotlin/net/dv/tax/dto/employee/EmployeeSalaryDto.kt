package net.dv.tax.dto.employee


import java.time.LocalDateTime

data class EmployeeSalaryDto (

    val id: Long,

    var hospitalId: String? = null,

    var basicSalary: Long? = null,

    var totalSalary: Long  ? = null,

    var detailSalary: String? = null,

    var nationalPension: Long? = null,

    var healthInsurance: Long? = null,

    var careInsurance: Long? = null,

    var unemployementInsurance: Long? = null,

    var incomeTax: Long? = null,

    var localIncomeTax: Long? = null,

    var incomeTaxYearEnd: Long? = null,

    var localIncomeTaxYearEnd: Long? = null,

    var actualPayment: Long? = null,

    var paymentsAt: LocalDateTime? = null,

    var createdAt: LocalDateTime?  = null,

    var employeeId: Long? = null,

)

