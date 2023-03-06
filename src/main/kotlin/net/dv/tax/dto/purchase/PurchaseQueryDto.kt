package net.dv.tax.dto.purchase

data class PurchaseQueryDto (

    val name: String? = null,

    val from: String? = null,

    val to: String? = null,

    val offset: Long? = 0,

    val size: Long? = 30,

    var deduction: Long? = null
)
