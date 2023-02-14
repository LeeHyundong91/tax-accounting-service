package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.MedicalExamListDto

interface MedicalExamSupport {
    fun groupingList(hospitalId: String, dataPeriod: String): List<MedicalExamListDto>
}