package net.dv.tax.controller.employee

import feign.Response
import net.dv.tax.dto.employee.*
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
    fun getEmployeeRequestList(@PathVariable hospitalId: String, employeeQueryDto: EmployeeQueryDto): ResponseEntity<EmployeeRequestReturnDto> {

        return ResponseEntity.ok( employeeService.getEmployeeRequestList(hospitalId, employeeQueryDto) ) ;
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
    fun getEmployeeList(@PathVariable hospitalId: String, employeeQueryDto: EmployeeQueryDto): ResponseEntity<EmployeeReturnDto> {

        return ResponseEntity.ok( employeeService.getEmployeeList(hospitalId, employeeQueryDto));
    }

   //직원 목록 상세
    @GetMapping("{employeeId}/detail")
    fun getEmployee(@PathVariable employeeId: String ): EmployeeDetailDto? {

        return employeeService.getEmployee(employeeId);
    }

    //월별 직원 급여 내역
    @GetMapping("salary/{hospitalId}/{employeeId}/list")
    fun getSalaryEmployeeList(@PathVariable hospitalId: String, @PathVariable employeeId: String ): List<EmployeeSalaryDto> {
        return employeeService.getSalaryEmployeeList(hospitalId, employeeId);
    }

    //급여대장 목록 조회
    @GetMapping("salary/mng/{hospitalId}/list")
    fun getSalaryMngList(@PathVariable hospitalId: String, employeeQueryDto: EmployeeQueryDto): ResponseEntity<EmployeeSalaryMngReturnDto> {
        return ResponseEntity.ok(employeeService.getSalaryMngList(hospitalId, employeeQueryDto));
    }

    //월별 급여 내역
    @GetMapping("salary/mng/{hospitalId}/{salaryMngId}/list")
    fun getSalaryMngDetailList(@PathVariable hospitalId: String, @PathVariable salaryMngId: String ): List<EmployeeSalaryDto> {
        return employeeService.getSalaryMngDetailList(hospitalId, salaryMngId);
    }

    //승인요청 하기
    @PutMapping("salary/mng/{salaryMngId}/appr")
    fun updateSalaryMngAppr(@PathVariable salaryMngId: String, apprCode: String ): ResponseEntity<Int> {
        var res = employeeService.updateSalaryMngAppr(salaryMngId, apprCode);
        return ResponseEntity.ok(res)
    }

    //확정 하기
    @PutMapping("salary/mng/{salaryMngId}/fixed")
    fun updateSalaryMngFixed(@PathVariable salaryMngId: String, fixedCode: String ): ResponseEntity<Int> {
        var res = employeeService.updateSalaryMngFixed(salaryMngId, fixedCode);
        return ResponseEntity.ok(res)
    }

    //급여대장 등록
    @PostMapping("salary/mng/insert")
    fun insertSalary(@RequestBody data: EmployeeSalary): ResponseEntity<Int> {
        return ResponseEntity.ok(employeeService.insertSalary( data));
    }

}