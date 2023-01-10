package net.dv.tax.repository

import net.dv.tax.domain.HospitalChartEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface HospitalChartRepository: JpaRepository<HospitalChartEntity?, Int>,
    JpaSpecificationExecutor<HospitalChartEntity?> {

        fun findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId: Int, year: Int ): List<HospitalChartEntity>?



}