package net.dv.tax.app.sales

import net.dv.tax.domain.sales.MedicalCareEntity
import net.dv.tax.app.dto.sales.MedicalCareListDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MedicalCareService(private val medicalCareRepository: MedicalCareRepository) {

    fun getList(hospitalId: String, year: String): MedicalCareListDto {
        return MedicalCareListDto(
            medicalCareRepository.dataList(hospitalId, year),
            medicalCareRepository.dataListTotal(hospitalId, year)
        )
    }

    fun getDetailList(hospitalId: String, year: String, page: Pageable): Page<MedicalCareEntity> {
        return medicalCareRepository.findAllByHospitalIdAndTreatmentYearMonth(hospitalId, year, page)
    }

}