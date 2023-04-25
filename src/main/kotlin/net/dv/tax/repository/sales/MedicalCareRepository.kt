package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.MedicalCareEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MedicalCareRepository : JpaRepository<MedicalCareEntity, Long> {
}