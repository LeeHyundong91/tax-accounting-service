package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("매출 누계 매출유형별 현황 아이템")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "sales_type_item")
@EntityListeners(AuditingEntityListener::class)
data class SalesTypeItemEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "SALES_TYPE_ID")
    var salesTypeId: Long? = null,

    @Comment("구분 - 청구매출, 요양급여, 의료급여 ...")
    var itemName: String? = null,

    @Comment("본인부담금")
    var itemOwnChargeAmount: Long? = 0,

    @Comment("본인부담 비률")
    var itemOwnChargeRatio: Float? = 0.0.toFloat(),

    @Comment("공단부담금")
    var itemCorpChargeAmount: Long? = 0,

    @Comment("공단부담금 비율")
    var itemCorpChargeRatio: Float? = 0.0.toFloat(),

    @Comment("소계")
    var groupAmount: Long? = 0,

    @Comment("소계 비율")
    var groupRatio: Float? = 0.0.toFloat(),

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )
