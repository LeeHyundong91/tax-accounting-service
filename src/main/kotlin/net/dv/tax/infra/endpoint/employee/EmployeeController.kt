package net.dv.tax.infra.endpoint.employee

import net.dv.access.Jwt
import net.dv.tax.Application
import net.dv.tax.app.dto.employee.*
import net.dv.tax.app.employee.EmployeeService
import net.dv.tax.app.enums.employee.RequestState
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
import java.util.*

@RestController
@RequestMapping("/${Application.VERSION}/employee")
class EmployeeController(
    private val employeeService: EmployeeService,
    private val excelComponent: ExcelComponent,
    private val awsS3Service: AwsS3Service
    ) {

    //직원 요청 등록
    @PostMapping("request/insert")
    fun registerEmployeeRequest(@RequestBody data: EmployeeRequestDto): ResponseEntity<Int> {

        data.requestStateCode = RequestState.RequestState_P.requestStateCode

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

    // 직원 정보 등록폼 기본정보 조회
    @GetMapping("")
    fun getEmployeeByGuest(
        @Jwt("sub") accountId: String?,
        @Jwt("name") name: String?,
        @RequestParam hospitalId: String?,
        @Jwt("email") email: String?): ResponseEntity<EmployeeDto> {

        val data = EmployeeDto()
        data.accountId = accountId
        data.email = email
        data.name = name
        data.hospitalId = hospitalId
        val res = employeeService.getEmployeeByGuest(data)
        return ResponseEntity.ok(res)
    }

    //직원 신규 등록
    @PostMapping("insert")
    fun registerEmployee(
        @RequestBody data: EmployeeDto): ResponseEntity<Int> {

        val res = employeeService.registerEmployee(data)
        return ResponseEntity.ok(res)
    }

    //직원 수정
    @PutMapping("update")
    fun updateEmployee(
        @Jwt("email") email: String?,
        @Jwt("sub") accountId: String?,
        @Jwt("name") name: String?,
        @RequestBody data: EmployeeDto): ResponseEntity<Int> {

        data.email = email
        data.name = name
        data.accountId = accountId

        val res = employeeService.updateEmployee(data)
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

    //급여대장 목록 상세 내역
    @GetMapping("salary/mng/{salaryMngId}/detail/list")
    fun getSalaryMngDetailList(@PathVariable salaryMngId: String ): EmployeeSalaryReturnDto {
        return employeeService.getSalaryMngDetailList( salaryMngId)
    }

    //승인요청 하기
    @PutMapping("salary/mng/{salaryMngId}/appr")
    fun updateSalaryMngAppr(@PathVariable salaryMngId: String, @RequestBody employeeQueryDto: EmployeeQueryDto ): ResponseEntity<Int> {
        var res = employeeService.updateSalaryMngAppr(salaryMngId, employeeQueryDto.apprCode!!)
        return ResponseEntity.ok(res)
    }

    //확정 하기
    @PutMapping("salary/mng/{salaryMngId}/fixed")
    fun updateSalaryMngFixed(@PathVariable salaryMngId: String, @RequestBody employeeQueryDto: EmployeeQueryDto ): ResponseEntity<Int> {
        var res = employeeService.updateSalaryMngFixed(salaryMngId, employeeQueryDto.fixedCode!!)
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

    //급여일괄 등록( 엑셀 파일 특성상 신규 등록만 취급한다. )
    @PostMapping("salary/insert/{hospitalId}/{hospitalName}/excel")
    fun rinsertSalaryExcel(@PathVariable hospitalId: String, @PathVariable hospitalName: String, excelFile: MultipartFile): ResponseEntity<Int> {

        val reportType = "employeeSalaryRegister"
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