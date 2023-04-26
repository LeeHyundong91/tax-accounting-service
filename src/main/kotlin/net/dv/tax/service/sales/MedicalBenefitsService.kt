package net.dv.tax.service.sales

import net.dv.tax.dto.sales.MedicalBenefitsListDto
import net.dv.tax.repository.sales.MedicalBenefitsRepository
import org.springframework.stereotype.Service

@Service
class MedicalBenefitsService(private val medicalBenefitsRepository: MedicalBenefitsRepository) {

    fun medicalBenefitsList(hospitalId: String, yearMonth: String): MedicalBenefitsListDto {
        return MedicalBenefitsListDto(
            medicalBenefitsRepository.dataList(hospitalId, yearMonth),
            medicalBenefitsRepository.dataListTotal(hospitalId, yearMonth)
        )
    }

}