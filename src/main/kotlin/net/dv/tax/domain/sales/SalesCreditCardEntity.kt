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
@Table(name = "sales_credit_card")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesCreditCardEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val receiveDataId: Long,

    @Comment("병원 아이디")
    var hospitalId: String,

    @Comment("승인년월")
    val approvalYearMonth: String? = null,

    @Comment("자료구분")
    val cardCategory: String? = null,

    @Comment("건수")
    val salesCount: Long? = 0,

    @Comment("매출합계")
    val totalSales: Long? = 0,

    @Comment("신용카드 결재")
    val creditCardSalesAmount: Long? = 0,

    @Comment("제로페이 결재")
    val zeroPaySalesAmount: Long? = 0,

    @Comment("비과세 금액")
    val taxFreeAmount: Long? = 0,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )


