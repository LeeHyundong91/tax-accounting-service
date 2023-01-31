package net.dv.tax.domain.sales

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Comment("요양급여매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "car_insurance")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class CarInsuranceEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: Int,

    @Comment("기간")
    var dataPeriod: LocalDate,

    @Comment("진료개시일")
    val treatmentStartDate : LocalDate,

    @Comment("처리상태")
    val processingStatus: String,

    @Comment("보험사명")
    val insuranceName: String,

    @Comment("환자명")
    val patientName: String,

    @Comment("환자생년월일")
    val patientBirthday: String,

    @Comment("청구액")
    val billingAmount: Long,

    @Comment("심사결정액")
    val decisionAmount: Long

)
