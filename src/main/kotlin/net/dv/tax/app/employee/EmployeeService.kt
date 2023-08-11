package net.dv.tax.app.employee

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.dv.tax.domain.employee.*
import net.dv.tax.app.dto.employee.*

import net.dv.tax.app.enums.employee.*
import net.dv.tax.utils.Encrypt
import org.dhatim.fastexcel.reader.Row
import org.joda.time.IllegalInstantException

import org.springframework.http.HttpStatus

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class EmployeeService(
    private val employeeRequestRepository: EmployeeRequestRepository,
    private val employeeRepository: EmployeeRepository,
    private val employeeHistoryRepository: EmployeeHistoryRepository,
    private val employeeAttachFileRepository: EmployeeAttachFileRepository,
    private val employeeSalaryRepository: EmployeeSalaryRepository,
    private val employeeSalaryMngRepository: EmployeeSalaryMngRepository,
    private val encrypt: Encrypt,
) {
    fun _parseLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
    //직원 요청 사항을 등록 한다.
    fun registerEmployeeRequest(employeeRequestDto: EmployeeRequestDto): Int {

        employeeRequestDto?.also { employeeRequest ->

//            var resignationAt: String? = null
//            employeeRequest.resignationAt?.also {
//                if (it.length > 0) resignationAt = it
//            }
            var entity = EmployeeRequestEntity()

            entity.encryptResidentNumber = encrypt.encodeToBase64(employeeRequest.residentNumber.toString())
            entity.residentNumber = employeeRequest.residentNumber
            entity.hospitalId = employeeRequest.hospitalId
            entity.hospitalName = employeeRequest.hospitalName
            entity.employeeCode = employeeRequest.employeeCode
            entity.name = employeeRequest.name
            entity.careerBreakYn = employeeRequest.careerBreakYn
            entity.spec = employeeRequest.spec
            entity.academicHistory = employeeRequest.academicHistory
            entity.contractDuration = employeeRequest.contractDuration
            entity.employment = employeeRequest.employment
            entity.annualType = employeeRequest.annualType
            entity.annualIncome = employeeRequest.annualIncome
            entity.position = employeeRequest.position
            entity.joinAt = _parseLocalDate(employeeRequest.joinAt.toString())
            entity.email = employeeRequest.email
            entity.jobClass = employeeRequest.jobClass
            entity.reason = employeeRequest.reason
            entity.enlistmentAt = _parseLocalDate(employeeRequest.enlistmentAt.toString())
            entity.dischargeAt = _parseLocalDate(employeeRequest.dischargeAt.toString())
            entity.workRenewalAt = _parseLocalDate(employeeRequest.workRenewalAt.toString())
            entity.resignationAt = _parseLocalDate(employeeRequest.resignationAt.toString())
            entity.resignationContents = employeeRequest.resignationContents
            entity.mobilePhoneNumber = employeeRequest.mobilePhoneNumber
            entity.office = employeeRequest.office
            entity.job = employeeRequest.job
            entity.careerNumber = employeeRequest.careerNumber
            entity.dependentCnt = employeeRequest.dependentCnt
            entity.address = employeeRequest.address
            entity.attachFileYn = employeeRequest.attachFileYn
            //등록시에는 무조건 P
            entity.requestState = RequestState.RequestState_P.requestStateCode
            employeeRequestRepository.save(entity)
        }

        return HttpStatus.OK.value()
    }

    //직원 요청 목록
    fun getEmployeeRequestList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): EmployeeRequestReturnDto {

        // 전체 카운트
        val employeeRequestListCnt = employeeRequestRepository.employeeRequestListCnt(hospitalId, employeeQueryDto)

        //리스트
        val employeeRequestList = employeeRequestRepository.employeeRequestList(hospitalId, employeeQueryDto).map {
            val entity = EmployeeRequestDto()

            entity.id = it.id!!
            entity.residentNumber = it.residentNumber
            entity.name = it.name
            entity.careerBreakYn = it.careerBreakYn
            entity.spec = it.spec
            entity.academicHistory = it.academicHistory
            entity.contractDuration = it.contractDuration
            entity.employment = it.employment
            entity.annualType = it.annualType
            entity.annualIncome = it.annualIncome
            entity.position = it.position ?: ""
            entity.joinAt = it.joinAt.toString()
            entity.address = it.address
            entity.email = it.email ?: ""
            entity.jobClass = getJobClassName(it.jobClass)
            entity.reason = it.reason ?: ""
            entity.job = it.job
            entity.dependentCnt = it.dependentCnt
            entity.enlistmentAt = it.enlistmentAt.toString()
            entity.careerNumber = it.careerNumber
            entity.dischargeAt = it.dischargeAt.toString()
            entity.workRenewalAt = it.workRenewalAt.toString()
            entity.resignationAt = it.resignationAt.toString()
            entity.resignationContents = it.resignationContents
            entity.createdAt = it.createdAt!!
            entity.requestStateCode = it.requestState
            entity.requestStateName = getRequestStateName(it.requestState)

            entity
        }

        return EmployeeRequestReturnDto(
            employeeRequestListCnt = employeeRequestListCnt,
            employeeRequestList = employeeRequestList
        )
    }

    //반영 
    @Transactional
    fun updateEmployeeRequestClose(employeeId: String): Int {

        employeeRequestRepository.findById(employeeId.toInt())?.map { employeeRequestEntity ->
            employeeRequestEntity?.also { employeeRequest ->
                updateEmployeeRequestCommonClose(employeeRequest)
            }
        }

        return HttpStatus.OK.value()
    }

    //일괄 반영
    @Transactional
    fun updateEmployeeRequestCloseAll(hospitalId: String): Int {
        employeeRequestRepository.findAllByHospitalIdAndRequestState(
            hospitalId, RequestState.RequestState_P.requestStateCode
        ).forEach { employeeRequestEntity ->
            employeeRequestEntity?.also { employeeRequest ->
                updateEmployeeRequestCommonClose(employeeRequest)

            }
        }

        return HttpStatus.OK.value()
    }

    //반영 로직
    fun updateEmployeeRequestCommonClose(employeeRequest: EmployeeRequestEntity) {

        //현재 등록 된 사용자 인지 확인한다.
        var resultEmployee = employeeRepository.findByHospitalIdAndResidentNumber(
            employeeRequest.hospitalId!!, employeeRequest.residentNumber!!
        )


        var employeeDto = EmployeeDto()

        employeeDto.residentNumber = employeeRequest.residentNumber
        employeeDto.hospitalId = employeeRequest.hospitalId
        employeeDto.hospitalName = employeeRequest.hospitalName
        employeeDto.employeeCode = employeeRequest.employeeCode
        employeeDto.name = employeeRequest.name
        employeeDto.employment = employeeRequest.employment
        employeeDto.annualType = employeeRequest.annualType
        employeeDto.annualIncome = employeeRequest.annualIncome
        employeeDto.position = employeeRequest.position
        employeeDto.joinAt = employeeRequest.joinAt.toString().substring(0, 10)
        employeeDto.email = employeeRequest.email
        employeeDto.jobClass = employeeRequest.jobClass!!
        employeeDto.reason = employeeRequest.reason
        employeeDto.resignationAt = employeeRequest.resignationAt.toString().substring(0, 10)
        employeeDto.resignationContents = employeeRequest.resignationContents
        employeeDto.mobilePhoneNumber = employeeRequest.mobilePhoneNumber
        employeeDto.office = employeeRequest.office
        employeeDto.job = employeeRequest.job
        employeeDto.careerNumber = employeeRequest.careerNumber
        employeeDto.dependentCnt = employeeRequest.dependentCnt
        employeeDto.address = employeeRequest.address
        employeeDto.attachFileYn = employeeRequest.attachFileYn

        // 등록된 사용자 일경우
        if (resultEmployee != null) {
            employeeDto.id = resultEmployee.id!!
            updateEmployee(employeeDto)
            //등록된 사용자가 아닐경우
        } else {
            registerEmployee(employeeDto)
        }

        //요청 목록에 데이터를 완료 상태로 변경 한다.
        employeeRequest.requestState = RequestState.RequestState_C.requestStateCode
        employeeRequestRepository.save(employeeRequest)

    }

    //완료 항목 삭제
    fun updateEmployeeRequestDeleteAll(hospitalId: String): Int {
        employeeRequestRepository.findAllByHospitalIdAndRequestState(
            hospitalId, RequestState.RequestState_C.requestStateCode
        ).forEach { employeeEntity ->

            employeeEntity.also { employee ->
                employee.requestState = RequestState.RequestState_D.requestStateCode
                employeeRequestRepository.save(employee);
            }
        }
        return HttpStatus.OK.value()
    }


    //직원등록
    @Transactional
    fun registerEmployee(employeeDto: EmployeeDto): Int {

        employeeRepository.findByHospitalIdAndResidentNumberAndJobClass(
            employeeDto.hospitalId!!,
            employeeDto.residentNumber!!,
            employeeDto.jobClass!!
        )?.also {
            throw Exception("이미 등록되어 있습니다.")
        }

        employeeDto?.also { employee ->

//            var resignationAt: String? = null
//            employee.resignationAt?.also {
//                if (it.length > 0) resignationAt = it
//            }

            var entity = EmployeeEntity()
            entity.encryptResidentNumber = encrypt.encodeToBase64(employee.residentNumber.toString())
            entity.residentNumber = employee.residentNumber
            entity.hospitalId = employee.hospitalId
            entity.hospitalName = employee.hospitalName
            entity.employeeCode = employee.employeeCode
            entity.name = employee.name
            entity.employment = employee.employment
            entity.annualType = employee.annualType
            entity.annualIncome = employee.annualIncome
            entity.position = employee.position
            entity.joinAt = _parseLocalDate(employee.joinAt.toString())
            entity.email = employee.email
            entity.jobClass = employee.jobClass
            entity.reason = employee.reason
            entity.resignationAt = _parseLocalDate(employee.resignationAt.toString())
            entity.resignationContents = employee.resignationContents
            entity.mobilePhoneNumber = employee.mobilePhoneNumber
            entity.office = employee.office
            entity.job = employee.job
            entity.careerNumber = employee.careerNumber
            entity.dependentCnt = employee.dependentCnt
            entity.address = employee.address
            entity.attachFileYn = employee.attachFileYn
            entity.apprAt = LocalDateTime.now()
            entity.taxRate = employee.taxRate

            employeeRepository.save(entity)

            //이력 등록
            registerEmployeeHistory(entity.id!!);

            //파일 목록이 있으면
            employeeDto.fileList?.also {
                it.forEach {
                    it.employeeId = entity.id!!
                    saveFile(it)
                }
            }
        }

        return HttpStatus.OK.value()
    }


    //직원수정
    @Transactional
    fun updateEmployee(employeeDto: EmployeeDto): Int {

        //기존의 정보 테이블을 조회 한다.
        employeeRepository.findById(employeeDto.id!!.toInt()).map { employeeEntity ->

            //변경 내용으로 덮기,
            employeeEntity?.also { employee ->

                employee.encryptResidentNumber = encrypt.encodeToBase64(employeeDto.residentNumber.toString())

                //퇴직일 경우 주민등록 번호 및 이름 공백 처리.
                if (employeeDto.jobClass.equals(JobClass.JobClass_R.jobClassCode)) {
                    employee.residentNumber = null
                    employee.name = null
                } else {
                    employee.residentNumber = employeeDto.residentNumber
                    employee.name = employeeDto.name
                }

                employee.employment = employeeDto.employment
                employee.annualType = employeeDto.annualType
                employee.annualIncome = employeeDto.annualIncome
                employee.position = employeeDto.position
                employee.joinAt = _parseLocalDate(employeeDto.joinAt.toString())
                employee.email = employeeDto.email
                employee.jobClass = employeeDto.jobClass
                employee.reason = employeeDto.reason
                employee.resignationContents = employeeDto.resignationContents
                employee.mobilePhoneNumber = employeeDto.mobilePhoneNumber
                employee.office = employeeDto.office
                employee.job = employeeDto.job
                employee.careerNumber = employeeDto.careerNumber
                employee.dependentCnt = employeeDto.dependentCnt
                employee.address = employeeDto.address
                employee.apprAt = LocalDateTime.now()
                employee.attachFileYn = employeeDto.attachFileYn
                employee.taxRate = employeeDto.taxRate
                employee.resignationAt = _parseLocalDate(employeeDto.resignationAt.toString())

                //저장
                employeeRepository.save(employee)

                //이력 등록
                registerEmployeeHistory(employee.id!!);

                //파일 목록이 있으면
                employeeDto.fileList?.also {
                    it.forEach {
                        it.employeeId = employee.id!!
                        saveFile(it)
                    }
                }
            }
        }

        return HttpStatus.OK.value()
    }

    //파일 등록 및 수정
    fun saveFile(employeeAttachFileDto: EmployeeAttachFileDto): Int {

        var path: String = ""

        employeeAttachFileDto.path?.also {
            path = it
        } ?: throw Exception("filePath is null ")

        val localFileName = path.substringAfterLast("/")
        val ext = path.substringAfterLast(".")

        employeeAttachFileDto.employeeId?.also {
            val employee = employeeRepository.findById(employeeAttachFileDto.employeeId.toInt()).get()

            val employeeAttachFileEntity = EmployeeAttachFileEntity(
                fileName = employeeAttachFileDto.fileName,
                localFileName = localFileName,
                path = path,
                fileExt = ext,
                useYn = employeeAttachFileDto.useYn,
                createdAt = LocalDateTime.now(),
                employee = employee
            )

            //아이디가 있으면 수정 없으면 등록
            employeeAttachFileDto.id?.also {
                employeeAttachFileEntity.id = employeeAttachFileDto.id
                employeeAttachFileDto.createdAt = employeeAttachFileDto.createdAt
            }

            employeeAttachFileRepository.save(employeeAttachFileEntity)

        } ?: throw Exception("employeeId is null")

        return HttpStatus.OK.value()
    }

    //직원 등록, 변경시 이력 등록
    fun registerEmployeeHistory(employeeId: Long) {
        employeeRepository.findById(employeeId.toInt())?.map { employeeEntity ->

            employeeEntity?.also {
                var entity = EmployeeHistoryEntity()

                entity.encryptResidentNumber = employeeEntity.encryptResidentNumber
                entity.hospitalId = employeeEntity.hospitalId
                entity.hospitalName = employeeEntity.hospitalName
                entity.employeeCode = employeeEntity.employeeCode
                entity.employment = employeeEntity.employment
                entity.annualType = employeeEntity.annualType
                entity.annualIncome = employeeEntity.annualIncome
                entity.position = employeeEntity.position
                entity.joinAt = employeeEntity.joinAt
                entity.email = employeeEntity.email
                entity.jobClass = employeeEntity.jobClass
                entity.reason = employeeEntity.reason
                entity.resignationAt = employeeEntity.resignationAt
                entity.resignationContents = employeeEntity.resignationContents
                entity.mobilePhoneNumber = employeeEntity.mobilePhoneNumber
                entity.office = employeeEntity.office
                entity.job = employeeEntity.job
                entity.careerNumber = employeeEntity.careerNumber
                entity.dependentCnt = employeeEntity.dependentCnt
                entity.address = employeeEntity.address
                entity.apprAt = employeeEntity.apprAt
                entity.attachFileYn = employeeEntity.attachFileYn
                entity.employee = employeeEntity

                //퇴직이 아니면 이름과 주민번호 등록
                if (!employeeEntity.jobClass.equals(JobClass.JobClass_R.jobClassCode)) {
                    entity.name = employeeEntity.name
                    entity.residentNumber = employeeEntity.residentNumber
                }


                employeeHistoryRepository.save(entity);
            }
        }
    }

    //직원 요청 목록
    fun getEmployeeList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): EmployeeReturnDto {

        val employeeListCnt = employeeRepository.employeeListCnt(hospitalId, employeeQueryDto)
        val employeeList = employeeRepository.employeeList(hospitalId, employeeQueryDto).map {
            val employeeDto = EmployeeDto()

            employeeDto.id = it.id!!
            employeeDto.residentNumber = it.residentNumber
            employeeDto.name = it.name
            employeeDto.employment = it.employment
            employeeDto.annualType = it.annualType
            employeeDto.annualIncome = it.annualIncome
            employeeDto.position = it.position ?: ""
            employeeDto.joinAt = it.joinAt.toString()
            employeeDto.email = it.email ?: ""
            employeeDto.jobClass = getJobClassName(it.jobClass)
            employeeDto.reason = it.reason ?: ""
            employeeDto.mobilePhoneNumber = it.mobilePhoneNumber
            employeeDto.resignationAt = it.resignationAt.toString()

            employeeDto
        }

        return EmployeeReturnDto(
            employeeListCnt = employeeListCnt,
            employeeList = employeeList

        )
    }

    fun getEmployee(employeeId: String): EmployeeDetailDto? {

        var employeeDto: EmployeeDto = EmployeeDto()

        employeeRepository.findById(employeeId.toInt()).map { it ->
            it?.also {
                employeeDto.residentNumber = it.residentNumber
                employeeDto.hospitalId = it.hospitalId
                employeeDto.hospitalName = it.hospitalName
                employeeDto.employeeCode = it.employeeCode
                employeeDto.name = it.name!!
                employeeDto.careerBreakYn = it.careerBreakYn
                employeeDto.spec = it.spec
                employeeDto.academicHistory = it.academicHistory
                employeeDto.contractDuration = it.contractDuration
                employeeDto.employment = it.employment
                employeeDto.annualType = it.annualType
                employeeDto.annualIncome = it.annualIncome
                employeeDto.position = it.position
                employeeDto.joinAt = it.joinAt?.toString()
                employeeDto.email = it.email
                employeeDto.jobClass = it.jobClass.toString()
                employeeDto.reason = it.reason
                employeeDto.enlistmentAt = it.enlistmentAt.toString()
                employeeDto.dischargeAt = it.dischargeAt.toString()
                employeeDto.workRenewalAt = it.workRenewalAt.toString()
                employeeDto.resignationAt = it.resignationAt.toString()
                employeeDto.resignationContents = it.resignationContents
                employeeDto.mobilePhoneNumber = it.mobilePhoneNumber
                employeeDto.office = it.office
                employeeDto.job = it.job
                employeeDto.careerNumber = it.careerNumber
                employeeDto.dependentCnt = it.dependentCnt
                employeeDto.address = it.address
                employeeDto.apprAt = it.apprAt
                employeeDto.attachFileYn = it.attachFileYn
                employeeDto.id = it.id!!
                employeeDto.writerId = it.writerId
                employeeDto.writerName = it.writerName
                employeeDto.taxRate = it.taxRate
                employeeDto.createdAt = it.createdAt
                employeeDto.updatedAt = it.updatedAt
            }
        }


        val attachFileList = employeeRepository.employeeFileList(employeeId.toLong()).map { fileEntity ->
            EmployeeAttachFileDto(
                id = fileEntity.id!!,
                fileName = fileEntity.fileName,
                localFileName = fileEntity.localFileName,
                path = fileEntity.path,
                fileSize = fileEntity.fileSize,
                fileExt = fileEntity.fileExt,
                createdAt = fileEntity.createdAt,
                employeeId = employeeId.toLong(),
                useYn = fileEntity.useYn
            )
        }

        val employeeDetailDto = EmployeeDetailDto(
            employee = employeeDto,
            attachFileList = attachFileList
        )


        return employeeDetailDto;
    }

    //직원 급여 조회
    fun getSalaryEmployeeList(hospitalId: String, employeeId: String): List<EmployeeSalaryDto> {

        val employee: EmployeeEntity = employeeRepository.findById(employeeId.toInt()).get()

        employee?.also {} ?: throw Exception("사용자가 없습니다.")

        return employeeSalaryRepository.findByHospitalIdAndEmployeeCode(hospitalId, employee.employeeCode!!).map {
            EmployeeSalaryDto(
                id = it.id!!,
                hospitalId = it.hospitalId,
                employeeCode = it.employeeCode,
                name = it.name,
                basicSalary = it.basicSalary,
                totalSalary = it.totalSalary,
                detailSalary = it.detailSalary,
                nationalPension = it.nationalPension,
                healthInsurance = it.healthInsurance,
                careInsurance = it.careInsurance,
                unemployementInsurance = it.unemployementInsurance,
                incomeTax = it.incomeTax,
                localIncomeTax = it.localIncomeTax,
                incomeTaxYearEnd = it.incomeTaxYearEnd,
                localIncomeTaxYearEnd = it.localIncomeTaxYearEnd,
                actualPayment = it.actualPayment,
                paymentsAt = it.paymentsAt,
                createdAt = it.createdAt,
            )
        }
    }

    fun getSalaryMngList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): EmployeeSalaryMngReturnDto {

        val employeeSalaryMngListCnt = employeeSalaryMngRepository.getSalaryMngListCnt(hospitalId, employeeQueryDto);
        val employeeSalaryMngList = employeeSalaryMngRepository.getSalaryMngList(hospitalId, employeeQueryDto).map {
            EmployeeSalaryMngDto(
                id = it.id!!,
                hospitalId = it.hospitalId,
                hospitalName = it.hospitalName,
                paymentsAt = it.paymentsAt,
                employeeCnt = it.employeeCnt.toString(),
                createdAt = it.createdAt,
                payrollCreatedAt = it.payrollCreatedAt,
                apprState = it.apprState,
                apprStateName = getApprovalName(it.apprState ?: "1"),
                fixedState = it.fixedState,
                fixedStateName = getFixedName(it.fixedState ?: "1")
            )
        }

        return EmployeeSalaryMngReturnDto(
            employeeSalaryMngListCnt = employeeSalaryMngListCnt,
            employeeSalaryMngList = employeeSalaryMngList
        )
    }

    fun getSalaryMngDetailList(salaryMngId: String): EmployeeSalaryReturnDto {

        val salaryMngEntity = employeeSalaryMngRepository.findById(salaryMngId.toInt())

        val salaryList = employeeSalaryRepository.findByEmployeeSalaryMng(salaryMngEntity.get()).map {
            EmployeeSalaryDto(
                id = it.id!!,
                hospitalId = it.hospitalId,
                employeeCode = it.employeeCode,
                name = it.name,
                basicSalary = it.basicSalary,
                totalSalary = it.totalSalary,
                detailSalary = it.detailSalary,
                nationalPension = it.nationalPension,
                healthInsurance = it.healthInsurance,
                careInsurance = it.careInsurance,
                unemployementInsurance = it.unemployementInsurance,
                incomeTax = it.incomeTax,
                localIncomeTax = it.localIncomeTax,
                incomeTaxYearEnd = it.incomeTaxYearEnd,
                localIncomeTaxYearEnd = it.localIncomeTaxYearEnd,
                actualPayment = it.actualPayment,
                paymentsAt = it.paymentsAt,
                createdAt = it.createdAt,
            )
        }

        val salaryMngDto = EmployeeSalaryMngDto(
            id = salaryMngEntity.get().id,
            apprState = salaryMngEntity.get().apprState,
            fixedState = salaryMngEntity.get().fixedState,
            paymentsAt = salaryMngEntity.get().paymentsAt,
        )


        return EmployeeSalaryReturnDto(
            employeeSalaryList = salaryList,
            employeeSalaryMng = salaryMngDto
        )
    }

    fun updateSalaryMngAppr(salaryMngId: String, apprCode: String): Int {

        val employeeSalaryMng = employeeSalaryMngRepository.findById(salaryMngId.toInt()).get()
        employeeSalaryMng.apprState = apprCode

        employeeSalaryMngRepository.save(employeeSalaryMng)

        return HttpStatus.OK.value()
    }

    @Transactional
    fun updateSalaryMngFixed(salaryMngId: String, fixedCode: String): Int {

        val employeeSalaryMng = employeeSalaryMngRepository.findById(salaryMngId.toInt()).get()
        employeeSalaryMng.fixedState = fixedCode

        //메일 전송 등등등
        if (fixedCode.equals(Fixed.Fixed_3.fixedCode)) {

        }

        employeeSalaryMngRepository.save(employeeSalaryMng)

        return HttpStatus.OK.value()
    }

    fun insertSalary(employeeSalary: EmployeeSalary): Int {

        val employeeSalaryMngEntity = EmployeeSalaryMngEntity(
            hospitalId = employeeSalary.hospitalId,
            hospitalName = employeeSalary.hospitalName,
            paymentsAt = employeeSalary.paymentsAt,
            employeeCnt = employeeSalary.employeeSalaryList!!.size.toLong(),
            createdAt = LocalDateTime.now()
        )

        employeeSalaryMngRepository.save(employeeSalaryMngEntity)

        employeeSalary.employeeSalaryList.forEach {

            val employeeSalaryEntity = EmployeeSalaryEntity(
                hospitalId = it.hospitalId,
                employeeCode = it.employeeCode,
                name = it.name,
                basicSalary = it.basicSalary,
                totalSalary = it.totalSalary,
                detailSalary = it.detailSalary,
                nationalPension = it.nationalPension,
                healthInsurance = it.healthInsurance,
                careInsurance = it.careInsurance,
                unemployementInsurance = it.unemployementInsurance,
                incomeTax = it.incomeTax,
                localIncomeTax = it.localIncomeTax,
                incomeTaxYearEnd = it.incomeTaxYearEnd,
                localIncomeTaxYearEnd = it.localIncomeTaxYearEnd,
                actualPayment = it.actualPayment,
                paymentsAt = employeeSalary.paymentsAt,
            )
            employeeSalaryRepository.save(employeeSalaryEntity)
        }
        return HttpStatus.OK.value()
    }


    //excel 파일로 직원을 일괄 등록 한다.
    @Transactional
    fun registerEmployeeExcelRequest(
        hospitalId: String,
        hospitalName: String,
        filePath: String,
        excelRows: MutableList<Row>,
    ): Int {

        val dataList = mutableListOf<EmployeeEntity>()

        /*Remove Title*/
        excelRows.removeFirst()
        excelRows.removeFirst()

        excelRows.forEach { row ->

            if (row.cellCount > 0) {
                val defaultName = row.getCell(2).rawValue

                //이름이 있는지 없는지에 따라서 진행 한다.
                if (!defaultName.isNullOrEmpty()) {

                    val joinAtStr = row.getCell(9)?.rawValue
                    val joinAt = LocalDate.parse(joinAtStr, DateTimeFormatter.ofPattern("yyyyMMdd"))

                    // 중복 체크 한다.

                    val checkList =
                        employeeRepository.findByHospitalIdAndEmployeeCode(hospitalId, row.getCell(1).rawValue)

                    if (checkList.isNullOrEmpty()) {
                        val entity = EmployeeEntity()

                        entity.hospitalId = hospitalId
                        entity.hospitalName = hospitalName
                        entity.employeeCode = row.getCell(1).rawValue
                        entity.name = defaultName
                        entity.encryptResidentNumber = encrypt.encodeToBase64(row.getCell(4).rawValue)
                        entity.residentNumber = row.getCell(4).rawValue
                        entity.jobClass = JobClass.JobClass_W.jobClassCode
                        entity.joinAt = joinAt
                        entity.mobilePhoneNumber = row.getCell(12).rawValue
                        entity.email = row.getCell(13).rawValue
                        entity.address = row.getCell(10).rawValue + " " + row.getCell(11).rawValue
                        entity.filePath = filePath
                        entity.office = row.getCell(8).rawValue
                        entity.position = row.getCell(6).rawValue
                        dataList.add(entity)
                    }
                }
            }

        }

        if (dataList.size > 0) employeeRepository.saveAll(dataList)

        return HttpStatus.OK.value()
    }

    //excel 파일로 월급여를 등록 한다.
    @Transactional
    fun insertSalaryExcel(
        hospitalId: String,
        hospitalName: String,
        filePath: String,
        excelRows: MutableList<Row>,
        paymentsAt: String,
    ): Int {


        //등록 가능한지 체크
        var isReg: Boolean = true;

        //검토상태로 해야 하는지 체크
        var isCheck: Boolean = false;

        //파일명으로 등록된 파일 중복 체크
        val deleteStr = paymentsAt.substring(0, 7)

        val salaryMngDeleteList = employeeSalaryMngRepository.getSalaryMngDeleteList(hospitalId, deleteStr)

        //확정 처리 상태인게 있을경우 에러 발생
        salaryMngDeleteList.forEach {
            if (it.fixedState.equals(Fixed.Fixed_3.fixedCode)) {
                throw IllegalInstantException("이미 확정난 데이터 입니다.");
                isReg = false
            }
        }

        //이상없음
        if (isReg) {

            val salaryDeleteList = employeeSalaryRepository.getSalaryDeleteList(hospitalId, deleteStr)

            employeeSalaryRepository.deleteAll(salaryDeleteList)
            employeeSalaryMngRepository.deleteAll(salaryMngDeleteList)


            //등록할 데이터
            val dataList = mutableListOf<EmployeeSalaryEntity>()

            //상세내역
            val detailSalaryIdxList = mutableListOf<Int>()
            val detailSalaryNameList = mutableListOf<String>()

            //상세내역 범위
            val basicSalary = "기본급"
            val totalSalary = "지급액계"

            //필수값 지정
            val fixedList = mutableListOf<String>(
                "기본급",
                "지급액계",
                "국민연금",
                "건강보험",
                "고용보험",
                "장기요양보험료",
                "소득세",
                "지방소득세",
                "소득세 정산",
                "차인지급액"
            )
            var fixedIdxList = mutableListOf<Int>()

            var isDetailIdx = false;

            /*Remove 합계*/
            excelRows.removeLast()

            //기준데이터 만들기
            excelRows.get(1).forEachIndexed { rIdx, cell ->

                //상세 금액으로 처리 되야 하는 부분
                if (!cell.rawValue.isNullOrEmpty() && cell.rawValue.equals(totalSalary)) isDetailIdx = false

                if (!cell.rawValue.isNullOrEmpty() && isDetailIdx) {
                    detailSalaryIdxList.add(rIdx)
                    detailSalaryNameList.add(cell.rawValue)
                }

                if (!cell.rawValue.isNullOrEmpty() && cell.rawValue.equals(basicSalary)) isDetailIdx = true

                //필수값 셋팅
                fixedList.forEach { fixValue ->
                    if (!cell.rawValue.isNullOrEmpty() && cell.rawValue.equals(fixValue)) {
                        fixedIdxList.add(rIdx)
                    }
                }
            }

            //지급금액이 0번째 로우 지만 제일 마지막이어서 뒤에 돌린다.
            excelRows.get(0).forEachIndexed { rIdx, cell ->
                //필수값 셋팅
                fixedList.forEach { fixValue ->
                    if (!cell.rawValue.isNullOrEmpty() && cell.rawValue.equals(fixValue)) {
                        fixedIdxList.add(rIdx)
                    }
                }
            }

            //기준데이터 생성후 삭제
            excelRows.removeFirst()
            excelRows.removeFirst()

            //관리 페이지 등록
            var employeeSalaryMngEntity = EmployeeSalaryMngEntity(
                hospitalId = hospitalId,
                hospitalName = hospitalName,
                employeeCnt = excelRows.size.toLong(),
                createdAt = LocalDateTime.now(),
                paymentsAt = paymentsAt,
                apprState = Approval.Approval_1.approvalCode,
                fixedState = Fixed.Fixed_1.fixedCode,
                filePath = filePath
            )

            var salaryMng = employeeSalaryMngRepository.save(employeeSalaryMngEntity)

            //월급여 등록
            excelRows.forEachIndexed { idx, row ->
                val detailMap = mutableMapOf<String, String>()

                var nameIdx = 0
                row.forEachIndexed { rIdx, cell ->
                    detailSalaryIdxList.forEach { dIdx ->
                        if (rIdx == dIdx) {
                            detailMap[detailSalaryNameList.get(nameIdx)] = cell.rawValue
                            nameIdx++
                        }
                    }
                }

                val detailJsonStr = Json.encodeToString(detailMap)

                val employeeSalaryEntity = EmployeeSalaryEntity(
                    hospitalId = hospitalId,
                    employeeCode = row.getCell(0).rawValue,
                    detailSalary = detailJsonStr,
                    name = row.getCell(1).rawValue,
                    paymentsAt = paymentsAt,
                    employeeSalaryMng = salaryMng
                )

                if (!row.getCell(fixedIdxList.get(0)).rawValue.isNullOrEmpty()) employeeSalaryEntity.basicSalary =
                    row.getCell(fixedIdxList.get(0)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(1)).rawValue.isNullOrEmpty()) employeeSalaryEntity.totalSalary =
                    row.getCell(fixedIdxList.get(1)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(2)).rawValue.isNullOrEmpty()) employeeSalaryEntity.nationalPension =
                    row.getCell(fixedIdxList.get(2)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(3)).rawValue.isNullOrEmpty()) employeeSalaryEntity.healthInsurance =
                    row.getCell(fixedIdxList.get(3)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(4)).rawValue.isNullOrEmpty()) employeeSalaryEntity.unemployementInsurance =
                    row.getCell(fixedIdxList.get(4)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(5)).rawValue.isNullOrEmpty()) employeeSalaryEntity.careInsurance =
                    row.getCell(fixedIdxList.get(5)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(6)).rawValue.isNullOrEmpty()) employeeSalaryEntity.incomeTax =
                    row.getCell(fixedIdxList.get(6)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(7)).rawValue.isNullOrEmpty()) employeeSalaryEntity.localIncomeTax =
                    row.getCell(fixedIdxList.get(7)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(8)).rawValue.isNullOrEmpty()) employeeSalaryEntity.incomeTaxYearEnd =
                    row.getCell(fixedIdxList.get(8)).rawValue.toLong()
                if (!row.getCell(fixedIdxList.get(9)).rawValue.isNullOrEmpty()) employeeSalaryEntity.actualPayment =
                    row.getCell(fixedIdxList.get(9)).rawValue.toLong()

                dataList.add(employeeSalaryEntity)

                //검토 해야 하는지 확인
                row.forEachIndexed { idx, cell ->
                    if (idx > 5) {
                        if (cell.rawValue.isNullOrEmpty()) isCheck = true
                    }
                }
            }
            employeeSalaryRepository.saveAll(dataList)

            //문제가 있으면 검토상태로 수정한다.
            if (isCheck) {
                salaryMng.fixedState = Fixed.Fixed_2.fixedCode
                employeeSalaryMngRepository.save(salaryMng)
            }
        }

        return HttpStatus.OK.value()
    }
}