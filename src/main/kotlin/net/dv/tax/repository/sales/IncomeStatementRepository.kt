package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.IncomeStatementEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface IncomeStatementRepository : JpaRepository<IncomeStatementEntity?, Int>,
    JpaSpecificationExecutor<IncomeStatementEntity?> {
}