package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.MedicalCareEntity
import net.dv.tax.repository.sales.support.MedicalCareSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MedicalCareRepository : JpaRepository<MedicalCareEntity, Long>, MedicalCareSupport {

    fun findAllByHospitalIdAndTreatmentYearMonth(hospitalId: String, treatmentYearMonth: String, page: Pageable): Page<MedicalCareEntity>

}