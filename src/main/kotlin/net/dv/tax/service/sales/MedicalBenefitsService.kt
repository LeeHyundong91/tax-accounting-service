package net.dv.tax.service.sales

import net.dv.tax.domain.sales.MedicalBenefitsEntity
import net.dv.tax.dto.sales.MedicalBenefitsListDto
import net.dv.tax.repository.sales.MedicalBenefitsRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MedicalBenefitsService(private val medicalBenefitsRepository: MedicalBenefitsRepository) {

    fun getMedicalBenefitsList(hospitalId: String, yearMonth: String): MedicalBenefitsListDto {
        return MedicalBenefitsListDto(
            medicalBenefitsRepository.dataList(hospitalId, yearMonth),
            medicalBenefitsRepository.dataListTotal(hospitalId, yearMonth)
        )
    }

    fun getMedicalBenefitsDetail(hospitalId: String, yearMonth: String, page: Pageable): Page<MedicalBenefitsEntity>{
        return medicalBenefitsRepository.findAllByHospitalIdAndTreatmentYearMonth(hospitalId, yearMonth, page)
    }



}