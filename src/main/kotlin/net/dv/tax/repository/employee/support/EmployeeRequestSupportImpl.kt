package net.dv.tax.repository.employee.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.domain.employee.QEmployeeRequestEntity.employeeRequestEntity
import net.dv.tax.domain.employee.QEmployeeSalaryMngEntity
import net.dv.tax.dto.employee.EmployeeQueryDto
import net.dv.tax.enum.employee.RequestState


import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Repository
class EmployeeRequestSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), EmployeeRequestSupport {


    override fun employeeRequestList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): List<EmployeeRequestEntity> {

        val realOffset = employeeQueryDto.offset!! * employeeQueryDto.size!!;

        val builder = BooleanBuilder()
        builder.and(employeeRequestEntity.hospitalId.eq(hospitalId))
        builder.and(employeeRequestEntity.requestState.notEqualsIgnoreCase(RequestState.RequestState_D.requestStateCode))

        //이름
        employeeQueryDto.name?.also {
            if( it.length > 0 ) builder.and(employeeRequestEntity.name.contains(it))
        }

        //주민번호
        employeeQueryDto.regidentNumber?.also {
            if( it.length > 0 ) builder.and(employeeRequestEntity.residentNumber.contains(it))
        }

        //시작일
        employeeQueryDto.from?.also {
            if( it.length > 0 ) {
                val fromDateTime = LocalDateTime.parse(it + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                builder.and(QEmployeeSalaryMngEntity.employeeSalaryMngEntity.createdAt.after(fromDateTime.minusDays(1)));
            }
        }

        //종료일
        employeeQueryDto.to?.also {
            if( it.length > 0 ) {
                val toDateTime = LocalDateTime.parse(it + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                builder.and(QEmployeeSalaryMngEntity.employeeSalaryMngEntity.createdAt.before(toDateTime.plusDays(1)));
            }
        }

       return query
            .select(employeeRequestEntity)
            .from(employeeRequestEntity)
            .where(builder)
            .offset(realOffset)
            .limit(employeeQueryDto.size)
            .fetch()
    }

    //총 개수 계산
    override fun employeeRequestListCnt(hospitalId: String, employeeQueryDto: EmployeeQueryDto): Long {
        val builder = BooleanBuilder()
        builder.and(employeeRequestEntity.hospitalId.eq(hospitalId))
        builder.and(employeeRequestEntity.requestState.notEqualsIgnoreCase(RequestState.RequestState_D.requestStateCode))

        //이름
        employeeQueryDto.name?.also {
            if( it.length > 0 ) builder.and(employeeRequestEntity.name.contains(it))
        }

        //주민번호
        employeeQueryDto.regidentNumber?.also {
            if( it.length > 0 ) builder.and(employeeRequestEntity.residentNumber.contains(it))
        }

        //시작일
        employeeQueryDto.from?.also {
            if( it.length > 0 ) {
                val fromDateTime = LocalDateTime.parse(it + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                builder.and(QEmployeeSalaryMngEntity.employeeSalaryMngEntity.createdAt.after(fromDateTime.minusDays(1)));
            }
        }

        //종료일
        employeeQueryDto.to?.also {
            if( it.length > 0 ) {
                val toDateTime = LocalDateTime.parse(it + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                builder.and(QEmployeeSalaryMngEntity.employeeSalaryMngEntity.createdAt.before(toDateTime.plusDays(1)));
            }
        }

        return query
            .select(employeeRequestEntity.count())
            .from(employeeRequestEntity)
            .where(builder)
            .fetchFirst()
    }

}