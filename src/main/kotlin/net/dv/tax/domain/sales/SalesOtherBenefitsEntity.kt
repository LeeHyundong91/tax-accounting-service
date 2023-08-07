package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "sales_other_benefits")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
@Comment("기타급여 매출")
data class SalesOtherBenefitsEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("기간 yyyy-MM")
    @Column(name = "DATA_PERIOD")
    var dataPeriod: String? = null,

    @Comment("항목명")
    @Column(name = "ITEM_NAME")
    var itemName: String? = null,

    @Comment("본인부담금")
    @Column(name = "OWN_CHARGE")
    var ownCharge: Long? = 0,

    @Comment("기관부담금")
    @Column(name = "AGENCY_EXPENSE")
    val agencyExpense: Long? = 0,

    @Comment("합계")
    @Column(name = "TOTAL_AMOUNT")
    val totalAmount: Long? = 0,

    @Comment("삭제여부")
    @Column(name = "IS_DELETE")
    val isDelete: Boolean? = false,

    @Column(name = "WIRTER")
    var writer: String? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)