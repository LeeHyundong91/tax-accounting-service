package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

class SalesElecInvoiceListDto {


    var count: Long? = 0

    @Comment("기간")
    var dataPeriod: String? = null

    @Comment("공급가액")
    var supplyPrice: Long? = 0

    @Comment("세액")
    var taxAmount: Long? = 0

    @Comment("합계금액")
    var totalAmount: Long? = 0
}