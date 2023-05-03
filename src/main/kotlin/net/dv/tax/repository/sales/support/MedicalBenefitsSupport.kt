package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.MedicalBenefitsDto

interface MedicalBenefitsSupport {

    fun dataList(hospitalId: String, treatmentYearMonth: String): List<MedicalBenefitsDto>

    fun dataListTotal(hospitalId: String, treatmentYearMonth: String) : MedicalBenefitsDto?

}