package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
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

    var hospitalId: String? = null,

    @Comment("진료년월")
    val treatmentYearMonth: String? = null,

    @Comment("급여총액")
    var totalSalary: Long? = 0,

    @Comment("청구액")
    var billingAmount: Long? = 0,

    @Comment("진료수납액")
    var medicalReceipts: Long? = 0,

    @Comment("본인부담금")
    var ownExpense: Long? = 0,

    @Comment("비급여")
    var nonPayment: Long? = 0,

    @Comment("기타")
    var etcAmount: Long = 0,

    @Comment("본인부담금 합계 - 본인부담금+비급여")
    var ownExpenseAmount: Long? = 0,

    @Comment("작성자")
    var writer: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),


    )
