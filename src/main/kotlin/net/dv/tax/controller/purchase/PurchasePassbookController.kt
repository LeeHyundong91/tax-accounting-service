package net.dv.tax.controller.purchase

import net.dv.tax.TaxAccountingApplication
import net.dv.tax.domain.purchase.PurchasePassbookEntity
import net.dv.tax.service.purchase.PurchasePassbookService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/${TaxAccountingApplication.VERSION}/purchase/passbook")
class PurchasePassbookController(private val passbookService: PurchasePassbookService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @PageableDefault(size = 30) page: Pageable,
    ): List<PurchasePassbookEntity> {
        return passbookService.getPassbookList(hospitalId, year, page)
    }



}