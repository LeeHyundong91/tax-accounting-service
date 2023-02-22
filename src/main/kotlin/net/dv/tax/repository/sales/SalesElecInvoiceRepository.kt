package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesElecInvoiceEntity
import net.dv.tax.repository.sales.support.SalesElecInvoiceSupport
import org.springframework.data.jpa.repository.JpaRepository

interface SalesElecInvoiceRepository : JpaRepository<SalesElecInvoiceEntity?, Int>, SalesElecInvoiceSupport {

    fun findAllByHospitalIdAndDataPeriodStartingWith(
        hospitalId: String,
        dataPeriod: String,
    ): List<SalesElecInvoiceEntity>
}