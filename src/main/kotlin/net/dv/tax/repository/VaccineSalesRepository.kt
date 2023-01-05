package net.dv.tax.repository

import net.dv.tax.domain.VaccineSalesEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface VaccineSalesRepository : JpaRepository<VaccineSalesEntity, Int>,
    JpaSpecificationExecutor<VaccineSalesEntity>{

        fun findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId: Int, year: String): List<VaccineSalesEntity>?

    }


