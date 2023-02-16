package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.SalesCashReceiptListDto

interface CashReceiptSupport {
    fun groupingList(hospitalId: String, dataPeriod: String): List<SalesCashReceiptListDto>
}