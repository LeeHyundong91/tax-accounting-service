package net.dv.tax.repository.sales

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.CarInsuranceEntity
import net.dv.tax.domain.sales.QCarInsuranceEntity.carInsuranceEntity
import net.dv.tax.dto.sales.CarInsuranceListDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class CarInsuranceSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(CarInsuranceEntity::class.java), CarInsuranceSupport {
    override fun groupingList(hospitalId: String, year: String): List<CarInsuranceListDto> {
    /*`as`*/
        return query.select(
            Projections.bean(
                CarInsuranceListDto::class.java,
                carInsuranceEntity.billingAmount.sum().`as`("billingAmount"),
                carInsuranceEntity.decisionAmount.sum().`as`("decisionAmount"),
                carInsuranceEntity.dataPeriod,
            )
        ).from(carInsuranceEntity)
            .where(
                carInsuranceEntity.hospitalId.eq(hospitalId),
                carInsuranceEntity.dataPeriod.startsWith(year)
            ).groupBy(carInsuranceEntity.dataPeriod)
            .fetch()

    }

}