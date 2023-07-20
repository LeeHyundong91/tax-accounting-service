package net.dv.tax.infra.endpoint.sales

import net.dv.tax.domain.sales.SalesElecInvoiceEntity
import net.dv.tax.app.dto.sales.SalesElecInvoiceListDto
import net.dv.tax.app.dto.sales.SalesElecTaxInvoiceListDto
import net.dv.tax.app.sales.SalesElecInvoiceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales")
class SalesElecInvoiceController(private val salesElecInvoiceService: SalesElecInvoiceService) {

    @GetMapping("/elec-invoice/list/{year}/{hospitalId}")
    fun getList(@PathVariable year: String, @PathVariable hospitalId: String): SalesElecInvoiceListDto {
        return salesElecInvoiceService.getList(hospitalId, year)
    }

    @GetMapping("/elec-invoice/{yearMonth}/{hospitalId}")
    fun getDetailList(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        @PageableDefault(size = 30) page: Pageable,
    ): Page<SalesElecInvoiceEntity> {
        return salesElecInvoiceService.getDetailList(hospitalId, yearMonth, page)
    }

    @GetMapping("/elec-tax-invoice/list/{year}/{hospitalId}")
    fun getTaxList(@PathVariable year: String, @PathVariable hospitalId: String): SalesElecTaxInvoiceListDto {
        return salesElecInvoiceService.getTaxList(hospitalId, year)
    }

    @GetMapping("/elec-tax-invoice/{yearMonth}/{hospitalId}")
    fun getTaxDetailList(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        @PageableDefault(size = 30) page: Pageable,
    ): Page<SalesElecInvoiceEntity> {
        return salesElecInvoiceService.getTaxDetailList(hospitalId, yearMonth, page)
    }

}