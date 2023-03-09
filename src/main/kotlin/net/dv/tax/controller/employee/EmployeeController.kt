package net.dv.tax.controller.employee

import net.dv.tax.TaxAccountingApplication
import net.dv.tax.dto.employee.*
import net.dv.tax.service.employee.EmployeeService
import net.dv.tax.utils.AwsS3Service
import net.dv.tax.utils.ExcelComponent
import org.hibernate.boot.model.naming.IllegalIdentifierException
import org.springframework.core.io.FileSystemResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@RestController
@RequestMapping("/${TaxAccountingApplication.VERSION}/employee")
class EmployeeController(
    private val employeeService: EmployeeService,
    private val excelComponent: ExcelComponent,
    private val awsS3Service: AwsS3Service
    ) {

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

        var res = employeeService.registerEmployee(data)
        return ResponseEntity.ok(res)
    }

    //직원 수정
    @PutMapping("update")
    fun updateEmployee(@RequestBody data: EmployeeDto): ResponseEntity<Int> {

        var res = employeeService.updateEmployee(data)
        return ResponseEntity.ok(res)
    }

    //직원 목록
    @GetMapping("{hospitalId}/list")
    fun getEmployeeList(@PathVariable hospitalId: String, employeeQueryDto: EmployeeQueryDto): ResponseEntity<EmployeeReturnDto> {

        return ResponseEntity.ok( employeeService.getEmployeeList(hospitalId, employeeQueryDto))
    }

   //직원 목록 상세
    @GetMapping("{employeeId}/detail")
    fun getEmployee(@PathVariable employeeId: String ): EmployeeDetailDto? {

        return employeeService.getEmployee(employeeId)
    }

    //월별 직원 급여 내역
    @GetMapping("salary/{hospitalId}/{employeeId}/list")
    fun getSalaryEmployeeList(@PathVariable hospitalId: String, @PathVariable employeeId: String ): List<EmployeeSalaryDto> {
        return employeeService.getSalaryEmployeeList(hospitalId, employeeId)
    }

    //급여대장 목록 조회
    @GetMapping("salary/mng/{hospitalId}/list")
    fun getSalaryMngList(@PathVariable hospitalId: String, employeeQueryDto: EmployeeQueryDto): ResponseEntity<EmployeeSalaryMngReturnDto> {
        return ResponseEntity.ok(employeeService.getSalaryMngList(hospitalId, employeeQueryDto))
    }

    //월별 급여 내역
    @GetMapping("salary/mng/{salaryMngId}/list")
    fun getSalaryMngDetailList(@PathVariable hospitalId: String, @PathVariable salaryMngId: String ): List<EmployeeSalaryDto> {
        return employeeService.getSalaryMngDetailList( salaryMngId)
    }

    //승인요청 하기
    @PutMapping("salary/mng/{salaryMngId}/appr")
    fun updateSalaryMngAppr(@PathVariable salaryMngId: String, apprCode: String ): ResponseEntity<Int> {
        var res = employeeService.updateSalaryMngAppr(salaryMngId, apprCode)
        return ResponseEntity.ok(res)
    }

    //확정 하기
    @PutMapping("salary/mng/{salaryMngId}/fixed")
    fun updateSalaryMngFixed(@PathVariable salaryMngId: String, fixedCode: String ): ResponseEntity<Int> {
        var res = employeeService.updateSalaryMngFixed(salaryMngId, fixedCode)
        return ResponseEntity.ok(res)
    }

    //급여대장 등록
    @PostMapping("salary/mng/insert")
    fun insertSalary(@RequestBody data: EmployeeSalary): ResponseEntity<Int> {
        return ResponseEntity.ok(employeeService.insertSalary( data))
    }


    //직원일괄 등록( 엑셀 파일 특성상 신규 등록만 취급한다. )
    @PostMapping("request/insert/{hospitalId}/{hospitalName}/excel")
    fun registerEmployeeExcelRequest(@PathVariable hospitalId: String, @PathVariable hospitalName: String,  excelFile: MultipartFile): ResponseEntity<Int> {

        val reportType = "employeeRegister"
        //파일을 s3에 업로드 한다.
        val returnMap = awsS3Service.upload(reportType, excelFile)
        val filePath = returnMap["filePath"]?: throw IllegalIdentifierException("filepath is empty")
        val rows = excelComponent.readXlsx(awsS3Service.getFileFromBucket(filePath))

        //업로드 된 파일 기준 등록 한다.
        var res = employeeService.registerEmployeeExcelRequest(hospitalId, hospitalName, filePath, rows)

        return ResponseEntity.ok(res)
    }

    //직원일괄 등록( 엑셀 파일 특성상 신규 등록만 취급한다. )
    @PostMapping("salary/insert/{hospitalId}/{hospitalName}/excel")
    fun rinsertSalaryExcel(@PathVariable hospitalId: String, @PathVariable hospitalName: String, excelFile: MultipartFile): ResponseEntity<Int> {

        val reportType = "employeeRegister"
        //파일을 s3에 업로드 한다.
        val returnMap = awsS3Service.upload(reportType, excelFile)
        val filePath = returnMap["filePath"]?: throw IllegalIdentifierException("filepath is empty")
        val rows = excelComponent.readXlsx(awsS3Service.getFileFromBucket(filePath))

        //업로드 된 파일 기준 등록 한다.
        var res = employeeService.insertSalaryExcel(hospitalId, hospitalName, filePath, rows, excelFile.originalFilename.toString().substringBefore("."))

        return ResponseEntity.ok(res)
    }

    @GetMapping("download/file")
    fun employeeFileDownload( filePath: String, fileName: String): ResponseEntity<FileSystemResource> {

        val file:File = awsS3Service.getFileFromBucket(filePath)
        val resource = FileSystemResource(file)

        // Set response headers
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.contentLength = file.length()

        val encodedFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20")
        headers.setContentDisposition(ContentDisposition.builder("attachment")
            .filename(encodedFileName)
            .build())

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource)
    }

}