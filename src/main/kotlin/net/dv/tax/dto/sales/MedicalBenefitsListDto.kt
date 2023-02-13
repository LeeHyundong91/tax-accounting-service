package net.dv.tax.dto.sales

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import org.hibernate.annotations.Comment

data class MedicalBenefitsListDto(

    @Column(name = "RECEIVE_DATA_ID")
    val medicalBenefitsId: Long?,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
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

    )