package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.EmployeeIndustryEntity
import net.dv.tax.repository.sales.support.EmployeeIndustrySupport
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeIndustryRepository : JpaRepository<EmployeeIndustryEntity?, Int>,
    EmployeeIndustrySupport {

        fun findAllByHospitalIdAndPaydayStartingWith(hospitalId: String, payday: String): List<EmployeeIndustryEntity>?


}