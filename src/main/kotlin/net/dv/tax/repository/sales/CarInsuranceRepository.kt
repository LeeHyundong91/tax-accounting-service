package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.CarInsuranceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CarInsuranceRepository : JpaRepository<CarInsuranceEntity?, Int>,
    CarInsuranceSupport {
}