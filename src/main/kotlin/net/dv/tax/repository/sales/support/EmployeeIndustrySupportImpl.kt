package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.EmployeeIndustryEntity
import net.dv.tax.domain.sales.QEmployeeIndustryEntity.employeeIndustryEntity
import net.dv.tax.dto.sales.EmployeeIndustryDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EmployeeIndustrySupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeIndustryEntity::class.java), EmployeeIndustrySupport {
    override fun groupingList(hospitalId: String, year: String): List<EmployeeIndustryDto> {
    /*`as`*/
        return query.select(
            Projections.bean(
                EmployeeIndustryDto::class.java,
                employeeIndustryEntity.billingAmount.sum().`as`("billingAmount"),
                employeeIndustryEntity.paymentAmount.sum().`as`("paymentAmount"),
                employeeIndustryEntity.actualPayment.sum().`as`("actualPayment"),
                employeeIndustryEntity.incomeTax.sum().`as`("incomeTax"),
                employeeIndustryEntity.residenceTax.sum().`as`("residenceTax"),
                employeeIndustryEntity.dataPeriod,
                employeeIndustryEntity.dataPeriod.count().`as`("billingCount")
            )
        ).from(employeeIndustryEntity)
            .where(
                employeeIndustryEntity.hospitalId.eq(hospitalId),
                employeeIndustryEntity.dataPeriod.startsWith(year)
            ).groupBy(employeeIndustryEntity.dataPeriod)
            .fetch()

    }

}