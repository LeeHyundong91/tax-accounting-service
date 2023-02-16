package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.SalesCreditCardListDto

interface SalesCreditCardSupport {
    fun groupingList(hospitalId: String, dataPeriod: String): List<SalesCreditCardListDto>
}