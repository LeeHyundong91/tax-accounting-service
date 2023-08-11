package net.dv.tax.domain.employee

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

import java.time.LocalDateTime
import java.util.*


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
    @Comment("주민번호 암호화")
    @Column(name = "ENCRYPT_RESIDENT_NUMBER")
    @GenericGenerator(name = "keyGenerator", strategy = "net.dv.company.domain.ResidentNumberGenerator")
    @GeneratedValue(generator = "keyGenerator")
    var encryptResidentNumber: String? = null,

    @Comment("주민번호")
    @Column(name = "RESIDENT_NUMBER")
    var residentNumber: String? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("병원명")
    @Column(name = "HOSPITAL_NAME")
    var hospitalName: String? = null,

    @Comment("사원번호")
    @Column(name = "EMPLOYEE_CODE")
    var employeeCode: String? = null,

    @Comment("이름")
    @Column(name = "NAME")
    var name: String? = null,

    @Comment("경력 단절 여부")
    @Column(name = "CAREER_BREAK_YN")
    var careerBreakYn: String? = "N",

    @Comment("기능 및 자격")
    @Column(name = "SPEC")
    var spec: String? = null,

    @Comment("최종 학력")
    @Column(name = "ACADEMIC_HISTORY")
    var academicHistory: String? = null,

    @Comment("계약 기간")
    @Column(name = "CONTRACT_DURATION")
    var contractDuration:Int? = null,

    @Comment("고용 유형 기간제: /정규:/계약:/프리랜서: ")
    @Column(name = "EMPLOYMENT")
    var employment: String? = null,

    @Comment("세전/세후")
    @Column(name = "ANNUAL_TYPE")
    var annualType: String? = null,

    @Comment("연봉")
    @Column(name = "ANNUAL_INCOME")
    var annualIncome: String? = null,

    @Comment("직위")
    @Column(name = "POSITION")
    var position: String? = null,

    @Comment("입사일")
    @Column(name = "JOIN_AT")
    var joinAt: LocalDate? = null,

    @Comment("이메일")
    @Column(name = "EMAIL")
    var email: String? = null,

    @Comment("재직구분 재직:W/휴직:L/퇴사:R")
    @Column(name = "JOB_CLASS")
    var jobClass: String? = null,

    @Comment("사유")
    @Column(name = "REASON")
    var reason: String? = null,

    @Comment("입대날짜")
    @Column(name = "ENLISTMENT_AT")
    var enlistmentAt: LocalDate? = null,

    @Comment("전역날짜")
    @Column(name = "DISCHARGE_AT")
    var dischargeAt: LocalDate? = null,

    @Comment("근로계약갱신일")
    @Column(name = "WORK_RENEWAL_AT")
    var workRenewalAt: LocalDate? = null,

    @Comment("신청일")
    @Column(name = "CREATED_AT")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Comment("퇴직일")
    @Column(name = "RESIGNATION_AT")
    var resignationAt: LocalDate? = null,

    @Comment("퇴직사유")
    @Column(name = "RESIGNATION_CONTENTS")
    var resignationContents: String? = null,

    @Comment("휴대전화번호")
    @Column(name = "MOBILE_PHONE_NUMBER")
    var mobilePhoneNumber: String? = null,

    @Comment("직책")
    @Column(name = "OFFICE")
    var office: String? = null,

    @Comment("직무")
    @Column(name = "JOB")
    var job: String? = null,

    @Comment("경력연차")
    @Column(name = "CAREER_NUMBER")
    var careerNumber: String? = null,

    @Comment("부양가족 수")
    @Column(name = "DEPENDENT_CNT")
    var dependentCnt: String? = null,

    @Comment("주소")
    @Column(name = "ADDRESS")
    var address: String? = null,

    @Comment("승인일")
    @Column(name = "APPR_AT")
    var apprAt: LocalDateTime? = null,

    @Comment("첨부파일 여부")
    @Column(name = "ATTACH_FILE_YN")
    var attachFileYn: String? = "N",

    @Comment("최종수정일")
    @Column(name = "UPDATED_AT")
    var updatedAt: LocalDateTime? = null,

    @Comment("작성자 ID")
    @Column(name = "WRITER_ID")
    var writerId: String? = null,

    @Comment("작성자 명")
    @Column(name = "WRITER_NAME")
    var writerName: String? = null,
)

@Entity
@Comment("노무직원요청관리")
@Table(name = "EMPLOYEE_REQUEST")
@EntityListeners(AuditingEntityListener::class)
class EmployeeRequestEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("요청상태  대기:P/완료:C/완료 및 요청목록삭제:D")
    @Column(name = "REQUEST_STATE")
    var requestState: String? = null,

) : BaseEntity()

@Entity
@Comment("노무직원관리")
@Table(name = "EMPLOYEE")
@EntityListeners(AuditingEntityListener::class)
class EmployeeEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("엑셀 업로드시 등록된 파이 ㄹ경로")
    @Column(name = "FILE_PATH")
    var filePath: String? = null,

    @Comment("세율")
    @Column(name = "TAX_RATE")
    var taxRate: String? = null,

    @Comment("유저아이디")
    @Column(name = "ACCOUNT_ID")
    var accountId: String? = null,
): BaseEntity()

