package net.dv.tax.controller

import net.dv.tax.service.common.TaxDataLoadAndSaveService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CallDataController(private val taxDataLoadService: TaxDataLoadAndSaveService) {

    @GetMapping("/get/data")
    fun getTaxDataFromReceiveService(){
        taxDataLoadService.getBenefitsData()
    }

}