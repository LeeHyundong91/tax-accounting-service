package net.dv.tax.app.dto.app

import org.hibernate.annotations.Comment

data class DashboardMainDto (

    @Comment("현재까지 집계 된 매출금액")
    val currentSales: DashboardValue? = DashboardValue(),

    @Comment("예상 비용")
    val expectCost: DashboardValue? = DashboardValue(),

    @Comment("예상 소득")
    val expectIncome: DashboardValue? = DashboardValue(),

    @Comment("예상 세액")
    val expectTax: DashboardValue? = DashboardValue(),

)

data class DashboardValue(

    @Comment("메인 금액")
    val mainValueAmount: Long? = 15406507,

    @Comment("전년 금액")
    val subValueAmount: Long? = 1970344,

    @Comment("차액")
    val compareValueAmount: Long? =123333,


)