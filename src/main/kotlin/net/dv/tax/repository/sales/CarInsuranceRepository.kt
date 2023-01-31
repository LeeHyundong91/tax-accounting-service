package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.CarInsuranceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CarInsuranceRepository: JpaRepository<CarInsuranceEntity?, Int>,
    JpaSpecificationExecutor<CarInsuranceEntity?> {
}