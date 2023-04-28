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

    val treatmentYearMonth: String? = null,

    var totalSalary: Int? = 0,

    var billingAmount: Int? = 0,

    var medicalReceipts: Int? = 0,

    var ownExpense: Int? = 0,

    var nonPayment: Int? = 0,

    var etcAmount: Long = 0,

    var ownExpenseAmount: Int? = 0,

    var writer: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),


    )
