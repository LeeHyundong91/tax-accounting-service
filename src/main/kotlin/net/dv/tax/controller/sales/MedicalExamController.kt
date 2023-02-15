package net.dv.tax.controller.sales

import net.dv.tax.dto.sales.MedicalExamListDto
import net.dv.tax.service.sales.MedicalExamService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/sales/medical-exam")
class MedicalExamController(private val medicalExamService: MedicalExamService) {

    @GetMapping("/list/{year}/{hospitalId}")
    fun getList(@PathVariable year: String, @PathVariable hospitalId: String): List<MedicalExamListDto>{
        return medicalExamService.getListData(hospitalId, year)
    }

    @GetMapping
    fun getDetail(){

    }

}