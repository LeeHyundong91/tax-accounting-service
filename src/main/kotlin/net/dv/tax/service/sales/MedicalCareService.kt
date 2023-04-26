package net.dv.tax.service.sales

import net.dv.tax.domain.sales.MedicalCareEntity
import net.dv.tax.dto.sales.MedicalCareListDto
import net.dv.tax.repository.sales.MedicalCareRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MedicalCareService(private val medicalCareRepository: MedicalCareRepository) {

    fun getMedicalCareList(hospitalId: String, year: String): MedicalCareListDto {
        return MedicalCareListDto(
            medicalCareRepository.dataList(hospitalId, year),
            medicalCareRepository.dataListTotal(hospitalId, year)
        )
    }

    fun getMedicalCareDetailList(hospitalId: String, year: String, page: Pageable): Page<MedicalCareEntity> {
        return medicalCareRepository.findAllByHospitalIdAndTreatmentYearMonth(hospitalId, year, page)
    }

}