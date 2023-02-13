package net.dv.tax.dto.feign

import org.hibernate.annotations.Comment
import java.time.YearMonth

data class ResponseMedicalBenefitsDto(

    val medicalCardId: Long,

    @Comment("병원아이디")
    val hospitalId: String?,

    @Comment("진료년월")
    val receptionYearMonth: YearMonth?,

    @Comment("공단부담금")
    val corporationExpense: Long?,

    @Comment("본인부담금(지급결정[진료비명세서]) - retAJ")
    val ownExpense: Long?,

    @Comment("실지급 - retBG")
    val actualPayment: Long?,

    @Comment("접수금액(청구) - retG")
    val amountReceived: Long?,

    @Comment("지급일자(예정) - retI")
    val payday: String?,



    )