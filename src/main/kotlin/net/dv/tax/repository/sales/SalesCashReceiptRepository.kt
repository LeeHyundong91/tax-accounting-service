package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesCashReceiptEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface SalesCashReceiptRepository: JpaRepository<SalesCashReceiptEntity?, Int>,
    JpaSpecificationExecutor<SalesCashReceiptEntity?> {
}