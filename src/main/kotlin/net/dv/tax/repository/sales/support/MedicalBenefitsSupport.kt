package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.MedicalBenefitsListDto

interface MedicalBenefitsSupport {
    fun groupingList(hospitalId: String, dataPeriod: String): List<MedicalBenefitsListDto>
}