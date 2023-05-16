package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Comment("매출 누계 매출유형별 현황")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "sales_type")
@EntityListeners(AuditingEntityListener::class)
data class SalesTypeEntity (

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val hospitalId: String? = null,

    @Comment("결산 년월")
    val resultYearMonth: String? = null,

    @Comment("구분 - 청구매출, 요양급여, 의료급여 ...")
    val groupName: String? = null,

    @Comment("본인부담금")
    val groupOwnChargeAmount: Long? = null,

    @Comment("본인부담 비률")
    val groupOwnChargeRatio: Float? =null,

    @Comment("공단부담금")
    val groupCorpChargeAmount: Long? = null,

    @Comment("공단부담금 비율")
    val groupCorpChargeRatio: Float? = null,

    @Comment("소계")
    val groupAmount: Long? = null,

    @Comment("소계 비율")
    val groupRatio: Float? = null,

    @Comment("합계액")
    val totalAmount: Long? = null,

    @Comment("합계 비율")
    val totalRatio: Float? = null,

    @Comment("일반 = false / 청구 = true")
    val isClaim: Boolean? = false,


)