package net.dv.tax.service.employee

import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeHistoryEntity
import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.dto.employee.*

import net.dv.tax.enum.employee.*
import net.dv.tax.repository.employee.*
import net.dv.tax.utils.Encrypt

import org.springframework.http.HttpStatus

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.LinkedList

@Service
class EmployeeService(
    private val employeeRequestRepository: EmployeeRequestRepository,
    private val employeeRepository: EmployeeRepository,
    private val employeeHistoryRepository: EmployeeHistoryRepository,
    private val employeeSalaryRepository: EmployeeSalaryRepository,
    private val employeeAttachFileRepository: EmployeeAttachFileRepository,
    private val encrypt:Encrypt,
    ) {


    //직원 요청 사항을 등록 한다.
    fun registerEmployeeRequest(employeeRequestDto: EmployeeRequestDto): Int{

        employeeRequestDto?.also { employeeRequest ->

            val joinAt = LocalDateTime.parse(employeeRequest.joinAt + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var resignationAt: String? = null
            employeeRequest.resignationAt?.also {
                if( it.length > 0 ) resignationAt = it
            }

            var saveEmployeeRequestEntity = EmployeeRequestEntity(
                encryptResidentNumber = encrypt.encodeToBase64(employeeRequest.residentNumber.toString()),
                residentNumber = employeeRequest.residentNumber,
                hospitalId = employeeRequest.hospitalId!!,
                hospitalName = employeeRequest.hospitalName!!,
                name = employeeRequest.name,
                employmentType = employeeRequest.employmentType!!,
                annualType = employeeRequest.annualType,
                annualIncome = employeeRequest.annualIncome,
                position = employeeRequest.position,
                joinAt = joinAt,
                email = employeeRequest.email,
                jobClass = employeeRequest.jobClass,
                reason = employeeRequest.reason,
                resignationAt = resignationAt ,
                resignationContents = employeeRequest.resignationContents,
                mobilePhoneNumber = employeeRequest.mobilePhoneNumber,
                office = employeeRequest.office,
                job = employeeRequest.job,
                jobDetail = employeeRequest.jobDetail,
                careerNumber = employeeRequest.careerNumber,
                dependentCnt = employeeRequest.dependentCnt,
                address = employeeRequest.address,
                attachFileYn = employeeRequest.attachFileYn,
                //등록시에는 무조건 P
                requestState = RequestState.RequestState_P.requestStateCode
            )

            employeeRequestRepository.save(saveEmployeeRequestEntity)
            employeeRequest.id = saveEmployeeRequestEntity.id!!
        }

        return HttpStatus.OK.value()
    }

    //직원 요청 목록
    fun getEmployeeRequestList(
        hospitalId: String,
        offset: Long,
        size: Long,
        searchType: String?,
        keyword: String?
    ): List<EmployeeRequestDto> {

        val realOffset = offset * size;

        return employeeRequestRepository.employeeRequestList(hospitalId, realOffset, size, searchType, keyword).map {
            EmployeeRequestDto(
                id = it.id!!,
                residentNumber = it.residentNumber,
                name = it.name,
                employment = getEmployeementName(it.employmentType),
                annualType = it.annualType,
                annualIncome = it.annualIncome,
                position = getPositionName(it.position ?: ""),
                joinAt = it.joinAt.toString(),
                email = it.email ?: "",
                jobClass = getJobClassName(it.jobClass),
                reason = it.reason ?: "",
                createdAt = it.createdAt!!,
                requestStateCode = it.requestState,
                requestStateName = getRequestStateName(it.requestState),
            )
        }
    }

    //반영 
    @Transactional
    fun updateEmployeeRequestClose(employeeId: String): Int {

        employeeRequestRepository.findById(employeeId.toInt())?.map {  employeeRequestEntity ->
            employeeRequestEntity?.also {employeeRequest ->
                updateEmployeeRequestCommonClose(employeeRequest)
            }
        }

        return HttpStatus.OK.value()
    }

    //일괄 반영
    @Transactional
    fun updateEmployeeRequestCloseAll(hospitalId: String): Int {
        employeeRequestRepository.findAllByHospitalIdAndRequestState(
            hospitalId, RequestState.RequestState_P.requestStateCode ).map{ employeeRequestEntity ->
            employeeRequestEntity?.also { employeeRequest ->
                updateEmployeeRequestCommonClose(employeeRequest)

            }
        }

        return HttpStatus.OK.value()
    }

    //반영 로직
    fun updateEmployeeRequestCommonClose( employeeRequest: EmployeeRequestEntity) {

        //현재 등록 된 사용자 인지 확인한다.
        var resultEmployee  = employeeRepository.findByHospitalIdAndResidentNumber(
            employeeRequest.hospitalId, employeeRequest.residentNumber!!)


        var employeeDto = EmployeeDto(
            residentNumber = employeeRequest.residentNumber,
            hospitalId = employeeRequest.hospitalId,
            hospitalName = employeeRequest.hospitalName,
            name = employeeRequest.name,
            employmentType = employeeRequest.employmentType,
            annualType = employeeRequest.annualType,
            annualIncome = employeeRequest.annualIncome,
            position = employeeRequest.position,
            joinAt = employeeRequest.joinAt.toString().substring(0, 10),
            email = employeeRequest.email,
            jobClass = employeeRequest.jobClass,
            reason = employeeRequest.reason,
            resignationAt = employeeRequest.resignationAt,
            resignationContents = employeeRequest.resignationContents,
            mobilePhoneNumber = employeeRequest.mobilePhoneNumber,
            office = employeeRequest.office,
            job = employeeRequest.job,
            jobDetail = employeeRequest.jobDetail,
            careerNumber = employeeRequest.careerNumber,
            dependentCnt = employeeRequest.dependentCnt,
            address = employeeRequest.address,
            attachFileYn = employeeRequest.attachFileYn
        )

        // 등록된 사용자 일경우
        if( resultEmployee != null) {
            employeeDto.id = resultEmployee.id!!
            updateEmployee(employeeDto)
        //등록된 사용자가 아닐경우
        }else {
            registerEmployee(employeeDto)
        }

        //요청 목록에 데이터를 완료 상태로 변경 한다.
        employeeRequest.requestState = RequestState.RequestState_C.requestStateCode
        employeeRequestRepository.save(employeeRequest)

    }

    //완료 항목 삭제
    fun updateEmployeeRequestDeleteAll( hospitalId: String): Int  {
        employeeRequestRepository.findAllByHospitalIdAndRequestState(
            hospitalId, RequestState.RequestState_C.requestStateCode).map{ employeeEntity ->

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

        employeeRepository.findByHospitalIdAndResidentNumberAndJobClass(employeeDto.hospitalId!!, employeeDto.residentNumber!!, employeeDto.jobClass)?.also{
            throw Exception("이미 등록되어 있습니다.")
        }

        employeeDto?.also { employee ->

            val joinAt = LocalDateTime.parse(employee.joinAt + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var resignationAt: String? = null
            employee.resignationAt?.also {
                if( it.length > 0 ) resignationAt = it
            }

            var saveEmployeeEntity = EmployeeEntity(
                encryptResidentNumber = encrypt.encodeToBase64(employee.residentNumber.toString()),
                residentNumber = employee.residentNumber,
                hospitalId = employee.hospitalId!!,
                hospitalName = employee.hospitalName!!,
                name = employee.name,
                employmentType = employee.employmentType,
                annualType = employee.annualType,
                annualIncome = employee.annualIncome,
                position = employee.position,
                joinAt = joinAt,
                email = employee.email,
                jobClass = employee.jobClass,
                reason = employee.reason,
                resignationAt = resignationAt ,
                resignationContents = employee.resignationContents,
                mobilePhoneNumber = employee.mobilePhoneNumber,
                office = employee.office,
                job = employee.job,
                jobDetail = employee.jobDetail,
                careerNumber = employee.careerNumber,
                dependentCnt = employee.dependentCnt,
                address = employee.address,
                attachFileYn = employee.attachFileYn,
                apprAt = LocalDateTime.now()
            )

            employeeRepository.save(saveEmployeeEntity)
            employee.id = saveEmployeeEntity.id!!

            //이력 등록
            registerEmployeeHistory(saveEmployeeEntity.id!!);
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

                val joinAt = LocalDateTime.parse(
                    employeeDto.joinAt + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                employee.encryptResidentNumber = encrypt.encodeToBase64(employeeDto.residentNumber.toString())


                //퇴직일 경우 주민등록 번호 및 이름 공백 처리.
                if( employeeDto.jobClass.equals(JobClass.JobClass_R)) {
                    employee.residentNumber = null
                    employee.name = null
                } else {
                    employee.residentNumber = employeeDto.residentNumber
                    employee.name = employeeDto.name
                }

                employee.employmentType = employeeDto.employmentType
                employee.annualType = employeeDto.annualType
                employee.annualIncome = employeeDto.annualIncome
                employee.position = employeeDto.position
                employee.joinAt = joinAt
                employee.email = employeeDto.email
                employee.jobClass = employeeDto.jobClass
                employee.reason = employeeDto.reason
                employee.resignationAt = employeeDto.resignationAt
                employee.resignationContents = employeeDto.resignationContents
                employee.mobilePhoneNumber = employeeDto.mobilePhoneNumber
                employee.office = employeeDto.office
                employee.job = employeeDto.job
                employee.jobDetail = employeeDto.jobDetail
                employee.careerNumber = employeeDto.careerNumber
                employee.dependentCnt = employeeDto.dependentCnt
                employee.address = employeeDto.address
                employee.apprAt = LocalDateTime.now()
                employee.attachFileYn = employeeDto.attachFileYn

                //저장
                employeeRepository.save(employee)

                //이력 등록
                registerEmployeeHistory(employee.id!!);
            }
        }

        return HttpStatus.OK.value()
    }


    //직원 등록, 변경시 이력 등록
    fun registerEmployeeHistory(employeeId: Long) {
        employeeRepository.findById(employeeId.toInt())?.map { employeeEntity ->
            employeeEntity?.also {
                var saveEmployeeHistoryEntity = EmployeeHistoryEntity(
                    encryptResidentNumber = employeeEntity.encryptResidentNumber,
                    residentNumber = employeeEntity.residentNumber,
                    hospitalId = employeeEntity.hospitalId,
                    hospitalName = employeeEntity.hospitalName,
                    name = employeeEntity.name!!,
                    employmentType = employeeEntity.employmentType,
                    annualType = employeeEntity.annualType,
                    annualIncome = employeeEntity.annualIncome,
                    position = employeeEntity.position,
                    joinAt = employeeEntity.joinAt,
                    email = employeeEntity.email,
                    jobClass = employeeEntity.jobClass,
                    reason = employeeEntity.reason,
                    resignationAt = employeeEntity.resignationAt,
                    resignationContents = employeeEntity.resignationContents,
                    mobilePhoneNumber = employeeEntity.mobilePhoneNumber,
                    office = employeeEntity.office,
                    job = employeeEntity.job,
                    jobDetail = employeeEntity.jobDetail,
                    careerNumber = employeeEntity.careerNumber,
                    dependentCnt = employeeEntity.dependentCnt,
                    address = employeeEntity.address,
                    apprAt = employeeEntity.apprAt,
                    attachFileYn = employeeEntity.attachFileYn,
                    employee = employeeEntity
                )
                employeeHistoryRepository.save(saveEmployeeHistoryEntity);
            }
        }
    }

    //직원 요청 목록
    fun getEmployeeList(
        hospitalId: String,
        offset: Long,
        size: Long,
        searchType: String?,
        keyword: String?
    ): List<EmployeeDto> {

        val realOffset = offset * size;

        return employeeRepository.employeeList(hospitalId, realOffset, size, searchType, keyword).map {
            EmployeeDto(
                id = it.id!!,
                residentNumber = it.residentNumber,
                name = it.name!!,
                employmentName = getEmployeementName(it.employmentType),
                employmentType = it.employmentType,
                annualType = it.annualType,
                annualIncome = it.annualIncome,
                position = getPositionName(it.position ?: ""),
                joinAt = it.joinAt.toString(),
                email = it.email ?: "",
                jobClass = getJobClassName(it.jobClass),
                reason = it.reason ?: ""
            )
        }
    }

    fun getEmployee( employeeId: String ): EmployeeDetailDto? {

        var employeeDto: EmployeeDto? = null

        employeeRepository.findById(employeeId.toInt()).map {employeeEntity ->
            employeeEntity?.also {
                employeeDto = EmployeeDto(
                    residentNumber = employeeEntity.residentNumber,
                    hospitalId = employeeEntity.hospitalId,
                    hospitalName = employeeEntity.hospitalName,
                    name = employeeEntity.name!!,
                    employmentType = employeeEntity.employmentType,
                    annualType = employeeEntity.annualType,
                    annualIncome = employeeEntity.annualIncome,
                    position = employeeEntity.position,
                    joinAt = employeeEntity.joinAt.toString(),
                    email = employeeEntity.email,
                    jobClass = employeeEntity.jobClass,
                    reason = employeeEntity.reason,
                    resignationAt = employeeEntity.resignationAt,
                    resignationContents = employeeEntity.resignationContents,
                    mobilePhoneNumber = employeeEntity.mobilePhoneNumber,
                    office = employeeEntity.office,
                    job = employeeEntity.job,
                    jobDetail = employeeEntity.jobDetail,
                    careerNumber = employeeEntity.careerNumber,
                    dependentCnt = employeeEntity.dependentCnt,
                    address = employeeEntity.address,
                    apprAt = employeeEntity.apprAt,
                    attachFileYn = employeeEntity.attachFileYn,
                    id = employeeEntity.id!!
                )
            }
        }


        val attachFileList = employeeRepository.employeeFileList(employeeId.toLong()).map{fileEntity ->

            EmployeeAttachFileDto(
                id = fileEntity.id,
                fileName = fileEntity.fileName,
                localFileName = fileEntity.localFileName,
                path = fileEntity.path,
                fileSize = fileEntity.fileSize,
                fileExt = fileEntity.fileExt,
                createdAt = fileEntity.createdAt,
                employeeId = employeeId
            )
        }

        val employeeDetailDto = EmployeeDetailDto(
            employee = employeeDto,
            attachFileList = attachFileList
        )


        return employeeDetailDto;
    }

    //급여 조회
    fun getSalaryList(hospitalId: String, employeeId: String): List<EmployeeSalaryDto> {


        val employee: EmployeeEntity = employeeRepository.findById(employeeId.toInt()).get()

        employee?.also{}?: throw Exception("사용자가 없습니다.")

        return employeeSalaryRepository.findByHospitalIdAndEmployee(hospitalId, employee).map {
            EmployeeSalaryDto(
                id = it.id,
                hospitalId = it.hospitalId,
                basicSalary =  it.basicSalary,
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
                employeeId = it.employee!!.id,
            )
        }
    }

}