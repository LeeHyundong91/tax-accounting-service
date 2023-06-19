package net.dv.tax.controller.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.PurchaseReportEntity
import net.dv.tax.domain.consulting.PurchaseReportItemEntity
import net.dv.tax.service.consulting.PurchaseReportService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/purchase/report")
class PurchaseConsultingController(
    private val purchaseReportService: PurchaseReportService,
) {

    private val log = KotlinLogging.logger {}

    @PutMapping("/{year}/{hospitalId}")
    fun purchaseItemUpdate(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @RequestBody itemList: MutableList<PurchaseReportItemEntity>,
    ) {
        purchaseReportService.saveItemData(hospitalId, year, itemList)!!
    }

    /*for test*/
    @GetMapping("/test/{year}/{hospitalId}")
    fun test(@PathVariable hospitalId: String, @PathVariable year: String) {
        purchaseReportService.saveData(hospitalId, year)
    }

    @GetMapping("/{year}/{hospitalId}")
    fun purchaseReport(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
    ): PurchaseReportEntity {
        return purchaseReportService.getData(hospitalId, year)
    }


}