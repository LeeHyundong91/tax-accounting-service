package net.dv.tax.repository.consulting

import net.dv.tax.domain.consulting.InsuranceClaimEntity
import net.dv.tax.domain.consulting.SalesPaymentMethodEntity
import net.dv.tax.domain.consulting.SalesTypeEntity
import net.dv.tax.domain.consulting.TaxExemptionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SalesTypeRepository : JpaRepository<SalesTypeEntity, Long> {
    fun findTopByHospitalIdAndResultYearMonth(hospitalId: String, resultYearMonth: String): SalesTypeEntity?
}

interface InsuranceClaimRepository : JpaRepository<InsuranceClaimEntity?, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId: String, resultYearMonth: String): InsuranceClaimEntity?
}

interface TaxExemptionRepository: JpaRepository<TaxExemptionEntity?, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId: String, resultYearMonth: String): TaxExemptionEntity?
}

interface SalesPaymentMethodRepository: JpaRepository<SalesPaymentMethodEntity, Long>{
    fun findTopByHospitalIdAndResultYearMonth(hospitalId: String, resultYearMonth: String): SalesPaymentMethodEntity
}