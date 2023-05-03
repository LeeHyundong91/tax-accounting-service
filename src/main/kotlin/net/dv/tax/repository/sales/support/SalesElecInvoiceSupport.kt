package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.SalesElecInvoiceDto
import net.dv.tax.dto.sales.SalesElecTaxInvoiceDto

interface SalesElecInvoiceSupport {

    fun dataList(hospitalId: String, writingDate: String): List<SalesElecInvoiceDto>

    fun dataListTotal(hospitalId: String, writingDate: String): SalesElecInvoiceDto?

    fun taxDataList(hospitalId: String, writingDate: String): List<SalesElecTaxInvoiceDto>

    fun taxDataListTotal(hospitalId: String, writingDate: String): SalesElecTaxInvoiceDto?


}