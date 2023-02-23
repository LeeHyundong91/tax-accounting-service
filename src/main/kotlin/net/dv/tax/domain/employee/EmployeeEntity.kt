package net.dv.tax.domain.employee

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import java.time.LocalDateTime

@Entity
@Comment("노무직원관리")
@Table(name = "EMPLOYEE")
@EntityListeners(AuditingEntityListener::class)
class EmployeeEntity(
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Comment("주민번호 암호화")
    @Column(name = "ENCRYPT_RESIDENT_NUMBER")
    val encryptResidentNumber: String? = null,

    @Comment("주민번호")
    @Column(name = "RESIDENT_NUMBER")
    var residentNumber: String? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("병원명")
    @Column(name = "HOSPITAL_NAME")
    var hospitalName: String,

    @Comment("이름")
    @Column(name = "NAME")
    var name: String,

    @Comment("고용 유형 기간제: /정규:/계약:/프리랜서: ")
    @Column(name = "EMPLOYMENT_TYPE")
    var employmentType: String,

    @Comment("세전/세후")
    @Column(name = "ANNUAL_TYPE")
    var annualType: String,

    @Comment("연봉")
    @Column(name = "ANNUAL_INCOME")
    var annualIncome: String,

    @Comment("직위")
    @Column(name = "POSITION")
    var position: String,

    @Comment("입사일")
    @Column(name = "JOIN_AT")
    var joinAt: LocalDateTime,

    @Comment("이메일")
    @Column(name = "EMAIL")
    var email: String,

    @Comment("재직구분 재직:W/휴직:L/퇴사:R")
    @Column(name = "JOB_CLASS")
    var jobClass: String,

    @Comment("사유")
    @Column(name = "REASON")
    var reason: String,

    @Comment("신청일")
    @Column(name = "CREATED_AT")
    var createdAt: LocalDateTime,

    @Comment("요청상태  대기:P/완료:C/완료 및 요청목록삭제:D")
    @Column(name = "REQUEST_STATE")
    var requestState: String,

    @Comment("퇴직일")
    @Column(name = "RESIGNATION_AT")
    var resignationAt: String,

    @Comment("퇴직사유")
    @Column(name = "RESIGNATION_CONTENTS")
    var resignationContents: String,

    @Comment("휴대전화번호")
    @Column(name = "MOBILE_PHONE_NUMBER")
    var mobilePhoneNumber: String,

    @Comment("직책")
    @Column(name = "OFFICE")
    var office: String,

    @Comment("직무")
    @Column(name = "JOB")
    var job: Long,

    @Comment("직무상세")
    @Column(name = "JOB_DETAIL")
    var jobDetail: Long,

    @Comment("경력연차")
    @Column(name = "CAREER_NUMBER")
    var careerNumber: String,

    @Comment("부양가족 수")
    @Column(name = "DEPENDENT_CNT")
    var dependentCnt: String,

    @Comment("주소")
    @Column(name = "ADDRESS")
    var address: String,

    @Comment("승인일")
    @Column(name = "APPR_AT")
    var apprAt: LocalDateTime? = null,

    @Comment("첨부파일 여부")
    @Column(name = "ATTACH_FILE_YN")
    var attachFileYn: String,

    @Comment("최종수정일")
    @Column(name = "UPDATED_AT")
    var updatedAt: LocalDateTime? = null,

    )

