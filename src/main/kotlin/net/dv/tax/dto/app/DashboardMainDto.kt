package net.dv.tax.dto.app

data class DashboardMainDto (

    val currentSales: DashboardValue? = null,

    val expectCost: DashboardValue? = null,

    val expectIncome: DashboardValue? = null,

    val expectTax: DashboardValue? = null,

)

data class DashboardValue(

    val mainValueAmount: Long? = 0,

    val subValueAmount: Long? = 0,

    val compareValueAmount: Long? =0,


)