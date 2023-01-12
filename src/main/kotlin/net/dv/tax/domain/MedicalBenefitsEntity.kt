package net.dv.tax.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Comment("요양급여매출관리")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "medical_benefits")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class MedicalBenefitsEntity(


    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Comment("기간")
    var dataPeriod: LocalDate,

    @Comment("본인부담금")
    val ownExpense: Int?,

    @Comment("공단부담금")
    val corporationExpense: Int,

    @Comment("접수일")
    val receptionDate: String,

    @Comment("지금(예정)일")
    val payday: String,

    @Comment("접수액")
    val amountReceived: Int,

    @Comment("실지급액")
    val actualPayment: Int


)
