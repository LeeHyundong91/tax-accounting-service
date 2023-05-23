package net.dv.tax.repository.consulting

import net.dv.tax.domain.consulting.InsuranceClaimEntity
import net.dv.tax.domain.consulting.SalesTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SalesTypeRepository : JpaRepository<SalesTypeEntity, Long> {
    fun findTopByHospitalIdAndResultYearMonth(hospitalId: String, resultYearMonth: String): SalesTypeEntity?
}

interface InsuranceClaimRepository : JpaRepository<InsuranceClaimEntity?, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId: String, dataPeriod: String): InsuranceClaimEntity?
}
