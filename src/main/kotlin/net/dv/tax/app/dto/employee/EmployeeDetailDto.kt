package net.dv.tax.app.dto.employee

data class EmployeeDetailDto(

    var employee: EmployeeDto? = null,

    var attachFileList: List<EmployeeAttachFileDto>? = null,
)
