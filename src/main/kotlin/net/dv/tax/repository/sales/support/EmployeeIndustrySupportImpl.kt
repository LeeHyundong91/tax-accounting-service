package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
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
    private fun baseQuery(hospitalId: String, yearMonth: String): JPAQuery<EmployeeIndustryDto> {
        return query.select(
            Projections.bean(
                EmployeeIndustryDto::class.java,
                employeeIndustryEntity.treatmentYearMonth.substring(0, 7).`as`("treatmentYearMonth"),
                employeeIndustryEntity.treatmentYearMonth.count().`as`("totalCount"),
                employeeIndustryEntity.billingAmount.sum().`as`("billingAmount"),
                employeeIndustryEntity.paymentAmount.sum().`as`("paymentAmount"),
                employeeIndustryEntity.incomeTax.sum().`as`("incomeTax"),
                employeeIndustryEntity.residenceTax.sum().`as`("residenceTax"),
                employeeIndustryEntity.actualPayment.sum().`as`("actualPayment"),
            )
        ).from(employeeIndustryEntity)
            .where(
                employeeIndustryEntity.hospitalId.eq(hospitalId),
                employeeIndustryEntity.treatmentYearMonth.startsWith(yearMonth)
            )
    }

    override fun dataList(hospitalId: String, yearMonth: String): List<EmployeeIndustryDto> {
        /*`as`*/
        return baseQuery(hospitalId, yearMonth)
            .groupBy(employeeIndustryEntity.treatmentYearMonth.substring(0, 7))
            .fetch()
    }

    override fun dataTotalList(hospitalId: String, yearMonth: String): EmployeeIndustryDto? {
        return baseQuery(hospitalId, yearMonth)
            .fetchOne()
        //?: throw EntityNotFoundException("No EmployeeIndustryDto found for hospitalId $hospitalId and yearMonth $yearMonth")
    }

}