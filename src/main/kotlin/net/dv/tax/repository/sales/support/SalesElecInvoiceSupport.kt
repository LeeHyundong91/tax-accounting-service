package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.SalesElecInvoiceListDto

interface SalesElecInvoiceSupport {
    fun groupingList(hospitalId: String, dataPeriod: String): List<SalesElecInvoiceListDto>
}