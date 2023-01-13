package net.dv.tax.repository

import net.dv.tax.domain.sales.VaccineSalesEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface VaccineSalesRepository : JpaRepository<VaccineSalesEntity, Int>,
    JpaSpecificationExecutor<VaccineSalesEntity>{

        fun findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId: Int, year: Int): List<VaccineSalesEntity>?

        /*Excel*/
        fun findAllByHospitalIdOrderByMonthAscYearAsc(hospitalId: Int): List<VaccineSalesEntity>?

    }


