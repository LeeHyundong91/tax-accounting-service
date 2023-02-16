package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
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
    @JsonIgnore
    val id: Long?,

    @Comment("병원 아이디")
    var hospitalId: String,

    @JsonProperty("medicalCardId")
    var receiveDataId: Long,

    @Comment("접수일자")
    @JsonProperty("receptionDate")
    val dataPeriod: String,

//    @Comment("접수일자")
//    val receptionDate: String,

    @Comment("접수금액")
    val receptionAmount: Long? = 0,

    @Comment("접수번호")
    val receptionNo: Long? = 0,

    @Comment("총의료급여비용")
    val medicalBenefitsAmount: Long? = 0,

    @Comment("건수")
    val benefitsCount: Long? = 0,

    @Comment("본인부담금" )
    val ownCharge: Long? = 0,

    @Comment("장애인의료비")
    val disabledExpenses: Long? = 0,

    @Comment("기관부담금")
    val agencyExpenses: Long? = 0,

    @Comment("절사금액")
    val cutOffAmount: Long? = 0,

    @Comment("기금부담금")
    val fundExpense: Long? = 0,

    @Comment("대불금")
    val proxyPayment: Long? = 0,

    @Comment("본인부담환급금")
    val refundPaid: Long? = 0,

    @Comment("검사기관지급액")
    val agencyPayment: Long? = 0,

    @Comment("당차수 실지급액")
    val actualPayment: Long? = 0,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )
