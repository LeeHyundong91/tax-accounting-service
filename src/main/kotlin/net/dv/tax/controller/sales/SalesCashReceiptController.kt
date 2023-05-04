package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.dto.sales.SalesCashReceiptListDto
import net.dv.tax.service.sales.SalesCashReceiptService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/cash-receipt")
class SalesCashReceiptController(private val salesCashReceiptService: SalesCashReceiptService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
    ): SalesCashReceiptListDto {
        return salesCashReceiptService.getList(hospitalId, year)
    }

    @GetMapping("/{yearMonth}/{hospitalId}")
    fun getDetailList(
        @PathVariable hospitalId: String,
        @PathVariable yearMonth: String,
        @PageableDefault(size = 30) page: Pageable,
    ): Page<SalesCashReceiptEntity> {
        return salesCashReceiptService.getDetailList(hospitalId, yearMonth, page)

    }

}