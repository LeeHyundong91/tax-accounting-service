package net.dv.tax.service.sales

import net.dv.tax.repository.sales.MedicalBenefitsRepository
import net.dv.tax.repository.sales.MedicalBenefitsSupportImpl
import org.springframework.stereotype.Service

@Service
class MedicalBenefitsService(private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val medicalBenefitsSupportImpl: MedicalBenefitsSupportImpl
) {

    fun getData() {
        medicalBenefitsSupportImpl.groupingList("cid01", "2022")
    }

}