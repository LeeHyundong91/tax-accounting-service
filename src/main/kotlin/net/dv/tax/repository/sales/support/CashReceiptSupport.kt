package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.SalesCashReceiptDto

interface CashReceiptSupport {

    fun dataList(hospitalId: String, yearMonth: String): List<SalesCashReceiptDto>

    fun dataListTotal(hospitalId: String, yearMonth: String): SalesCashReceiptDto?

}