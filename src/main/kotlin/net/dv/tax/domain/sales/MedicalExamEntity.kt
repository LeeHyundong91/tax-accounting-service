package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Comment("건강검진매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "medical_exam")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class MedicalExamEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String?,

    @Comment("청구일")
    val billingDate: Date?,

    @Comment("청구번호")
    val billingNo: Long,

    @Comment("청구금액")
    val billingAmount: Long,

    @Comment("검진금액")
    val examAmount: Long,

    @Comment("지급금액")
    val paymentAmount: Long,

    @Comment("송금액")
    val remittance: Long,

    @Comment("조정금액")
    val adjustmentAmount: Long,

    @Comment("세액")
    val taxAmount: Long,

    @Comment("채권압류액")
    val bondAmount: Long,

    @Comment("환수액")
    val recoveredAmount: Long,

    @Comment("공제액")
    val deductibleAmount: Long,


    )
