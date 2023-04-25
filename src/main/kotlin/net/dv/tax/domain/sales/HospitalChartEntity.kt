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
@Comment("병원차트 매출목록")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class HospitalChartEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("병원 아이디")
    var hospitalId: String? = null,

    @Comment("연도")
    var year: Int? = 0,

    @Comment("월")
    var month: Int? = 0,

    @Comment("진료비")
    var medicalExpenses: Int? = 0,

    @Comment("급여총액")
    var totalSalary: Int? = 0,

    @Comment("청구액")
    var billingAmount: Int? = 0,

    @Comment("진료수납액")
    var medicalReceipts: Int? = 0,

    @Comment("본인부담 금액")
    var ownExpense: Int? = 0,

    @Comment("본인부담 비급여")
    var nonPayment: Int? = 0,

    @Comment("본인부담 금액 합계")
    var ownExpenseAmount: Int? = 0,

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false, name = "CREATED_AT")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Comment("작성자")
    var writer: String?


)
