package net.dv.tax.controller.purchase

import net.dv.tax.TaxAccountingApplication
import net.dv.tax.domain.purchase.PurchaseHandwrittenEntity
import net.dv.tax.service.purchase.PurchaseHandwrittenService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/${TaxAccountingApplication.VERSION}/purchase/handwritten")
class PurchaseHandwrittenController(private val purchaseHandwrittenService: PurchaseHandwrittenService) {

    @PutMapping("/save/{hospitalId}")
    fun saveList(
        @RequestBody dataList: List<PurchaseHandwrittenEntity>,
        @PathVariable hospitalId: String,
    ): ResponseEntity<HttpStatus> {

        return purchaseHandwrittenService.dataSave(dataList)
    }

    @GetMapping("/list/{hospitalId}/{year}")
    fun dataList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @PageableDefault(size = 30) page: Pageable,
    ): List<PurchaseHandwrittenEntity> {

        return purchaseHandwrittenService.dataList(hospitalId, year, page)
    }

}