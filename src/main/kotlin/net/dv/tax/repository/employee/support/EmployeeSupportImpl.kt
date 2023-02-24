package net.dv.tax.repository.employee.support

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.QEmployeeEntity.employeeEntity
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EmployeeSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), EmployeeSupport {

    override fun employeeRequestList(hospitalId: String, offset:Long, size:Long,  type: String?, keyword: String?): List<EmployeeEntity> {
        /*val builder = BooleanBuilder()
        builder.and(workEmployeeEntity.)*/

        return query
            .select(employeeEntity)
            .from(employeeEntity)
            .where(employeeEntity.hospitalId.eq(hospitalId))
            .offset(offset)
            .limit(size)
            .fetch()
    }
}