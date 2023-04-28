package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesVaccineEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SalesVaccineRepository : JpaRepository<SalesVaccineEntity, Int> {

    fun findAllByHospitalIdAndPaymentMonthYearStartingWithOrderByPaymentMonthYear(
        hospitalId: String,
        year: String,
    ): List<SalesVaccineEntity>


}


