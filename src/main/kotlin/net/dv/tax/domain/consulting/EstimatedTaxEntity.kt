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

@Comment("예상세액")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "ESTIMATED_TAX")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class EstimatedTaxEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var consultingReportId: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    var writer: String? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 30)
    @JoinColumn(name = "ESTIMATED_TAX_ID")
    var detailList: MutableList<EstimatedTaxPersonalEntity> = mutableListOf(),

    )


@Comment("예상세액 병원장")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "ESTIMATED_TAX_PERSONAL")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class EstimatedTaxPersonalEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "ESTIMATED_TAX_ID")
    val estimatedTaxId: Long? = null,

    var hospitalId: String? = null,

    @Comment("병원장")
    var director: String? = null,

    @Comment("병원장 아이디")
    var directorId: String? = null,

    @Comment("지분")
    var stake: Float? = 0.0.toFloat(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 30)
    @JoinColumn(name = "ESTIMATED_TAX_PERSONAL_ID")
    var detailList: MutableList<EstimatedTaxItemEntity> = mutableListOf(),


    )

@Comment("예상세액 항목")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "ESTIMATED_TAX_ITEM")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class EstimatedTaxItemEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var category: String? = null,

    var itemName: String? = null,

    @Column(name = "ESTIMATED_TAX_PERSONAL_ID")
    var estimatedTaxPersonalId: Long? = null,

    @Comment("전년이월액")
    var itemValue: Long? = 0,

    )
