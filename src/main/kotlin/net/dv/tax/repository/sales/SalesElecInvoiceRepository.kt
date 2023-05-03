package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesElecInvoiceEntity
import net.dv.tax.repository.sales.support.SalesElecInvoiceSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SalesElecInvoiceRepository : JpaRepository<SalesElecInvoiceEntity, Long>, SalesElecInvoiceSupport {

    fun findAllByHospitalIdAndTaxIsFalseAndWritingDateStartingWithOrderByWritingDateDesc(
        hospitalId: String,
        writingDate: String,
        page: Pageable,
    ): Page<SalesElecInvoiceEntity>

    fun findAllByHospitalIdAndTaxIsTrueAndWritingDateStartingWithOrderByWritingDateDesc(
        hospitalId: String,
        writingDate: String,
        page: Pageable,
    ): Page<SalesElecInvoiceEntity>

}