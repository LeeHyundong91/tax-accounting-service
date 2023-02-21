package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.SalesHandInvoiceEntity
import net.dv.tax.service.sales.SalesHandInvoiceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/sales/hand-invoice")
class SalesHandInvoiceController(private val salesHandInvoiceService: SalesHandInvoiceService) {


    @PutMapping("/save/{hospitalId}")
    fun saveList(dataList: List<SalesHandInvoiceEntity>, @PathVariable hospitalId: String): ResponseEntity<HttpStatus> {
        return salesHandInvoiceService.saveData(dataList, hospitalId)
    }

    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(@PathVariable year: String, @PathVariable hospitalId: String): List<SalesHandInvoiceEntity> {
        return salesHandInvoiceService.getListData(hospitalId, year)
    }

}