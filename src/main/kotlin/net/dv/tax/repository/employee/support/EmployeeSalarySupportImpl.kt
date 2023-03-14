package net.dv.tax.repository.employee.support

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.QEmployeeSalaryEntity.employeeSalaryEntity
import net.dv.tax.domain.employee.EmployeeSalaryEntity

import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EmployeeSalarySupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeSalaryEntity::class.java), EmployeeSalarySupport {

    override fun getSalaryDeleteList(hospitalId: String, paymentAt: String): List<EmployeeSalaryEntity> {
        return query
            .select(employeeSalaryEntity)
            .from(employeeSalaryEntity)
            .where(employeeSalaryEntity.hospitalId.eq(hospitalId))
            .where(employeeSalaryEntity.paymentsAt.startsWith(paymentAt))
            .fetch()
    }

}