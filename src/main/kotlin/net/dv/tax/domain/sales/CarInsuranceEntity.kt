package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("자동차보험 매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "car_insurance")
@EntityListeners(AuditingEntityListener::class)
data class CarInsuranceEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JsonProperty("carInsuranceId")
    val receiveDataId: Long,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String,

    @Comment("기간(년,월)")
    @JsonProperty("receptionDate")
    var dataPeriod: String,

    @Comment("청구액")
    val billingAmount: Long,

    @Comment("심사결정액")
    val decisionAmount: Long,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),


    )
