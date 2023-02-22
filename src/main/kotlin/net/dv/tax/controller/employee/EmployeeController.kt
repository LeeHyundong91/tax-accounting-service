package net.dv.tax.controller.employee

import net.dv.tax.dto.employee.EmployeeRequestDto
import net.dv.tax.enum.employee.getPositionName
import net.dv.tax.service.employee.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/employee")
class EmployeeController(private val workEmployeeService: EmployeeService) {

    //요청 목록
    @GetMapping("request/list/{hospitalId}")
    fun getEmployeeRequest(@PathVariable hospitalId: String
                            , offset: Long?, size: Long?
                            , searchType: String?, keyword: String?): List<EmployeeRequestDto> {

        return workEmployeeService.getEmployeeRequestList(hospitalId, offset?: 0, size?: 30, searchType, keyword);
    }

}