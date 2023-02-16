package net.dv.tax.dto.sales

import org.hibernate.annotations.Comment

class SalesCreditCardListDto{

    @Comment("승인년월")
    var dataPeriod: String? = null

    @Comment("자료구분")
    var cardCategory: String? = null

    @Comment("건수")
    var salesCount: Long? = 0

    @Comment("매출합계")
    var totalSales: Long? = 0

    @Comment("신용카드 결재")
    var creditCardSalesAmount: Long? = 0

    @Comment("구매전용카드 결재")
    var purchaseCardSalesAmount: Long? = 0

    @Comment("비과세 금액")
    var taxFreeAmount: Long? = 0


    }