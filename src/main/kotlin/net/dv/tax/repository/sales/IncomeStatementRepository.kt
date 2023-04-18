package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.IncomeStatementEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IncomeStatementRepository : JpaRepository<IncomeStatementEntity?, Int> {

    fun findAllByHospitalIdAndDataPeriodStartingWith(hospitalId: String, dataPeriod: String)

}