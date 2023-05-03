package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.MedicalCareDto

interface MedicalCareSupport {

    fun dataList(hospitalId: String, treatmentYearMonth: String): List<MedicalCareDto>

    fun dataListTotal(hospitalId: String, treatmentYearMonth: String) : MedicalCareDto?

}