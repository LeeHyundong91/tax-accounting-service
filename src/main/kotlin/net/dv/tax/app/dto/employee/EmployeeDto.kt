package net.dv.tax.app.dto.employee

import java.time.LocalDate
import java.time.LocalDateTime

data class EmployeeDto(
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var fileList: List<EmployeeAttachFileDto>? = null,
    var taxRate: String? = null,
    var accountId: String? = null,
    var apprAt: LocalDateTime? = null
) : EmployeeBaseDto()
