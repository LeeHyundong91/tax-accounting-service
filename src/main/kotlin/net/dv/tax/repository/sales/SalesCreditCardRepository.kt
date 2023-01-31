package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesCreditCardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface SalesCreditCardRepository: JpaRepository<SalesCreditCardEntity?, Int>,
    JpaSpecificationExecutor<SalesCreditCardEntity?> {
}