package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Comment("매출 누계 매출유형별 현황")
@Suppress("JpaAttributeTypeInspection")
@BatchSize(size = 10)
@Entity
@Table(name = "sales_type")
@EntityListeners(AuditingEntityListener::class)
data class SalesTypeEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    @Comment("합계액")
    var totalAmount: Long? = null,

    @Comment("합계 비율")
    var totalRatio: Float? = null,

    @Comment("일반 = false / 청구 = true")
    var isClaim: Boolean? = false,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @BatchSize(size = 10)
    @JoinColumn(name = "SALES_TYPE_ID")
    var detailList: MutableList<SalesTypeItemEntity>? = mutableListOf(),


    )
//
//금연치료 / 소견서 / 희귀 / 기타