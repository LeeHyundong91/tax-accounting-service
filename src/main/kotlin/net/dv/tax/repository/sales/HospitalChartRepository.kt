package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.HospitalChartEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface HospitalChartRepository: JpaRepository<HospitalChartEntity?, Int>,
    JpaSpecificationExecutor<HospitalChartEntity?> {

        fun findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId: String, year: Int): List<HospitalChartEntity>?



}