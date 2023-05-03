package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.SalesCreditCardDto

interface SalesCreditCardSupport {
    fun dataList(hospitalId: String, year: String): List<SalesCreditCardDto>

    fun dataListTotal(hospitalId: String, year: String): SalesCreditCardDto?

}