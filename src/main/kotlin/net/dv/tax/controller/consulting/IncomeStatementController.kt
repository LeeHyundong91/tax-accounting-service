package net.dv.tax.controller.consulting

import net.dv.tax.domain.consulting.IncomeStatementEntity
import net.dv.tax.service.consulting.IncomeStatementService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/consulting/income-statement")
class IncomeStatementController(private val incomeStatementService: IncomeStatementService) {

    /*for test*/
    @GetMapping("/test/{year}/{hospitalId}")
    fun test(@PathVariable hospitalId: String, @PathVariable year: String) {
        incomeStatementService.saveData(hospitalId, year)
    }

    @GetMapping("/{year}/{hospitalId}")
    fun getData(@PathVariable hospitalId: String, @PathVariable year: String): IncomeStatementEntity {
        return incomeStatementService.getData(hospitalId, year)
    }

}