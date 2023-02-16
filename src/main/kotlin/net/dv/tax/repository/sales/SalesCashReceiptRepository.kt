package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.repository.sales.support.CashReceiptSupport
import org.springframework.data.jpa.repository.JpaRepository

interface SalesCashReceiptRepository : JpaRepository<SalesCashReceiptEntity?, Int>,
    CashReceiptSupport {

    fun findAllByHospitalIdAndDataPeriodStartingWith(
        hospitalId: String,
        dataPeriod: String,
    ): List<SalesCashReceiptEntity>
}