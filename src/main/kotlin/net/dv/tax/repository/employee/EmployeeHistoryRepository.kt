package net.dv.tax.repository.employee

import net.dv.tax.domain.employee.EmployeeHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeHistoryRepository : JpaRepository<EmployeeHistoryEntity?, Int>
