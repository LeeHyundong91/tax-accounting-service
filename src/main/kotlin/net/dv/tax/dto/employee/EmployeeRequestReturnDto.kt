package net.dv.tax.dto.employee


data class EmployeeRequestReturnDto(

    var employeeRequestListCnt: Long,
    var employeeRequestList: List<EmployeeRequestDto>
)
