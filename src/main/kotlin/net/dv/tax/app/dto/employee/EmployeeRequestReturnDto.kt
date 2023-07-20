package net.dv.tax.app.dto.employee


data class EmployeeRequestReturnDto(

    var employeeRequestListCnt: Long,
    var employeeRequestList: List<EmployeeRequestDto>
)
