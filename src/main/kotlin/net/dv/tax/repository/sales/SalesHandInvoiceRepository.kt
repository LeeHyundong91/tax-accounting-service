package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesHandInvoiceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface SalesHandInvoiceRepository : JpaRepository<SalesHandInvoiceEntity?, Int>,
    JpaSpecificationExecutor<SalesHandInvoiceEntity?>