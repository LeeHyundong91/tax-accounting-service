package net.dv.tax.infra.endpoint.consulting

import net.dv.tax.domain.consulting.TaxCreditItemEntity
import net.dv.tax.domain.consulting.TaxCreditPersonalItemEntity
import net.dv.tax.app.dto.consulting.TaxCreditDto
import net.dv.tax.app.consulting.TaxCreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/consulting/tax-credit")
class TaxCreditController(private val taxCreditService: TaxCreditService) {

    /*for test*/
    @GetMapping("/test/{year}/{hospitalId}")
    fun makeData(@PathVariable hospitalId: String, @PathVariable year: String) {
        taxCreditService.saveData(hospitalId, year)
    }

    @GetMapping("/{year}/{hospitalId}")
    fun getData(@PathVariable hospitalId: String, @PathVariable year: String): TaxCreditDto {
        return taxCreditService.getData(hospitalId, year)
    }

    @PatchMapping("/{year}/{hospitalId}/{option}")
    fun patchOption(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        /*EMPLOYMENT_INCREASE, INTEGRATED_EMPLOYMENT*/
        @PathVariable option: String,
    ): ResponseEntity<HttpStatus> {
        return taxCreditService.patchItemOption(hospitalId, year, option)
    }

    @PutMapping("/{year}/{hospitalId}")
    fun saveHospitalData(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @RequestBody itemList: MutableList<TaxCreditItemEntity>,
    ) {
        taxCreditService.updateHospitalItem(itemList, hospitalId, year)
    }

    @DeleteMapping("/{year}/{hospitalId}/{id}")
    fun deleteHospitalItem(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @PathVariable id: Long,
    ): ResponseEntity<HttpStatus> {
        return taxCreditService.deleteHospitalItem(hospitalId, year, id)
    }

    @PutMapping("/{year}/{hospitalId}/{taxCreditPersonalId}")
    fun savePersonalData(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @PathVariable taxCreditPersonalId: Long,
        @RequestBody itemList: MutableList<TaxCreditPersonalItemEntity>,
    ): ResponseEntity<HttpStatus> {
        return taxCreditService.updatePersonalItem(hospitalId, year, taxCreditPersonalId, itemList)
    }

    @DeleteMapping("/{year}/{hospitalId}/item/{id}")
    fun deletePersonalItem(
        @PathVariable hospitalId: String,
        @PathVariable year: String,
        @PathVariable id: Long,
    ): ResponseEntity<HttpStatus> {
        return taxCreditService.deletePersonalItem(hospitalId, year, id)
    }


}