package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.MedicalBenefitsEntity
import net.dv.tax.repository.sales.support.MedicalBenefitsSupport
import org.springframework.data.jpa.repository.JpaRepository

interface MedicalBenefitsRepository : JpaRepository<MedicalBenefitsEntity, Long>, MedicalBenefitsSupport {
}