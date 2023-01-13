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
    val id: Int,

    @Comment("청구일")
    val billingDate: LocalDate,

    @Comment("진료일")
    val medicalDay: LocalDate,

    @Comment("지금일")
    val payday: LocalDate,

    @Comment("청구번호")
    val billingNo: Int,

    @Comment("급여종류")
    val salaryType: String,

    @Comment("청구금액")
    val billingAmount: Int,

    @Comment("지급금액")
    val paymentAmount: Int,

    @Comment("실지급금액")
    val actualPayment: Int,

    @Comment("소득세")
    val incomeTax: Int,

    @Comment("주민세")
    val residenceTax: Int,


    )
