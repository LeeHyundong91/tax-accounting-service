package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Comment("건강검진매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "MEDICAL_EXAM")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class MedicalExamEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: Int,

    @Comment("청구일")
    val billingDate: Date,

    @Comment("청구번호")
    val billingNo: Int,

    @Comment("청구금액")
    val billingAmount: Int,

    @Comment("검진금액")
    val examAmount: Int,

    @Comment("지급금액")
    val paymentAmount: Int,

    @Comment("송금액")
    val remittance: Int,

    @Comment("조정금액")
    val adjustmentAmount: Int,

    @Comment("세액")
    val taxAmount: Int,

    @Comment("채권압류액")
    val bondAmount: Int,

    @Comment("환수액")
    val recoveredAmount: Int,

    @Comment("공제액")
    val deductibleAmount: Int,


    )
