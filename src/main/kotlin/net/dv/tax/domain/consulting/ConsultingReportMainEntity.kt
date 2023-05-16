package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Comment("매출명세서 환자결제분")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "consulting_report_main")
@EntityListeners(AuditingEntityListener::class)
data class ConsultingReportMainEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val hospitalId: String? = null,

    val resultYearMonth: String? = null,

    @Comment("매출 아이디")
    val salesId: Long? = null,

    @Comment("매입 아이디")
    val purchasesId: Long? = null,

    @Comment("과/면세비율 아이디")
    val taxRateId: Long? = null,

    @Comment("연환산 손익계산서 아이디")
    val yearlyConsolidatedId: Long? = null,

    @Comment("결산조정비용 아이디")
    val adjustmentCostsId: Long? = null,

    @Comment("예상손익 아이디")
    val expectedProfitAndLossId: Long? = null,

    @Comment("세액공제내역 아이디")
    val taxDeductionId: Long? = null,

    @Comment("예상세액 아이디")
    val expectedTaxAmountId: Long? = null,
)
