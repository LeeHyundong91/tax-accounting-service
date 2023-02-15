package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.MedicalExamEntity
import net.dv.tax.repository.sales.support.MedicalExamSupport
import org.springframework.data.jpa.repository.JpaRepository

interface MedicalExamRepository : JpaRepository<MedicalExamEntity?, Int>,
    MedicalExamSupport {

    fun findAllByHospitalIdAndDataPeriodStartingWith(hospitalId: String, dataPeriod: String): List<MedicalExamEntity>?

}