package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("매출누계 결제수단별 현황")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "sales_payment_method")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesPaymentMethodEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    @Comment("신용카드 금액")
    var creditCardAmount:  Long? = 0,

    @Comment("신용카드 비율")
    var creditCardRatio: Float? = 0.0.toFloat(),

    @Comment("현금영수증 금액")
    var cashReceiptAmount:  Long? = 0,

    @Comment("현금영수증 비율")
    var cashReceiptRatio: Float? = 0.0.toFloat(),

    @Comment("판매대행 금액")
    var salesAgentAmount:  Long? = 0,

    @Comment("판매대행 비율")
    var salesAgentRatio: Float? = 0.0.toFloat(),

    @Comment("공단부담금 ")
    var corpPayAmount:  Long? = 0,

    @Comment("공단부담 비율")
    var corpPayRatio: Float? = 0.0.toFloat(),

    @Comment("보험사부담금")
    var insPayAmount:  Long? = 0,

    @Comment("보함사부담 비율")
    var insPayRatio: Float? = 0.0.toFloat(),

    @Comment("실제순현금액")
    var actualCashAmount:  Long? = 0,

    @Comment("실제순현금비율")
    var actualCashRatio: Float? = 0.0.toFloat(),

    @Comment("수정신고 금액")
    var revisedAmount:  Long? = 0,

    @Comment("수정신고 비율")
    var revisedRatio: Float? = 0.0.toFloat(),

    @Comment("소계액")
    var smallSumAmount:  Long? = 0,

    @Comment("소계 비율")
    var smallSumRatio: Float? = 0.0.toFloat(),

    @Comment("진료비 할인액")
    var discountAmount:  Long? = 0,

    @Comment("진료비 할인 비율")
    var discountRatio: Float? = 0.0.toFloat(),

    @Comment("미수금")
    var receivableAmount:  Long? = 0,

    @Comment("미수비율")
    var receivableRatio: Float? = 0.0.toFloat(),

    @Comment("합계 액")
    var totalAmount:  Long? = 0,

    @Comment("합계 비율")
    var totalRatio: Float? = 0.0.toFloat(),

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )