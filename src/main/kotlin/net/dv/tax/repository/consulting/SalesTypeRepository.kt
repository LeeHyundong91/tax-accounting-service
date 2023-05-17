package net.dv.tax.repository.consulting

import net.dv.tax.domain.consulting.SalesTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SalesTypeRepository: JpaRepository<SalesTypeEntity, Long> {

    fun countAllByHospitalIdAndResultYearMonth(hospitalId: String, resultYearMonth: String): Long

    fun findTopByHospitalIdAndResultYearMonth(hospitalId: String, resultYearMonth: String): SalesTypeEntity

}