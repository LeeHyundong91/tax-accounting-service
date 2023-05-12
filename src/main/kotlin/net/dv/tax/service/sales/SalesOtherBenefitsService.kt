package net.dv.tax.service.sales

import net.dv.tax.domain.sales.SalesOtherBenefitsEntity
import net.dv.tax.dto.sales.SalesOtherBenefitsListDto
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

    fun getList(hospitalId: String, dataPeriod: String): SalesOtherBenefitsListDto {

        val dataList = salesOtherBenefitsRepository.findAllByHospitalIdAndDataPeriodAndIsDeleteFalseStartingWithOrderByDataPeriod(
            hospitalId,
            dataPeriod
        )

        return SalesOtherBenefitsListDto(
            dataList,
            totalList(dataList)
        )
    }


    fun totalList(dataList: List<SalesOtherBenefitsEntity>): SalesOtherBenefitsEntity {

        return SalesOtherBenefitsEntity(
            ownCharge = dataList.sumOf { it.ownCharge ?: 0 },
            agencyExpenses = dataList.sumOf { it.agencyExpenses ?: 0 },
            totalAmount = dataList.sumOf { it.totalAmount ?: 0 },
        )
    }

}