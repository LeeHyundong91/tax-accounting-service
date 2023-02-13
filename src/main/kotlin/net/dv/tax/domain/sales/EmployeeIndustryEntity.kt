package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Comment("고용산재매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "EMPLOYEE_INDUSTRY")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class EmployeeIndustryEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("청구일 - 안써!!")
    val billingDate: LocalDate,

    @Comment("진료일")
    val medicalDay: LocalDate,

    @Comment("지금일")
    val payday: LocalDate,

    @Comment("청구번호")
    val billingNo: Long,

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


    )
