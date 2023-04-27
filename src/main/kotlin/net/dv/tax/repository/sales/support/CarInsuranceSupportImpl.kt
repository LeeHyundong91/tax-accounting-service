package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.CarInsuranceEntity
import net.dv.tax.domain.sales.QCarInsuranceEntity.carInsuranceEntity
import net.dv.tax.dto.sales.CarInsuranceDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport

class CarInsuranceSupportImpl(private val query: JPAQueryFactory) : CustomQuerydslRepositorySupport(
    CarInsuranceEntity::class.java
), CarInsuranceSupport {
    override fun dataList(hospitalId: String, yearMonth: String): List<CarInsuranceDto> {

        return query.select(
            Projections.bean(
                CarInsuranceDto::class.java,
                carInsuranceEntity.treatmentYearMonth.`as`("treatmentYearMonth"),
                carInsuranceEntity.claimItemsCount.sum().`as`("claimItemsCount"),
                carInsuranceEntity.decisionTotalAmount.sum().`as`("decisionTotalAmount"),
                carInsuranceEntity.decisionPatientPayment.sum().`as`("decisionPatientPayment"),
                carInsuranceEntity.decisionAmount.sum().`as`("decisionAmount"),
            )
        ).from(carInsuranceEntity)
            .where(
                carInsuranceEntity.hospitalId.eq(hospitalId),
                carInsuranceEntity.treatmentYearMonth.startsWith(yearMonth)
            )
            .groupBy(carInsuranceEntity.treatmentYearMonth)
            .fetch()
    }

    override fun dataListTotal(hospitalId: String, yearMonth: String): CarInsuranceDto {
        return query.select(
            Projections.bean(
                CarInsuranceDto::class.java,
                carInsuranceEntity.treatmentYearMonth.substring(0, 4).`as`("treatmentYearMonth"),
                carInsuranceEntity.claimItemsCount.sum().`as`("claimItemsCount"),
                carInsuranceEntity.decisionTotalAmount.sum().`as`("decisionTotalAmount"),
                carInsuranceEntity.decisionPatientPayment.sum().`as`("decisionPatientPayment"),
                carInsuranceEntity.decisionAmount.sum().`as`("decisionAmount"),
            )
        ).from(carInsuranceEntity)
            .where(
                carInsuranceEntity.hospitalId.eq(hospitalId),
                carInsuranceEntity.treatmentYearMonth.startsWith(yearMonth)
            )
            .fetchOne()!!
    }
}