package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
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
    val id: Int? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
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
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Comment("작성자")
    @JsonIgnore
    var writer: String?


)
