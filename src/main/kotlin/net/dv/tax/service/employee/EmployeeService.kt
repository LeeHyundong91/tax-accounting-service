package net.dv.tax.service.employee

import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeHistoryEntity

import net.dv.tax.dto.employee.EmployeeDto
import net.dv.tax.dto.employee.EmployeeRequestDto
import net.dv.tax.dto.employee.EmployeeSalaryDto
import net.dv.tax.enum.employee.*
import net.dv.tax.repository.employee.EmployeeHistoryRepository
import net.dv.tax.repository.employee.EmployeeRepository
import net.dv.tax.repository.employee.EmployeeSalaryRepository
import net.dv.tax.utils.Encrypt

import org.springframework.http.HttpStatus

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val employeeHistoryRepository: EmployeeHistoryRepository,
    private val employeeSalaryRepository: EmployeeSalaryRepository,

    private val encrypt:Encrypt,
    ) {

    fun getEmployeeRequestList(
        hospitalId: String,
        offset: Long,
        size: Long,
        searchType: String?,
        keyword: String?
    ): List<EmployeeRequestDto> {

        val realOffset = offset * size;

        return employeeRepository.employeeRequestList(hospitalId, realOffset, size, searchType, keyword).map {
            EmployeeRequestDto(
                id = it.id!!,
                residentNumber = it.residentNumber,
                name = it.name,
                employment = getEmployeementName(it.employmentType),
                annualType = it.annualType,
                annualIncome = it.annualIncome,
                position = getPositionName(it.position ?: ""),
                joinAt = it.joinAt,
                email = it.email ?: "",
                jobClass = getJobClassName(it.jobClass),
                reason = it.reason ?: "",
                createdAt = it.createdAt!!,
                requestStateCode = it.requestState,
                requestStateName = getRequestStateName(it.requestState)
            )
        }
    }

    //직원등록
    @Transactional
    fun registerEmployee(employeeDto: EmployeeDto): Int {

        employeeRepository.findByHospitalIdAndResidentNumberAndJobClass(employeeDto.hospitalId, employeeDto.residentNumber!!, employeeDto.jobClass)?.also{
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
                hospitalId = employee.hospitalId,
                hospitalName = employee.hospitalName,
                name = employee.name,
                employmentType = employee.employmentType,
                annualType = employee.annualType,
                annualIncome = employee.annualIncome,
                position = employee.position,
                joinAt = joinAt,
                email = employee.email,
                jobClass = employee.jobClass,
                reason = employee.reason,
                requestState = employee.requestState,
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
        employeeRepository.findById(employeeDto.id.toInt()).map { employeeEntity ->

            //변경 내용으로 덮기,
            employeeEntity?.also { employee ->

                val joinAt = LocalDateTime.parse(
                    employeeDto.joinAt + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                val apprAt = LocalDateTime.parse(
                    employeeDto.apprAt + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


                employee.encryptResidentNumber = encrypt.encodeToBase64(employeeDto.residentNumber.toString())
                employee.residentNumber = employeeDto.residentNumber           
                employee.name = employeeDto.name
                employee.employmentType = employeeDto.employmentType
                employee.annualType = employeeDto.annualType
                employee.annualIncome = employeeDto.annualIncome
                employee.position = employeeDto.position
                employee.joinAt = joinAt
                employee.email = employeeDto.email            
                employee.jobClass = employeeDto.jobClass
                employee.reason = employeeDto.reason
                employee.requestState = employeeDto.requestState
                employee.resignationAt = employeeDto.resignationAt
                employee.resignationContents = employeeDto.resignationContents
                employee.mobilePhoneNumber = employeeDto.mobilePhoneNumber
                employee.office = employeeDto.office
                employee.job = employeeDto.job
                employee.jobDetail = employeeDto.jobDetail
                employee.careerNumber = employeeDto.careerNumber
                employee.dependentCnt = employeeDto.dependentCnt
                employee.address = employeeDto.address
                employee.apprAt = apprAt
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
                    name = employeeEntity.name,
                    employmentType = employeeEntity.employmentType,
                    annualType = employeeEntity.annualType,
                    annualIncome = employeeEntity.annualIncome,
                    position = employeeEntity.position,
                    joinAt = employeeEntity.joinAt,
                    email = employeeEntity.email,
                    jobClass = employeeEntity.jobClass,
                    reason = employeeEntity.reason,
                    requestState = employeeEntity.requestState,
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
                    employeeEntity = employeeEntity
                )
                employeeHistoryRepository.save(saveEmployeeHistoryEntity);
            }
        }
    }

    //반영 
    @Transactional
    fun updateEmployeeRequestClose(employeeId: String): Int {

        employeeRepository.findById(employeeId.toInt())?.map { employee ->
            employee?.also {
                updateEmployeeRequestCommonClose(employee)
            }
        }

        return HttpStatus.OK.value()
    }

    //일괄 반영
    @Transactional
    fun updateEmployeeRequestCloseAll(hospitalId: String): Int {
        employeeRepository.findAllByHospitalIdAndRequestState(
            hospitalId, RequestState.RequestState_P.requestStateCode ).map{ employeeEntity ->
            employeeEntity?.also { employee ->
                updateEmployeeRequestCommonClose(employee)
            }
        }

        return HttpStatus.OK.value()
    }

    //반영 로직
    fun updateEmployeeRequestCommonClose( employee: EmployeeEntity) {

        var resultEmployee  = employeeRepository.findByHospitalIdAndResidentNumberAndRequestStateNot(
            employee.hospitalId, employee.residentNumber!!, RequestState.RequestState_P.requestStateCode)

        var historyEmployeeId: Long? = null

        // 등록된 행이 있으면 상태값(재직, 휴직, 퇴직)만 변경한다.
        // 반영시 상태값(재직, 휴직, 퇴직) 뿐 아니라 다른 데이터도 업데이트가 필요 하다면 수정 해야 한다.
        if( resultEmployee != null) {

            resultEmployee.requestState = employee.requestState
            resultEmployee.requestState = RequestState.RequestState_C.requestStateCode
            resultEmployee.apprAt = LocalDateTime.now()

            //수정 사항을 등록한다.
            employeeRepository.save(resultEmployee)
            historyEmployeeId = resultEmployee.id

            //현재 등록된 행을 삭제 한다.
            employeeRepository.delete(employee);

        //등록된 행이 없으면 ( 신규등록 )
        }else {
            employee.requestState = RequestState.RequestState_C.requestStateCode
            employee.apprAt = LocalDateTime.now()

            //반영상태를 저장한다.
            employeeRepository.save(employee)
            historyEmployeeId = employee.id
        }

        //이력사항을 등록 한다.
        historyEmployeeId?.also{ employeeId ->
            registerEmployeeHistory( employeeId)
        }

    }

    //완료 항목 삭제
    fun updateEmployeeRequestDeleteAll( hospitalId: String): Int  {
        employeeRepository.findAllByHospitalIdAndRequestState(
            hospitalId, RequestState.RequestState_C.requestStateCode).map{ employeeEntity ->

            employeeEntity.also { employee ->
                employee.requestState = RequestState.RequestState_D.requestStateCode
                employeeRepository.save(employee);
            }
        }
        return HttpStatus.OK.value()
    }



}