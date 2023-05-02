package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

data class SalesCreditCardListDto(

    var salesCreditCardList: List<SalesCreditCardDto>,

    var listTotal: SalesCreditCardDto,

    )

data class SalesCreditCardDto(
    @Comment("승인년월")
    var approvalYearMonth: String? = null,

    @Comment("건수")
    var salesCount: Long? = 0,

    @Comment("매출합계")
    var totalSales: Long? = 0,

    @Comment("신용카드 결재")
    var creditCardSalesAmount: Long? = 0,

    @Comment("제로페이 결재")
    var zeroPaySalesAmount: Long? = 0,

    @Comment("비과세 금액")
    var taxFreeAmount: Long? = 0,

    )