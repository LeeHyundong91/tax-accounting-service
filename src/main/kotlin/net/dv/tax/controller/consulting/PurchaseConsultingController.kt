package net.dv.tax.controller.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.PurchaseReportItemEntity
import net.dv.tax.repository.consulting.PurchaseReportRepository
import net.dv.tax.service.consulting.PurchaseReportService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/purchase")
class PurchaseConsultingController(
    private val purchaseReportService: PurchaseReportService,
    private val purchaseReportRepository: PurchaseReportRepository,
) {

    private val log = KotlinLogging.logger {}


    @PutMapping("/item/save")
    fun purchaseItemUpdate(@RequestBody itemList: MutableList<PurchaseReportItemEntity>)
            : ResponseEntity<HttpStatus> {
        return purchaseReportService.saveItemData(itemList)
    }

    @GetMapping("/{year}/{hospitalId}")
    fun test(@PathVariable hospitalId: String, @PathVariable year: String) {
        purchaseReportService.makeData(hospitalId, year)

    }


}