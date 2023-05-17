package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.QSalesOtherBenefitsEntity.salesOtherBenefitsEntity
import net.dv.tax.domain.sales.SalesOtherBenefitsEntity
import net.dv.tax.dto.sales.SalesOtherBenefitsGroupDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class SalesOtherBenefitsSupportImpl(private val query: JPAQueryFactory) : CustomQuerydslRepositorySupport(
    SalesOtherBenefitsEntity::class.java
), SalesOtherBenefitsSupport {
    override fun dataList(hospitalId: String, yearMonth: String): List<SalesOtherBenefitsGroupDto> {

        return query
            .select(
                Projections.bean(
                    SalesOtherBenefitsGroupDto::class.java,
                    salesOtherBenefitsEntity.itemName.`as`("itemName"),
                    salesOtherBenefitsEntity.agencyExpense.sum().`as`("agencyExpense"),
                    salesOtherBenefitsEntity.ownCharge.sum().`as`("ownCharge"),
                    salesOtherBenefitsEntity.totalAmount.sum().`as`("totalAmount"),
                )
            ).from(salesOtherBenefitsEntity)
            .where(
                salesOtherBenefitsEntity.hospitalId.eq(hospitalId),
                salesOtherBenefitsEntity.dataPeriod.startsWith(yearMonth)
            )
            .groupBy(salesOtherBenefitsEntity.itemName)
            .fetch()
    }

}