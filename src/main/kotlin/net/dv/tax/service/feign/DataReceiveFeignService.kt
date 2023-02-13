package net.dv.tax.service.feign

import net.dv.tax.domain.sales.MedicalBenefitsEntity
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "data-receive-service", url = "localhost:8090/send")
interface DataReceiveFeignService {

    @GetMapping("/medical-benefits")
    fun getMedicalBenefits(): List<MedicalBenefitsEntity>

}