package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("고용산재매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "employee_industry")
@EntityListeners(AuditingEntityListener::class)
data class EmployeeIndustryEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id: Long,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Column(name = "RECEIVE_DATA_ID")
    var receiveDataId: Long,

    @Comment("진료년월")
    @Column(name = "TREATMENT_YEAR_MONTH")
    val treatmentYearMonth: String,

    @Comment("지급년월일")
    @Column(name = "PAYDAY")
    val payday: String,

    @Comment("접수번호")
    @Column(name = "RECEPTION_NO")
    val receptionNo: Long,

    @Comment("급여종류")
    @Column(name = "SALARY_TYPE")
    val salaryType: String,

    @Comment("청구금액")
    @Column(name = "BILLING_AMOUNT")
    val billingAmount: Long,

    @Comment("지급금액")
    @Column(name = "PAYMENT_AMOUNT")
    val paymentAmount: Long,

    @Comment("실지급금액")
    @Column(name = "ACTUAL_PAYMENT")
    val actualPayment: Long,

    @Comment("소득세")
    @Column(name = "INCOME_TAX")
    val incomeTax: Long,

    @Comment("주민세")
    @Column(name = "RESIDENCE_TAX")
    val residenceTax: Long,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
