package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.HospitalChartEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HospitalChartRepository : JpaRepository<HospitalChartEntity?, Int> {

    fun findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId: String, year: Int): List<HospitalChartEntity>?


}