@Entity
@Comment("노무직원관리 이력")
@Table(name = "EMPLOYEE_HISTORY")
@EntityListeners(AuditingEntityListener::class)
class EmployeeHistoryEntity(
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Comment("주민번호 암호화")
    @Column(name = "ENCRYPT_RESIDENT_NUMBER")
    val encryptResidentNumber: String? = null,

    @Comment("주민번호")
    @Column(name = "RESIDENT_NUMBER")
    var residentNumber: String? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("병원명")
    @Column(name = "HOSPITAL_NAME")
    var hospitalName: String,

    @Comment("이름")
    @Column(name = "NAME")
    var name: String,

    @Comment("고용 유형 기간제: /정규:/계약:/프리랜서: ")
    @Column(name = "EMPLOYMENT_TYPE")
    var employmentType: String,

    @Comment("세전/세후")
    @Column(name = "ANNUAL_TYPE")
    var annualType: String,

    @Comment("연봉")
    @Column(name = "ANNUAL_INCOME")
    var annualIncome: String,

    @Comment("직위")
    @Column(name = "POSITION")
    var position: String,

    @Comment("입사일")
    @Column(name = "JOIN_AT")
    var joinAt: LocalDateTime,

    @Comment("이메일")
    @Column(name = "EMAIL")
    var email: String,

    @Comment("재직구분 재직:W/휴직:L/퇴사:R")
    @Column(name = "JOB_CLASS")
    var jobClass: String,

    @Comment("사유")
    @Column(name = "REASON")
    var reason: String,

    @Comment("신청일")
    @Column(name = "CREATED_AT")
    var createdAt: LocalDateTime,

    @Comment("요청상태  대기:P/완료:C/완료 및 요청목록삭제:D")
    @Column(name = "REQUEST_STATE")
    var requestState: String,

    @Comment("퇴직일")
    @Column(name = "RESIGNATION_AT")
    var resignationAt: String,

    @Comment("퇴직사유")
    @Column(name = "RESIGNATION_CONTENTS")
    var resignationContents: String,

    @Comment("휴대전화번호")
    @Column(name = "MOBILE_PHONE_NUMBER")
    var mobilePhoneNumber: String,

    @Comment("직책")
    @Column(name = "OFFICE")
    var office: String,

    @Comment("직무")
    @Column(name = "JOB")
    var job: Long,

    @Comment("직무상세")
    @Column(name = "JOB_DETAIL")
    var jobDetail: Long,

    @Comment("경력연차")
    @Column(name = "CAREER_NUMBER")
    var careerNumber: String,

    @Comment("부양가족 수")
    @Column(name = "DEPENDENT_CNT")
    var dependentCnt: String,

    @Comment("주소")
    @Column(name = "ADDRESS")
    var address: String,

    @Comment("승인일")
    @Column(name = "APPR_AT")
    var apprAt: LocalDateTime? = null,

    @Comment("첨부파일 여부")
    @Column(name = "ATTACH_FILE_YN")
    var attachFileYn: String,

    @Comment("최종수정일")
    @Column(name = "UPDATED_AT")
    var updatedAt: LocalDateTime? = null,

    @Comment("이력 생성일")
    @Column(name = "HISTORY_CREATED_AT")
    var historyCreatedAt: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    val employeeEntity: EmployeeEntity? = null

)

@Entity
@Comment("노무직원급여관리")
@Table(name = "EMPLOYEE_SALARY")
@EntityListeners(AuditingEntityListener::class)
class employeeSalaryEntity(
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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
    var paymentsAt: LocalDateTime? = null,

    @Comment("등록일")
    @Column(name = "CREATED_AT")
    var createdAt: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    val employeeEntity: EmployeeEntity? = null
)


@Entity
@Comment("노무직원관리파일")
@Table(name = "EMPLOYEE_ATTACH_FILE")
@EntityListeners(AuditingEntityListener::class)
class employeeAttachFileEntity(
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Comment("기본 급여")
    @Column(name = "BASIC_SALARY")
    var basicSalary: Long? = null,

    @Comment("파일명")
    @Column(name = "FILE_NAME")
    var fileName: String? = null,

    @Comment("저장소 파일명")
    @Column(name = "LOCAL_FILE_NAME")
    var localFileName: String? = null,

    @Comment("저장소 파일 위치")
    @Column(name = "PATH")
    var path: Long? = null,

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
    var createdAt: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    val employeeEntity: EmployeeEntity? = null
)


