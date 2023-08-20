package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.*


@Entity
@Comment("신용카드매출관리")
@Suppress("JpaAttributeTypeInspection")
@Table(name = "SALES_CREDIT_CARD")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesCreditCardEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "RECEIVE_DATA_ID")
    val receiveDataId: Long,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("승인년월")
    @Column(name = "APPROVAL_YEAR_MONTH")
    val approvalYearMonth: String? = null,

    @Comment("자료구분")
    @Column(name = "CARD_CATEGORY")
    val cardCategory: String? = null,

    @Comment("건수")
    @Column(name = "SALES_COUNT")
    val salesCount: Long? = 0,

    @Comment("매출합계")
    @Column(name = "TOTAL_SALES")
    val totalSales: Long? = 0,

    @Comment("신용카드 결재")
    @Column(name = "CREDIT_CARD_SALES_AMOUNT")
    val creditCardSalesAmount: Long? = 0,

    @Comment("제로페이 결재")
    @Column(name = "ZERO_PAY_SALES_AMOUNT")
    val zeroPaySalesAmount: Long? = 0,

    @Comment("비과세 금액")
    @Column(name = "TAX_FREE_AMOUNT")
    val taxFreeAmount: Long? = 0,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)


