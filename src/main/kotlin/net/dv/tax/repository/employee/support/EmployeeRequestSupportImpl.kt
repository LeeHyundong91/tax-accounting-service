package net.dv.tax.repository.employee.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.employee.EmployeeEntity
import net.dv.tax.domain.employee.EmployeeRequestEntity
import net.dv.tax.domain.employee.QEmployeeRequestEntity.employeeRequestEntity
import net.dv.tax.dto.employee.EmployeeQueryDto
import net.dv.tax.enums.employee.RequestState
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Repository
class EmployeeRequestSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(EmployeeEntity::class.java), EmployeeRequestSupport {


    override fun employeeRequestList(hospitalId: String, employeeQueryDto: EmployeeQueryDto): List<EmployeeRequestEntity> {

        val realOffset = employeeQueryDto.offset!! * employeeQueryDto.size!!;

        val builder = getBuilder(hospitalId, employeeQueryDto)

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
        val builder = getBuilder(hospitalId, employeeQueryDto)

        return query
            .select(employeeRequestEntity.count())
            .from(employeeRequestEntity)
            .where(builder)
            .fetchFirst()
    }

    fun getBuilder(hospitalId: String, employeeQueryDto: EmployeeQueryDto): BooleanBuilder{

        val builder = BooleanBuilder()
        builder.and(employeeRequestEntity.hospitalId.eq(hospitalId))
        builder.and(employeeRequestEntity.requestState.notEqualsIgnoreCase(RequestState.RequestState_D.requestStateCode))

        //이름
        if( !employeeQueryDto.name.isNullOrEmpty()) {
            builder.and(employeeRequestEntity.name.contains(employeeQueryDto.name))
        }

        //주민번호
        if( !employeeQueryDto.regidentNumber.isNullOrEmpty()) {
            builder.and(employeeRequestEntity.residentNumber.contains(employeeQueryDto.regidentNumber))
        }

        //입사일
        if( !employeeQueryDto.joinAt.isNullOrEmpty()) {
            val joinAt = LocalDate.parse(employeeQueryDto.joinAt, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            builder.and(employeeRequestEntity.joinAt.eq(joinAt))
        }

        //사원번호
        if( !employeeQueryDto.employeeCode.isNullOrEmpty()) {
            builder.and(employeeRequestEntity.employeeCode.contains(employeeQueryDto.employeeCode))
        }

        // 재직구분
        if ( !employeeQueryDto.jobClass.isNullOrEmpty() ){
            builder.and(employeeRequestEntity.jobClass.eq(employeeQueryDto.jobClass))
        }


        return builder
    }

}