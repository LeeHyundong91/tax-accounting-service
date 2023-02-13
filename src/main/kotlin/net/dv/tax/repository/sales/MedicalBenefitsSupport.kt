package net.dv.tax.repository.sales

import net.dv.tax.dto.sales.MedicalBenefitsListDto

interface MedicalBenefitsSupport {
    fun groupingList(hospitalId: String, dataPeriod: String): List<MedicalBenefitsListDto>
}