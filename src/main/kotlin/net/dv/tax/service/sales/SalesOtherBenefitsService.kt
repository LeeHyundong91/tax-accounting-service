package net.dv.tax.service.sales

import net.dv.tax.domain.sales.SalesOtherBenefitsEntity
import net.dv.tax.repository.sales.SalesOtherBenefitsRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SalesOtherBenefitsService(private val salesOtherBenefitsRepository: SalesOtherBenefitsRepository) {

    fun saveData(dataList: List<SalesOtherBenefitsEntity>): ResponseEntity<HttpStatus> {
        salesOtherBenefitsRepository.saveAll(dataList)
        return ResponseEntity(HttpStatus.OK)
    }

    fun getList(hospitalId: String, dataPeriod: String): List<SalesOtherBenefitsEntity> {
        return salesOtherBenefitsRepository.findAllByHospitalIdAndDataPeriod(hospitalId, dataPeriod)
    }


}