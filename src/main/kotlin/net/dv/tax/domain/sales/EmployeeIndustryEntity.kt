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
    var hospitalId: String,

    var receiveDataId: Long,

    @Comment("진료년월")
    val treatmentYearMonth: String,

    @Comment("지급년월일")
    val payday: String,

    @Comment("접수번호")
    val receptionNo: Long,

    @Comment("급여종류")
    val salaryType: String,

    @Comment("청구금액")
    val billingAmount: Long,

    @Comment("지급금액")
    val paymentAmount: Long,

    @Comment("실지급금액")
    val actualPayment: Long,

    @Comment("소득세")
    val incomeTax: Long,

    @Comment("주민세")
    val residenceTax: Long,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )
