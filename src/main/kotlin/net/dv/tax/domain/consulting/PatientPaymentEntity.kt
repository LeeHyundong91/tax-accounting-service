package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Comment("매출명세서 환자결제분")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "sales_patient_payment")
@EntityListeners(AuditingEntityListener::class)
data class PatientPaymentEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val hospitalId: String? = null,

    @Comment("결산 년월")
    val resultYearMonth: String? = null,

    @Comment("신용카드 금액")
    val creditCardAmount: Long? = null,

    @Comment("신용카드 비율")
    val creditCardRatio: Float? = null,

    @Comment("현금영수증 금액")
    val cashReceiptAmount: Long? = null,

    @Comment("현금영수증 비율")
    val cashReceiptRatio: Float? = null,

    @Comment("환자 현금")
    val patientCashAmount: Long? = null,

    @Comment("환자 현금비율")
    val patientCashRatio: Float? = null,

    @Comment("합계 액")
    val totalAmount: Long? = null,

    @Comment("합계 비율")
    val totalRatio: Float? = null,

    )