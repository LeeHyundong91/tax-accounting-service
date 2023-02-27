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

@Comment("요양급여매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "medical_benefits")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class MedicalBenefitsEntity(


    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id: Long,

    @JsonProperty("medicalBenefitsId")
    val receiveDataId : Long?,

    @Comment("병원 아이디")
    var hospitalId: String?,

    @Comment("진료년원")
    @JsonProperty("receptionYearMonth")
    var dataPeriod: String?,

    @Comment("본인부담금")
    val ownExpense: Long?,

    @Comment("공단부담금")
    val corporationExpense: Long?,

    @Comment("지금(예정)일")
    val payday: String?,

    @Comment("접수액")
    val amountReceived: Long?,

    @Comment("실지급액")
    val actualPayment: Long?,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),



    )
