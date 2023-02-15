package net.dv.tax.service.sales

import net.dv.tax.domain.sales.MedicalExamEntity
import net.dv.tax.dto.sales.MedicalExamListDto
import net.dv.tax.repository.sales.MedicalExamRepository
import org.springframework.stereotype.Service

@Service
class MedicalExamService(
    private val medicalExamRepository: MedicalExamRepository,
) {
    fun getListData(hospitalId: String, year: String): List<MedicalExamListDto> {
        return medicalExamRepository.groupingList(hospitalId, year)
    }

    fun getDetailData(hospitalId: String, yearMonth: String) : List<MedicalExamEntity>{
        return medicalExamRepository.findAllByHospitalIdAndDataPeriodStartingWith(hospitalId,yearMonth)!!
    }



}