package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesVaccineEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface SalesVaccineRepository : JpaRepository<SalesVaccineEntity, Int>,
    JpaSpecificationExecutor<SalesVaccineEntity>{

        fun findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId: String, year: Int): List<SalesVaccineEntity>?

        /*Excel*/
        fun findAllByHospitalIdOrderByMonthAscYearAsc(hospitalId: String): List<SalesVaccineEntity>?

    }


