package net.dv.tax.repository.employee.support

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeAttachFileEntity
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.QEmployeeEntity.employeeEntity
import net.dv.tax.domain.employee.QEmployeeAttachFileEntity.employeeAttachFileEntity
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EmployeeSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), EmployeeSupport {

    override fun employeeList(hospitalId: String, offset:Long, size:Long,  type: String?, keyword: String?): List<EmployeeEntity> {
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

    override fun employeeFileList(employeeId: Long): List<EmployeeAttachFileEntity> {
        return query
            .select(employeeAttachFileEntity)
            .from(employeeAttachFileEntity)
            .where(employeeAttachFileEntity.employee.id.eq(employeeId))
            .where(employeeAttachFileEntity.useYn.eq("Y"))
            .fetch()
    }
}