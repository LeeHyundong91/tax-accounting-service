package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.MedicalBenefitsEntity
import net.dv.tax.repository.sales.support.MedicalBenefitsSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MedicalBenefitsRepository : JpaRepository<MedicalBenefitsEntity, Long>, MedicalBenefitsSupport {

    fun findAllByHospitalIdAndTreatmentYearMonth(
        hospitalId: String,
        yearMonth: String,
        page: Pageable
    ): Page<MedicalBenefitsEntity>

}