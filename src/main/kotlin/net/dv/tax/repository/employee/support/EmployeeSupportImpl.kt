package net.dv.tax.repository.employee.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeAttachFileEntity
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.QEmployeeEntity.employeeEntity
import net.dv.tax.domain.employee.QEmployeeAttachFileEntity.employeeAttachFileEntity
import net.dv.tax.dto.employee.EmployeeQueryDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Repository
class EmployeeSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), EmployeeSupport {


    override fun employeeListCnt(hospitalId: String, employeeQueryDto: EmployeeQueryDto): Long {

        val builder = getBuilder(hospitalId, employeeQueryDto)

        return query
            .select(employeeEntity.count())
            .from(employeeEntity)
            .where(builder)
            .fetchFirst()
    }


    override fun employeeList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): List<EmployeeEntity> {

        val realOffset = employeeQueryDto.offset!! * employeeQueryDto.size!!;

        val builder = getBuilder(hospitalId, employeeQueryDto)

        return query
            .select(employeeEntity)
            .from(employeeEntity)
            .where(builder)
            .offset(realOffset)
            .limit(employeeQueryDto.size)
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

    fun getBuilder(hospitalId: String, employeeQueryDto: EmployeeQueryDto): BooleanBuilder{

        val builder = BooleanBuilder()
        builder.and(employeeEntity.hospitalId.eq(hospitalId))

        //이름
        employeeQueryDto.name?.also {
            builder.and(employeeEntity.name.contains(it))
        }

        //주민번호
        employeeQueryDto.regidentNumber?.also {
            builder.and(employeeEntity.residentNumber.contains(it))
        }

        //시작일
        employeeQueryDto.from?.also {
            if( it.length > 0 ) {
                val fromDateTime = LocalDateTime.parse(it + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                builder.and(employeeEntity.createdAt.after(fromDateTime.minusDays(1)));
            }
        }

        //종료일
        employeeQueryDto.to?.also {
            if( it.length > 0 ) {
                val toDateTime = LocalDateTime.parse(it + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                builder.and(employeeEntity.createdAt.before(toDateTime.plusDays(1)));
            }
        }

        return builder
    }
}