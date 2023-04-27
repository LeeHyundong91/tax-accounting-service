package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.CarInsuranceEntity
import net.dv.tax.repository.sales.support.CarInsuranceSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CarInsuranceRepository : JpaRepository<CarInsuranceEntity?, Int>, CarInsuranceSupport {

    fun findAllByHospitalIdAndTreatmentYearMonth(
        hospitalId: String,
        treatmentYearMonth: String,
        page: Pageable,
    ): Page<CarInsuranceEntity>

}