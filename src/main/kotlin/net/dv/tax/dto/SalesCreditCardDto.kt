package net.dv.tax.dto

import org.hibernate.annotations.Comment

data class SalesCreditCardDto (

    @Comment("기간")
    var dataPeriod: String,

    @Comment("결제건수")
    var billingCount: Long


)