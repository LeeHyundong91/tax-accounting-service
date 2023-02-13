package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.MedicalBenefitsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MedicalBenefitsRepository : JpaRepository<MedicalBenefitsEntity?, Int>, MedicalBenefitsSupport {

}