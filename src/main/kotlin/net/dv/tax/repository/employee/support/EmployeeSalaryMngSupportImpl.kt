package net.dv.tax.repository.employee.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeSalaryMngEntity
import net.dv.tax.domain.employee.QEmployeeSalaryMngEntity.employeeSalaryMngEntity
import net.dv.tax.dto.employee.EmployeeQueryDto

import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Repository
class EmployeeSalaryMngSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeSalaryMngEntity::class.java), EmployeeSalaryMngSupport {


    override fun getSalaryMngListCnt( hospitalId: String, employeeQueryDto: EmployeeQueryDto): Long {

        val builder = getBuilder(hospitalId, employeeQueryDto)

        return query
            .select(employeeSalaryMngEntity.count())
            .from(employeeSalaryMngEntity)
            .where(builder)
            .fetchFirst()

    }

    override fun getSalaryMngList( hospitalId: String, employeeQueryDto: EmployeeQueryDto): List<EmployeeSalaryMngEntity> {

        val realOffset = employeeQueryDto.offset!! * employeeQueryDto.size!!;

        val builder = getBuilder(hospitalId, employeeQueryDto)

        return query
            .select(employeeSalaryMngEntity)
            .from(employeeSalaryMngEntity)
            .where(builder)
            .offset(realOffset)
            .limit(employeeQueryDto.size)
            .fetch()

    }

    fun getBuilder(hospitalId: String, employeeQueryDto: EmployeeQueryDto): BooleanBuilder{

        val builder = BooleanBuilder()
        builder.and(employeeSalaryMngEntity.hospitalId.eq(hospitalId))

        if( !employeeQueryDto.year.isNullOrEmpty() ){
            builder.and(employeeSalaryMngEntity.paymentsAt.startsWith(employeeQueryDto.year))
        }

        if( !employeeQueryDto.sendDate.isNullOrEmpty() ){
            
            //전날 23:59:59 이후부터
            val fromDateTime = LocalDateTime.parse(employeeQueryDto.sendDate + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            builder.and(employeeSalaryMngEntity.createdAt.after(fromDateTime.minusDays(1)))

            //다음날 00:00:00 까지
            val toDateTime = LocalDateTime.parse(employeeQueryDto.sendDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            builder.and(employeeSalaryMngEntity.createdAt.before(toDateTime.plusDays(1)))

        }

        return builder
    }

    override fun getSalaryMngDeleteList(hospitalId: String, paymentAt: String): List<EmployeeSalaryMngEntity> {
        return query
            .select(employeeSalaryMngEntity)
            .from(employeeSalaryMngEntity)
            .where(employeeSalaryMngEntity.hospitalId.eq(hospitalId))
            .where(employeeSalaryMngEntity.paymentsAt.startsWith(paymentAt))
            .fetch()

    }

}