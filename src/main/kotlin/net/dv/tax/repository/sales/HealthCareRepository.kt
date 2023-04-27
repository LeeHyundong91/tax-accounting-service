package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.HealthCareEntity
import net.dv.tax.repository.sales.support.HealthCareSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface HealthCareRepository : JpaRepository<HealthCareEntity, Long>, HealthCareSupport {

    fun findAllByHospitalIdAndPaidDateStartingWith(
        hospitalId: String,
        paidDate: String,
        page: Pageable,
    ): Page<HealthCareEntity>


}