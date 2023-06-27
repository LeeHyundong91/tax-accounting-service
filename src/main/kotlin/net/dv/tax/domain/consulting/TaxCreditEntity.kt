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

@Comment("세액공제")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "tax_credit")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class TaxCreditEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var consultingReportId: Long? = null,

    var hospitalId: String? = null,

    var resultYearMonth: String? = null,

    var writer: String? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),


    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 30)
    @JoinColumn(name = "TAX_CREDIT_ID")
    var detailList: MutableList<TaxCreditItemEntity> = mutableListOf(),

    )

@Comment("세액공제 목록")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "tax_credit_item")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class TaxCreditItemEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "TAX_CREDIT_ID")
    val taxCreditId: Long? = null,

    var category: String? = null,

    var itemName: String? = null,

    var itemValue: Long? = 0,

    @Comment("2년차 공제액")
    var itemValue2: Long? = 0,

    @Comment("3년차 공제액")
    var itemValue3: Long? = 0,

    var activeCategory: String? = null,

    )

@Comment("세액공제 병원장 목록")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "tax_credit_personal")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class TaxCreditPersonalEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var consultingReportId: Long? = null,

    var resultYearMonth: String? = null,

    var hospitalId: String? = null,

    @Comment("병원장")
    var director: String? = null,

    @Comment("지분")
    var stake: Float? = 0.0.toFloat(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 30)
    @JoinColumn(name = "TAX_CREDIT_PERSONAL_ID")
    var detailList: MutableList<TaxCreditPersonalItemEntity> = mutableListOf(),

    )

@Comment("세액공제 개인 목록")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "tax_credit_personal_item")
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
data class TaxCreditPersonalItemEntity(
    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var category: String? = null,

    var itemName: String? = null,

    @Column(name = "TAX_CREDIT_PERSONAL_ID")
    var taxCreditPersonalId: Long? = null,

    @Comment("전년이월액")
    var lastYearAmount: Long? = 0,

    @Comment("당기발생액")
    var currentAccruals: Long = 0,

    @Comment("당기소멸액")
    var vanishingAmount: Long? = 0,

    @Comment("당기공제액")
    var currentDeduction: Long? = 0,

    @Comment("차기 이월액")
    var amountCarriedForward: Long? = 0,


    )

