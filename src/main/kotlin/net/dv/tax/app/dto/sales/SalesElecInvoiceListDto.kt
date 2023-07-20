package net.dv.tax.app.dto.sales

import org.hibernate.annotations.Comment

data class SalesElecInvoiceListDto(

    val salesElecInvoiceList: List<SalesElecInvoiceDto>,
    val totalList: SalesElecInvoiceDto? = null,

    )

data class SalesElecInvoiceDto(

    @Comment("작성일자")
    var writingDate: String? = null,

    @Comment("건수")
    var totalCount: Long? = 0,

    @Comment("공급대가")
    var supplyAmount: Long? = 0,

    )

data class SalesElecTaxInvoiceListDto(

    val salesElecTaxInvoiceList: List<SalesElecTaxInvoiceDto>,
    val totalList: SalesElecTaxInvoiceDto? = null,
)

data class SalesElecTaxInvoiceDto(
    @Comment("작성일자")
    var writingDate: String? = null,

    @Comment("건수")
    var totalCount: Long? = 0,

    @Comment("합계급액 - totaAmtStr")
    val totalAmount: Long? = null,

    @Comment("공급가액 - sumSplCftStr")
    val supplyAmount: Long? = null,

    @Comment("세액 - sumTxamtStr")
    val taxAmount: Long? = null,
)