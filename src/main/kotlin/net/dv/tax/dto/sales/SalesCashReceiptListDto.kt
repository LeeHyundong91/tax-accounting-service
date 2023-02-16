package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

class SalesCashReceiptListDto{

    var dataPeriod: String? = null

    @Comment("합계")
    var totalAmount: Long? = null

    @Comment("공급가액")
    var supplyPrice: Long? = null

    @Comment("부가세")
    var vat: Long? = null

    @Comment("봉사료")
    var serviceCharge: Long? = null
    
    var count: Long? = null

    }
