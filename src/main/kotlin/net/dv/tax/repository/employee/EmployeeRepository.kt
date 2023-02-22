package net.dv.tax.repository.employee

import net.dv.tax.domain.sales.CarInsuranceEntity
import net.dv.tax.repository.employee.support.EmployeeSupport
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<CarInsuranceEntity?, Int>,
    EmployeeSupport {
}