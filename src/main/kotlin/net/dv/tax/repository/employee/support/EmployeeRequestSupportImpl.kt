package net.dv.tax.repository.employee.support

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.domain.employee.QEmployeeRequestEntity.employeeRequestEntity
import net.dv.tax.enum.employee.RequestState


import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EmployeeRequestSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), EmployeeRequestSupport {

    override fun employeeRequestList(hospitalId: String, offset:Long, size:Long,  type: String?, keyword: String?): List<EmployeeRequestEntity> {
        /*val builder = BooleanBuilder()
        builder.and(workEmployeeEntity.)*/

       return query
            .select(employeeRequestEntity)
            .from(employeeRequestEntity)
            .where(employeeRequestEntity.hospitalId.eq(hospitalId))
            .where(employeeRequestEntity.requestState.notEqualsIgnoreCase(RequestState.RequestState_D.requestStateCode))
            .offset(offset)
            .limit(size)
            .fetch()
    }

}