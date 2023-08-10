package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "HOSPITAL_CHART")
@Comment("병원차트 매출목록")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class HospitalChartEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("진료년월")
    @Column(name = "TREATMENT_YEAR_MONTH")
    val treatmentYearMonth: String? = null,

    @Comment("급여총액")
    @Column(name = "TOTAL_SALARY")
    var totalSalary: Long? = 0,

    @Comment("청구액")
    @Column(name = "BILLING_AMOUNT")
    var billingAmount: Long? = 0,

    @Comment("진료수납액")
    @Column(name = "MEDICAL_RECEIPTS")
    var medicalReceipts: Long? = 0,

    @Comment("본인부담금")
    @Column(name = "OWN_EXPENSE")
    var ownExpense: Long? = 0,

    @Comment("비급여")
    @Column(name = "NON_PAYMENT")
    var nonPayment: Long? = 0,

    @Comment("기타")
    @Column(name = "ECT_AMOUNT")
    var etcAmount: Long = 0,

    @Comment("본인부담금 합계 - 본인부담금+비급여")
    @Column(name = "OWN_EXPENSE_AMOUNT")
    var ownExpenseAmount: Long? = 0,

    @Comment("작성자")
    @Column(name = "WRITER")
    var writer: String? = null,

    @Column(name = "CREATED_AT")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