@Entity
@Comment("노무직원관리 이력")
@Table(name = "EMPLOYEE_HISTORY")
@EntityListeners(AuditingEntityListener::class)
class EmployeeHistoryEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("이력 생성일")
    @Column(name = "HISTORY_CREATED_AT")
    var historyCreatedAt: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    var employee: EmployeeEntity? = null,
) : BaseEntity()




@Entity
@Comment("노무직원관리파일")
@Table(name = "EMPLOYEE_ATTACH_FILE")
@EntityListeners(AuditingEntityListener::class)
class EmployeeAttachFileEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Comment("파일명")
    @Column(name = "FILE_NAME")
    var fileName: String? = null,

    @Comment("저장소 파일명")
    @Column(name = "LOCAL_FILE_NAME")
    var localFileName: String? = null,

    @Comment("저장소 파일 위치")
    @Column(name = "PATH")
    var path: String? = null,

    @Comment("파일 용량")
    @Column(name = "FILE_SIZE")
    var fileSize: Long? = null,

    @Comment("파일 확장자")
    @Column(name = "FILE_EXT")
    var fileExt: String? = null,

    @Comment("사용 여부")
    @Column(name = "USE_YN")
    var useYn: String? = null,

    @Comment("등록일")
    @Column(name = "CREATED_AT")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    val employee: EmployeeEntity? = null
)

@Entity
@Comment("노무직원급여관리")
@Table(name = "EMPLOYEE_SALARY_MNG")
@EntityListeners(AuditingEntityListener::class)
class EmployeeSalaryMngEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("병원 명")
    @Column(name = "HOSPITAL_NAME")
    var hospitalName: String? = null,

    @Comment("지급일")
    @Column(name = "PAYMENTS_AT")
    var paymentsAt: String? = null,

    @Comment("직원수")
    @Column(name = "EMPLOYEE_CNT")
    var employeeCnt: Long? = null,

    @Comment("등록일")
    @Column(name = "CREATED_AT")
    var createdAt: LocalDateTime? = null,

    @Comment("급여대장발급일시")
    @Column(name = "PAYROLL_CREATED_AT")
    var payrollCreatedAt: LocalDateTime? = null,

    @Comment("승인요청상태")
    @Column(name = "APPR_STATE")
    var apprState: String? = null,

    @Comment("확정처리상태")
    @Column(name = "FIXED_STATE")
    var fixedState: String? = null,

    @Comment("등록파일경로")
    @Column(name = "FILE_PATH")
    var filePath: String? = null,

)

@Entity
@Comment("노무직원급여")
@Table(name = "EMPLOYEE_SALARY")
@EntityListeners(AuditingEntityListener::class)
class EmployeeSalaryEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("사원번호")
    @Column(name = "EMPLOYEE_CODE")
    var employeeCode: String? = null,

    @Comment("이름")
    @Column(name = "NAME")
    var name: String? = null,

    @Comment("기본 급여")
    @Column(name = "BASIC_SALARY")
    var basicSalary: Long? = null,

    @Comment("총 급여")
    @Column(name = "TOTAL_SALARY")
    var totalSalary: Long? = null,

    @Comment("급여내역상세")
    @Column(name = "DETAIL_SALARY")
    var detailSalary: String? = null,

    @Comment("국민연금")
    @Column(name = "NATIONAL_PENSION")
    var nationalPension: Long? = null,

    @Comment("건강보험")
    @Column(name = "HEALTH_INSURANCE")
    var healthInsurance: Long? = null,

    @Comment("요양보험")
    @Column(name = "CARE_INSURANCE")
    var careInsurance: Long? = null,

    @Comment("고용보험")
    @Column(name = "UNEMPLOYEMENT_INSURANCE")
    var unemployementInsurance: Long? = null,

    @Comment("소득세")
    @Column(name = "INCOME_TAX")
    var incomeTax: Long? = null,

    @Comment("지방소득세")
    @Column(name = "LOCAL_INCOME_TAX")
    var localIncomeTax: Long? = null,

    @Comment("소득세 연말정산")
    @Column(name = "INCOME_TAX_YEAR_END")
    var incomeTaxYearEnd: Long? = null,

    @Comment("지소세연말정산")
    @Column(name = "LOCAL_INCOME_TAX_YEAR_END")
    var localIncomeTaxYearEnd: Long? = null,

    @Comment("차인지급합계")
    @Column(name = "ACTUAL_PAYMENT")
    var actualPayment: Long? = null,

    @Comment("지급일")
    @Column(name = "PAYMENTS_AT")
    var paymentsAt: String? = null,

    @Comment("등록일")
    @Column(name = "CREATED_AT")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Comment("관리 아아디")
    @ManyToOne
    @JoinColumn(name = "SALARY_MNG_ID")
    var employeeSalaryMng: EmployeeSalaryMngEntity? = null
)
