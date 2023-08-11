package net.dv.tax.app.dto.employee

import net.dv.tax.app.enums.employee.RequestState
import java.time.LocalDate
import java.time.LocalDateTime

data class EmployeeRequestDto(
    var createdAt: LocalDateTime? = LocalDateTime.now(),
    var updatedAt: String? = null,
    var requestStateCode: String? = RequestState.RequestState_P.requestStateCode,
    var requestStateName: String? = null,
) : EmployeeBaseDto()
