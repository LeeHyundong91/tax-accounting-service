package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("과/면세비율 메인")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "tax_exemption")
@DynamicUpdate
data class TaxExemptionEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var consultingReportId: Long? = null,

    var hospitalId: String? = null,

    @Comment("결산 년월")
    var resultYearMonth: String? = null,

    @Comment("총합계")
    var totalAmount: Long? = 0,

    @Comment("과세합계")
    var taxAmount: Long? = 0,

    @Comment("면세합계")
    var exemptionAmount: Long? = 0,

    @Comment("과세비율")
    var taxRatio: Float? = 0.0.toFloat(),

    @Comment("작성자")
    var writer: String? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @BatchSize(size = 10)
    @JoinColumn(name = "TAX_EXEMPTION_ID")
    var detailList: MutableList<TaxExemptionItemEntity>? = mutableListOf(),

    )
