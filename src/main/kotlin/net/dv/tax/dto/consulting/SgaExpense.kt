package net.dv.tax.dto.consulting

interface SgaExpense {

    var valueAmount: Long
    var itemName: String

}

data class SgaExpenseDto(

    var valueAmount: Long? = 0,
    var itemName: String? = null,

    )
