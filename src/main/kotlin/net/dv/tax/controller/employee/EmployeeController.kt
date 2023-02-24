package net.dv.tax.controller.employee

import net.dv.tax.dto.employee.EmployeeDto
import net.dv.tax.dto.employee.EmployeeRequestCloseDto
import net.dv.tax.dto.employee.EmployeeRequestDto
import net.dv.tax.dto.employee.EmployeeSalaryDto
import net.dv.tax.service.employee.EmployeeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/employee")
class EmployeeController(private val employeeService: EmployeeService) {

    //직원 요청 등록
    @PostMapping("request/insert")
    fun registerEmployeeRequest(@RequestBody data: EmployeeRequestDto): ResponseEntity<Int> {
        var res = employeeService.registerEmployeeRequest(data);
        return ResponseEntity.ok(res)
    }

    //직원 요청 목록
    @GetMapping("request/{hospitalId}/list")
    fun getEmployeeRequestList(@PathVariable hospitalId: String
                            , offset: Long?, size: Long?
                            , searchType: String?, keyword: String?): List<EmployeeRequestDto> {

        return employeeService.getEmployeeRequestList(hospitalId, offset?: 0, size?: 30, searchType, keyword);
    }

    //직원 요청 반영
    @PutMapping("request/{employeeId}/update")
    fun updateEmployeeRequestClose(@PathVariable employeeId: String): ResponseEntity<Int> {
        var res = employeeService.updateEmployeeRequestClose(employeeId);
        return ResponseEntity.ok(res)
    }

    //직원 요청 일괄 반영
    @PutMapping("request/{hospitalId}/updateAll")
    fun updateEmployeeRequestCloseAll(@PathVariable hospitalId: String): ResponseEntity<Int> {
        var res = employeeService.updateEmployeeRequestCloseAll(hospitalId);
        return ResponseEntity.ok(res)
    }

    //직원 요청 완료 항목 삭제
    @PutMapping("request/{hospitalId}/deleteAll")
    fun updateEmployeeRequestDeleteAll(@PathVariable hospitalId: String): ResponseEntity<Int> {
        var res = employeeService.updateEmployeeRequestDeleteAll(hospitalId);
        return ResponseEntity.ok(res)
    }

    //직원 신규 등록
    @PostMapping("insert")
    fun registerEmployee(@RequestBody data: EmployeeDto): ResponseEntity<Int> {

        var res = employeeService.registerEmployee(data);
        return ResponseEntity.ok(res)
    }

    //직원 수정
    @PutMapping("update")
    fun updateEmployee(@RequestBody data: EmployeeDto): ResponseEntity<Int> {

        var res = employeeService.updateEmployee(data);
        return ResponseEntity.ok(res)
    }

    //직원 목록
    @GetMapping("{hospitalId}/list")
    fun getEmployeeList(@PathVariable hospitalId: String
                        , offset: Long?, size: Long?
                        , searchType: String?, keyword: String?): List<EmployeeDto> {

        return employeeService.getEmployeeList(hospitalId, offset?: 0, size?: 30, searchType, keyword);
    }

   //직원 목록 상세
    @GetMapping("{employeeId}/detail")
    fun getEmployee(@PathVariable employeeId: String ): EmployeeDto? {

        return employeeService.getEmployee(employeeId);
    }

    //급여내역
    @GetMapping("salary/{hospitalId}/list")
    fun getSalaryList(@PathVariable hospitalId: String): List<EmployeeSalaryDto> {
        return employeeService.getSalaryList(hospitalId);
    }

}