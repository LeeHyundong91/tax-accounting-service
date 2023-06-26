package net.dv.tax.controller.consulting

import net.dv.tax.domain.consulting.ExpectedIncomeEntity
import net.dv.tax.service.consulting.ExpectedIncomeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/consulting/expected-income")
class ExpectedIncomeController(private val expectedIncomeService: ExpectedIncomeService) {


    /*for test*/
    @GetMapping("/test/{year}/{hospitalId}")
    fun test(@PathVariable hospitalId: String, @PathVariable year: String) {
        expectedIncomeService.saveData(hospitalId, year)
    }

    @GetMapping("/{year}/{hospitalId}")
    fun getData(@PathVariable hospitalId: String, @PathVariable year: String): ExpectedIncomeEntity {
        return expectedIncomeService.getData(hospitalId, year)
    }


}