package net.dv.tax.app.sales

import net.dv.tax.domain.sales.MedicalBenefitsEntity
import net.dv.tax.app.dto.sales.MedicalBenefitsListDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MedicalBenefitsService(private val medicalBenefitsRepository: MedicalBenefitsRepository) {

    fun getList(hospitalId: String, yearMonth: String): MedicalBenefitsListDto {
        return MedicalBenefitsListDto(
            medicalBenefitsRepository.dataList(hospitalId, yearMonth),
            medicalBenefitsRepository.dataListTotal(hospitalId, yearMonth)
        )
    }

    fun getDetailList(hospitalId: String, yearMonth: String, page: Pageable): Page<MedicalBenefitsEntity>{
        return medicalBenefitsRepository.findAllByHospitalIdAndTreatmentYearMonth(hospitalId, yearMonth, page)
    }



}