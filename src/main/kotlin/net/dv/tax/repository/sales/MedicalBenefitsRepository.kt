package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.MedicalBenefitsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface MedicalBenefitsRepository: JpaRepository<MedicalBenefitsEntity?, Int>,
    JpaSpecificationExecutor<MedicalBenefitsEntity?> {
}