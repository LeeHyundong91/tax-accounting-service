package net.dv.tax.infra.endpoint.sales

import net.dv.tax.domain.sales.SalesHandInvoiceEntity
import net.dv.tax.app.dto.sales.SalesHandInvoiceListDto
import net.dv.tax.app.sales.SalesHandInvoiceService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/sales/hand-invoice")
class SalesHandInvoiceController(private val salesHandInvoiceService: SalesHandInvoiceService) {


    @PutMapping("/save/{hospitalId}")
    fun saveList(
        @RequestBody dataList: List<SalesHandInvoiceEntity>,
        @PathVariable hospitalId: String,
    ): ResponseEntity<HttpStatus> {
        return salesHandInvoiceService.saveData(dataList, hospitalId)
    }


    @GetMapping("/{yearMonth}/{hospitalId}")
    fun getDetailList(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        @PageableDefault(size = 30) page: Pageable,
    ): SalesHandInvoiceListDto {
        return salesHandInvoiceService.getList(hospitalId, yearMonth, page)

    }

}