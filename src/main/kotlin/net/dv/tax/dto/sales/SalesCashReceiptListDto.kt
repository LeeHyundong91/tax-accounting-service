package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

data class SalesCashReceiptListDto(

    val salesCashReceiptList: List<SalesCashReceiptDto>,
    val listTotal: SalesCashReceiptDto? = null,

    )

data class SalesCashReceiptDto(
    @Comment("매출일시 - trsDtm")
    var salesDate: String? = null,

    @Comment("건수")
    var totalCount: Long? = 0,

    @Comment("공급가액 - splCft")
    var supplyAmount: Long? = 0,

    @Comment("부가세 - vaTxamt")
    var taxAmount: Long? = 0,

    @Comment("봉사료 - tip")
    var serviceFee: Long? = 0,

    @Comment("총금액 - totaTrsAmt")
    var totalAmount: Long? = 0,
)
