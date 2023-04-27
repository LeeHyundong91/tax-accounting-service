package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.HealthCareEntity
import net.dv.tax.domain.sales.QHealthCareEntity.healthCareEntity
import net.dv.tax.dto.sales.HealthCareDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class HealthCareSupportImpl(private val query: JPAQueryFactory) : CustomQuerydslRepositorySupport(
    HealthCareEntity::class.java
), HealthCareSupport {
    override fun dataList(hospitalId: String, yearMonth: String): List<HealthCareDto> {

        return query.select(
            Projections.bean(
                HealthCareDto::class.java,
                healthCareEntity.paidDate.substring(0, 7).`as`("paidDate"),
                healthCareEntity.claimAmount.sum().`as`("claimAmount"),
                healthCareEntity.adjustedAmount.sum().`as`("adjustedAmount"),
                healthCareEntity.screeningAmount.sum().`as`("screeningAmount"),
                healthCareEntity.deductedAmount.sum().`as`("deductedAmount"),
                healthCareEntity.paidAmount.sum().`as`("paidAmount"),
                healthCareEntity.addedPersons.sum().`as`("addedPersons"),
                healthCareEntity.addedAmount.sum().`as`("addedAmount"),
                healthCareEntity.additionalPayment.sum().`as`("additionalPayment"),
                healthCareEntity.taxAmount.sum().`as`("taxAmount"),
                healthCareEntity.refundAmount.sum().`as`("refundAmount"),
                healthCareEntity.deductionAmount.sum().`as`("deductionAmount"),
                healthCareEntity.receivableAmount.sum().`as`("receivableAmount"),
                healthCareEntity.remittanceAmount.sum().`as`("remittanceAmount"),
            )
        ).from(healthCareEntity)
            .where(
                healthCareEntity.hospitalId.eq(hospitalId),
                healthCareEntity.paidDate.startsWith(yearMonth)
            )
            .groupBy(healthCareEntity.paidDate.substring(0, 7))
            .fetch()
    }

    override fun dataListTotal(hospitalId: String, yearMonth: String): HealthCareDto {
        return query.select(
            Projections.bean(
                HealthCareDto::class.java,
                healthCareEntity.paidDate.substring(0, 4).`as`("paidDate"),
                healthCareEntity.claimAmount.sum().`as`("claimAmount"),
                healthCareEntity.adjustedAmount.sum().`as`("adjustedAmount"),
                healthCareEntity.screeningAmount.sum().`as`("screeningAmount"),
                healthCareEntity.deductedAmount.sum().`as`("deductedAmount"),
                healthCareEntity.paidAmount.sum().`as`("paidAmount"),
                healthCareEntity.addedPersons.sum().`as`("addedPersons"),
                healthCareEntity.addedAmount.sum().`as`("addedAmount"),
                healthCareEntity.additionalPayment.sum().`as`("additionalPayment"),
                healthCareEntity.taxAmount.sum().`as`("taxAmount"),
                healthCareEntity.refundAmount.sum().`as`("refundAmount"),
                healthCareEntity.deductionAmount.sum().`as`("deductionAmount"),
                healthCareEntity.receivableAmount.sum().`as`("receivableAmount"),
                healthCareEntity.remittanceAmount.sum().`as`("remittanceAmount"),
            )
        ).from(healthCareEntity)
            .where(
                healthCareEntity.hospitalId.eq(hospitalId),
                healthCareEntity.paidDate.startsWith(yearMonth)
            )
            .fetchOne()!!
    }
}