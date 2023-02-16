package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.SalesElecInvoiceEntity
import net.dv.tax.dto.sales.SalesElecInvoiceListDto
import net.dv.tax.service.sales.SalesElecInvoiceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/elec-invoice")
class SalesElecInvoiceController(private val salesElecInvoiceService: SalesElecInvoiceService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(@PathVariable year: String, @PathVariable hospitalId: String): List<SalesElecInvoiceListDto>{
        return salesElecInvoiceService.getListData(hospitalId, year)
    }

    @GetMapping("/{yearMonth}/{hospitalId}")
    fun getDetail(@PathVariable yearMonth: String, @PathVariable hospitalId: String): List<SalesElecInvoiceEntity>{
        return salesElecInvoiceService.getDetailData(hospitalId, yearMonth)
    }

}