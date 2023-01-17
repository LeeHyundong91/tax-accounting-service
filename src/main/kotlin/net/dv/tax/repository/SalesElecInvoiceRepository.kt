package net.dv.tax.repository

import net.dv.tax.domain.sales.SalesElecInvoiceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface SalesElecInvoiceRepository: JpaRepository<SalesElecInvoiceEntity?, Int>,
    JpaSpecificationExecutor<SalesElecInvoiceEntity?> {
}