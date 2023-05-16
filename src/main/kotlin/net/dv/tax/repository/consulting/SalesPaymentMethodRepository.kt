package net.dv.tax.repository.consulting

import net.dv.tax.domain.consulting.SalesPaymentMethodEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SalesPaymentMethodRepository: JpaRepository<SalesPaymentMethodEntity, Long>{

    fun findTopByHospitalIdAndResultYearMonth(hospitalId: String, resultYearMonth: String): SalesPaymentMethodEntity

}