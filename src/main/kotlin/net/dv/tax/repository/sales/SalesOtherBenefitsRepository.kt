package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesOtherBenefitsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SalesOtherBenefitsRepository : JpaRepository<SalesOtherBenefitsEntity, Long> {

    fun findAllByHospitalIdAndDataPeriodStartingWithOrderByDataPeriod(hospitalId: String, dataPeriod: String): List<SalesOtherBenefitsEntity>

}