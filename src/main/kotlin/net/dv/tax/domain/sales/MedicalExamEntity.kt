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
    val id: Long?,

    @Comment("병원 아이디 - cid")
    var hospitalId: String,

    @JsonProperty("medicalCardId")
    var receiveDataId: Long,

    @Comment("접수년월")
    @JsonProperty("receptionYearMonth")
    val dataPeriod: String,

    @Comment("접수일자")
    val receptionDate: String,

    @Comment("접수금액 - retF")
    val receptionAmount: Long? = 0,

    @Comment("접수번호")
    val receptionNo: Long? = 0,

    @Comment("총의료급여비용(의료급여비용 심사결정 내역) - retAJ")
    val medicalBenefitsAmount: Long? = 0,

    @Comment("건수(의료급여비용 심사결정 내역) - retAK")
    val benefitsCount: Long? = 0,

    @Comment("본인부담금(의료급여비용 심사결정 내역)- retAL" )
    val ownCharge: Long? = 0,

    @Comment("장애인의료비(의료급여비용 심사결정 내역) - retAM")
    val disabledExpenses: Long? = 0,

    @Comment("기관부담금(의료급여비용 심사결정 내역) - retAN")
    val agencyExpenses: Long? = 0,

    @Comment("절사금액(의료급여비용 심사결정 내역) - retAO")
    val cutOffAmount: Long? = 0,

    @Comment("기금부담금(지급결정) - retAP")
    val fundExpense: Long? = 0,

    @Comment("대불금(지급결정) - retAQ")
    val proxyPayment: Long? = 0,

    @Comment("본인부담환급금(지급결정) - retAR")
    val refundPaid: Long? = 0,

    @Comment("검사기관지급액(지급결정) - retAS")
    val agencyPayment: Long? = 0,

    @Comment("당차수 실지급액 - retAV")
    val actualPayment: Long? = 0,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )
