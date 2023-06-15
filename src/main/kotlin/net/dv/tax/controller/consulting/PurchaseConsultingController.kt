package net.dv.tax.controller.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.PurchaseReportEntity
import net.dv.tax.domain.consulting.PurchaseReportItemEntity
import net.dv.tax.repository.consulting.PurchaseReportRepository
import net.dv.tax.service.consulting.PurchaseReportService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/purchase")
class PurchaseConsultingController(
    private val purchaseReportService: PurchaseReportService,
    private val purchaseReportRepository: PurchaseReportRepository,
) {

    private val log = KotlinLogging.logger {}


    @PutMapping("/item/save/{year}/{hospitalId}")
    fun purchaseItemUpdate(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @RequestBody itemList: MutableList<PurchaseReportItemEntity>,
    )
            {
        purchaseReportService.saveItemData(hospitalId, year, itemList)!!
    }

    /*for test*/
    @GetMapping("/{year}/{hospitalId}")
    fun test(@PathVariable hospitalId: String, @PathVariable year: String) {
        purchaseReportService.saveData(hospitalId, year)
    }

    @GetMapping("/report/{year}/{hospitalId}")
    fun purchaseReport(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
    ): PurchaseReportEntity{
        return purchaseReportService.getData(hospitalId, year)
    }


    fun calcSmallData() {

    }


}