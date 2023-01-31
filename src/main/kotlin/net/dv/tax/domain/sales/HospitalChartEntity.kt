package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "hospital_chart")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class HospitalChartEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: Int,

    @Comment("연도")
    var year: Int?,

    @Comment("월")
    var month: Int?,

    @Comment("진료비")
    var medicalExpenses: Int?,

    @Comment("급여총액")
    var totalSalary: Int?,

    @Comment("청구액")
    var billingAmount: Int?,

    @Comment("진료수납액")
    var medicalReceipts: Int?,

    @Comment("본인부담 금액")
    var ownExpense: Int?,

    @Comment("본인부담 비급여")
    var nonPayment: Int?,

    @Comment("본인부담 금액 합계")
    var ownExpenseAmount: Int?,

    @CreatedDate
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Comment("작성자")
    var writer: String?


)
