package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("매출명세서 보험청구분")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "INSURANCE_CLAIM")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class InsuranceClaimEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var consultingReportId: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    @Comment("합계액")
    var totalAmount: Long? = null,

    @Comment("합계 비율")
    var totalRatio: Float? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 30)
    @JoinColumn(name = "INSURANCE_CLAIM_ID")
    var detailList: MutableList<InsuranceClaimItemEntity> = mutableListOf(),

    )

@Comment("매출명세서 보험청구분 항목")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "INSURANCE_CLAIM_ITEM")
@EntityListeners(AuditingEntityListener::class)
data class InsuranceClaimItemEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "INSURANCE_CLAIM_ID")
    val insuranceClaimId: Long? = null,

    @Comment("항목명")
    val itemName: String? = null,

    @Comment("합계액")
    var itemAmount: Long? = null,

    @Comment("합계 비율")
    var itemRatio: Float? = null,

    )

