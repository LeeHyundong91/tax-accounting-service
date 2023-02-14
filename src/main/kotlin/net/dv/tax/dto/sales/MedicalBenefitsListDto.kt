package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

class MedicalBenefitsListDto(

    var totalAmount: Long? = 0,

    @Comment("진료년원")
    var dataPeriod: String? = null,

    @Comment("본인부담금")
    var ownExpense: Long? = 0,

    @Comment("공단부담금")
    var corporationExpense: Long? = 0,

    @Comment("접수액")
    var amountReceived: Long? = 0,

    @Comment("실지급액")
    var actualPayment: Long? = 0,

    )