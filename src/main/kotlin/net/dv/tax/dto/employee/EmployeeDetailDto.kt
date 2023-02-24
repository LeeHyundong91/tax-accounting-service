package net.dv.tax.dto.employee

import java.time.LocalDateTime

data class EmployeeDetailDto(

    var employee: EmployeeDto? = null,

    var attachFileList: List<EmployeeAttachFileDto>? = null,
)
