package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.Comment
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
data class SalesPaymentMethodEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    @Comment("신용카드 금액")
    var creditCardAmount: Long? = null,

    @Comment("신용카드 비율")
    var creditCardRatio: Float? = null,

    @Comment("현금영수증 금액")
    var cashReceiptAmount: Long? = null,

    @Comment("현금영수증 비율")
    var cashReceiptRatio: Float? = null,

    @Comment("판매대행 금액")
    var salesAgentAmount: Long? = null,

    @Comment("판매대행 비율")
    var salesAgentRatio: Float? = null,

    @Comment("공단부담금 ")
    var corpPayAmount: Long? = null,

    @Comment("공단부담 비율")
    var corpPayRatio: Float? = null,

    @Comment("보험사부담금")
    var insPayAmount: Long? = null,

    @Comment("보함사부담 비율")
    var insPayRatio: Float? = null,

    @Comment("실제순현금액")
    var actualCashAmount: Long? = null,

    @Comment("실제순현금비율")
    var actualCashRatio: Float? = null,

    @Comment("수정신고 금액")
    var revisedAmount: Long? = null,

    @Comment("수정신고 비율")
    var revisedRatio: Float? = null,

    @Comment("소계액")
    var smallSumAmount: Long? = null,

    @Comment("소계 비율")
    var smallSumRatio: Float? = null,

    @Comment("진료비 할인액")
    var discountAmount: Long? = null,

    @Comment("진료비 할인 비율")
    var discountRatio: Float? = null,

    @Comment("미수금")
    var receivableAmount: Long? = null,

    @Comment("미수비율")
    var receivableRatio: Float? = null,

    @Comment("합계 액")
    var totalAmount: Long? = null,

    @Comment("합계 비율")
    var totalRatio: Float? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )