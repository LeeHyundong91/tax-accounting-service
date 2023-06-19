package net.dv.tax.controller.consulting

import net.dv.tax.domain.consulting.AdjustmentCostEntity
import net.dv.tax.domain.consulting.AdjustmentCostItemEntity
import net.dv.tax.service.consulting.AdjustmentCostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/adjustment-cost")
class AdjustmentCostController(private val adjustmentCostService: AdjustmentCostService) {

    @GetMapping("/test/{year}/{hospitalId}")
    fun test(@PathVariable hospitalId: String, @PathVariable year: String) {
        adjustmentCostService.saveData(hospitalId, year)
    }

    @PutMapping("/{year}/{hospitalId}")
    fun saveData(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @RequestBody itemList: MutableList<AdjustmentCostItemEntity>,
    ) {
        adjustmentCostService.saveItemData(hospitalId, year, itemList)
    }

    @GetMapping("/{year}/{hospitalId}")
    fun getData(@PathVariable hospitalId: String, @PathVariable year: String): AdjustmentCostEntity{
        return adjustmentCostService.getData(hospitalId, year)
    }

    @DeleteMapping("/{id}")
    fun removeData(@PathVariable id: Long): ResponseEntity<HttpStatus>{
        return adjustmentCostService.removeItem(id)
    }

}