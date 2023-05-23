package net.dv.tax.repository.sales.support

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.QSalesVaccineEntity.salesVaccineEntity
import net.dv.tax.domain.sales.SalesVaccineEntity
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class SalesVaccineSupportImpl(private val query: JPAQueryFactory) : CustomQuerydslRepositorySupport(
    SalesVaccineEntity::class.java
), SalesVaccineSupport {
    override fun monthlySumAmount(hospitalId: String, yearMonth: String): Long? {
        return query
            .select(
                salesVaccineEntity.payAmount.sum()
            ).from(salesVaccineEntity)
            .where(
                salesVaccineEntity.hospitalId.eq(hospitalId),
                salesVaccineEntity.paymentYearMonth.startsWith(yearMonth)
            )
            .fetchOne()
    }

}