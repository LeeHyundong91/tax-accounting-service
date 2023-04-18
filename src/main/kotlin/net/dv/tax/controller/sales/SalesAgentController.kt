package net.dv.tax.controller.sales

import net.dv.tax.domain.sales.SalesAgentEntity
import net.dv.tax.service.sales.SalesAgentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/agent")
class SalesAgentController(private val salesAgentService: SalesAgentService) {

    @GetMapping("/list/{dataPeriod}/{hospitalId}")
    fun dataList(@PathVariable dataPeriod: String, @PathVariable hospitalId: String): List<SalesAgentEntity> {
        return salesAgentService.getDataList(hospitalId, dataPeriod)
    }

}