package net.dv.tax.controller.sales

import net.dv.tax.service.sales.IncomeStatementService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/income-statement")
class IncomeStatementController(private val incomeStatementService: IncomeStatementService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(@PathVariable hospitalId: String, @PathVariable year: String){
        incomeStatementService.dataList(hospitalId, year)
    }

}