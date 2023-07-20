package net.dv.tax.app.employee.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeAttachFileEntity
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.QEmployeeEntity.employeeEntity
import net.dv.tax.domain.employee.QEmployeeAttachFileEntity.employeeAttachFileEntity
import net.dv.tax.app.dto.employee.EmployeeQueryDto
import net.dv.tax.app.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDate
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
            .orderBy(employeeEntity.createdAt.desc())
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
            .orderBy(employeeAttachFileEntity.createdAt.desc())
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

        //입사일
        if( !employeeQueryDto.joinAt.isNullOrEmpty()) {
            val joinAt = LocalDate.parse(employeeQueryDto.joinAt, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            builder.and(employeeEntity.joinAt.eq(joinAt))
        }

        //사원번호
        if( !employeeQueryDto.employeeCode.isNullOrEmpty()) {
            builder.and(employeeEntity.employeeCode.contains(employeeQueryDto.employeeCode))
        }

        // 재직구분
        if ( !employeeQueryDto.jobClass.isNullOrEmpty() ){
            builder.and(employeeEntity.jobClass.eq(employeeQueryDto.jobClass))
        }

        return builder
    }
}