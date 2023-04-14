package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesAgentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SalesAgentRepository : JpaRepository<SalesAgentEntity, Long> {

    fun findAllByHospitalIdAndDataPeriod(hospitalId: String, dataPeriod: String): List<SalesAgentEntity>

}