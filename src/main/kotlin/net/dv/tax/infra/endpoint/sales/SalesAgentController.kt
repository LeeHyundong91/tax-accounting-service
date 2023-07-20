package net.dv.tax.infra.endpoint.sales

import net.dv.tax.app.dto.sales.SalesAgentListDto
import net.dv.tax.app.sales.SalesAgentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/agent")
class SalesAgentController(private val salesAgentService: SalesAgentService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
    ): SalesAgentListDto {
        return salesAgentService.getList(hospitalId, year)
    }

}