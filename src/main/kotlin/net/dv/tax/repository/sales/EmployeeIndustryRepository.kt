package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.EmployeeIndustryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface EmployeeIndustryRepository : JpaRepository<EmployeeIndustryEntity?, Int>,
    JpaSpecificationExecutor<EmployeeIndustryEntity?> {

    fun findAllByHospitalId(hospitalId: Int): List<EmployeeIndustryEntity>?

}