package net.dv.tax.service.sales

import net.dv.tax.domain.sales.HealthCareEntity
import net.dv.tax.dto.sales.HealthCareListDto
import net.dv.tax.repository.sales.HealthCareRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class HealthCareService(private val healthCareRepository: HealthCareRepository) {

    fun getList(hospitalId: String, yearMonth: String): HealthCareListDto {
        return HealthCareListDto(
            healthCareRepository.dataList(hospitalId, yearMonth),
            healthCareRepository.dataListTotal(hospitalId, yearMonth)
        )
    }

    fun getDetailList(hospitalId: String, yearMonth: String, page: Pageable): Page<HealthCareEntity> {
        return healthCareRepository.findAllByHospitalIdAndPaidDateStartingWith(hospitalId, yearMonth, page)
    }

}