package net.dv.tax.infra.endpoint.purchase

import net.dv.tax.Application
import net.dv.tax.domain.purchase.PurchasePassbookEntity
import net.dv.tax.app.purchase.PurchasePassbookService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/${Application.VERSION}/purchase/passbook")
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