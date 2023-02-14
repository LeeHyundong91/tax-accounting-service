package net.dv.tax.service.sales

import net.dv.tax.dto.sales.MedicalBenefitsListDto
import net.dv.tax.repository.sales.MedicalBenefitsRepository
import org.springframework.stereotype.Service

@Service
class MedicalBenefitsService(
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
) {
    fun getListData(hospitalId: String, year: String) : List<MedicalBenefitsListDto> {
        return medicalBenefitsRepository.groupingList(hospitalId, year)
    }

}