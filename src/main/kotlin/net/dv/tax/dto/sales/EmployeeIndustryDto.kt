package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment
import java.util.*

data class EmployeeIndustryDto(

    @Comment("기간")
    val dataPeriod: Date,

    @Comment("청구건수")
    val billingCount: Long,



)
