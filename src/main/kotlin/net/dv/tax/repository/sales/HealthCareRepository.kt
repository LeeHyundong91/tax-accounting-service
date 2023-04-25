package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.HealthCareEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HealthCareRepository : JpaRepository<HealthCareEntity, Long> {
}