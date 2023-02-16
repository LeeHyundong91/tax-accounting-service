package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.dto.sales.SalesCashReceiptListDto
import net.dv.tax.service.sales.SalesCashReceiptService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/cash-receipt")
class SalesCashReceiptController(private val salesCashReceiptService: SalesCashReceiptService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(@PathVariable year: String, @PathVariable hospitalId: String): List<SalesCashReceiptListDto>{
        return salesCashReceiptService.getListData(hospitalId, year)
    }

    @GetMapping("/{yearMonth}/{hospitalId}")
    fun getDetail(@PathVariable yearMonth: String, @PathVariable hospitalId: String): List<SalesCashReceiptEntity>{
        return salesCashReceiptService.getDetailData(hospitalId, yearMonth)
    }

}