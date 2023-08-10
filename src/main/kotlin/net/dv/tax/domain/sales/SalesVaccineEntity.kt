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
@Table(name = "SALES_VACCINE")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesVaccineEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("지급완료 년월")
    @Column(name = "PAYMENT_YEAR_MONTH")
    val paymentYearMonth: String? = null,

    @Comment("지급완료 건수")
    @Column(name = "PAY_COUNT")
    var payCount: Long? = 0,

    @Comment("지급금액")
    @Column(name = "PAY_AMOUNT")
    var payAmount: Long? = 0,

    @Comment("작성자")
    @Column(name = "WRITER")
    var writer: String? = null,

    @CreatedDate
    @Column(name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
