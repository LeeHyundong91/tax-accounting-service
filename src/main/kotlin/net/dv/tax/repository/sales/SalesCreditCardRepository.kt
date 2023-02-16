package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesCreditCardEntity
import net.dv.tax.repository.sales.support.SalesCreditCardSupport
import org.springframework.data.jpa.repository.JpaRepository

interface SalesCreditCardRepository: JpaRepository<SalesCreditCardEntity?, Int>,
    SalesCreditCardSupport {
}