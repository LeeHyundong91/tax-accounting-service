package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

class CarInsuranceListDto(

    @Comment("기간(년,월)")
    var dataPeriod: String? = null,

    @Comment("청구액")
    var billingAmount: Long? = 0,

    @Comment("심사결정액")
    var decisionAmount: Long? = 0,
)