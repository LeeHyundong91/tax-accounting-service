package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.repository.sales.support.CashReceiptSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SalesCashReceiptRepository : JpaRepository<SalesCashReceiptEntity, Int>,
    CashReceiptSupport {
    fun findAllByHospitalIdAndSalesDateStartingWith(
        hospitalId: String,
        salesDate: String,
        page: Pageable,
    ): Page<SalesCashReceiptEntity>

}