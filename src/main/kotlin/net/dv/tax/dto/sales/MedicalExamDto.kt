package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment
import java.time.LocalDate

data class MedicalExamDto(

    @Comment("기간")
    var dataPeriod: LocalDate,

    @Comment("청구건수")
    val billingCount: Long,

)
