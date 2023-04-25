package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesHandInvoiceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SalesHandInvoiceRepository : JpaRepository<SalesHandInvoiceEntity?, Int>
     {

    fun findAllByHospitalIdAndIssueDtStartingWithAndIsDeleteIsFalse(
        hospitalId: String,
        issueDt: String,
    ): List<SalesHandInvoiceEntity?>?

}