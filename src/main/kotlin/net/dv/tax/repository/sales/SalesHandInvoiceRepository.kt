package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesHandInvoiceEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SalesHandInvoiceRepository : JpaRepository<SalesHandInvoiceEntity, Int>
     {

    fun findAllByHospitalIdAndIssueDateStartingWithAndIsDeleteIsFalseOrderByIssueDateDesc(
        hospitalId: String,
        issueDate: String,
        page: Pageable
    ): Page<SalesHandInvoiceEntity>

}