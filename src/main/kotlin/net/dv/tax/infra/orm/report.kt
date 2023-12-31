package net.dv.tax.infra.orm

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.consulting.ConsultingReportQueryRepository
import net.dv.tax.domain.consulting.ConsultingReportEntity
import net.dv.tax.domain.consulting.QConsultingReportEntity.consultingReportEntity
import net.dv.tax.infra.endpoint.consulting.ConsultingController
import org.springframework.stereotype.Component

@Component
class ConsultingReportRepositoryImpl(private val factory: JPAQueryFactory): ConsultingReportQueryRepository {

    override fun fetch(query: ConsultingController.ConsultingReportDto): List<ConsultingReportEntity> {
        val offset = query.offset!!* query.size!!

        var builder = getReportBuilder(query)

        return factory
            .select(consultingReportEntity)
            .from(consultingReportEntity)
            .where(builder)
            .orderBy(consultingReportEntity.year.desc())
            .offset(offset)
            .limit(query.size!!)
            .fetch()
    }

    override fun getCount(query: ConsultingController.ConsultingReportDto): Long {
        var builder = getReportBuilder(query)

        return factory
            .select(consultingReportEntity.count())
            .from(consultingReportEntity)
            .where(builder)
            .fetchFirst()
    }

    fun getReportBuilder(query: ConsultingController.ConsultingReportDto): BooleanBuilder {
        val builder = BooleanBuilder()

        //병원
        if(!query.hospitalId.isNullOrEmpty())
            builder.and(consultingReportEntity.hospitalId.eq(query.hospitalId))

        //status
        if(!query.status.isNullOrEmpty()) {
            builder.and(consultingReportEntity.status.eq(ConsultingReportEntity.Status.valueOf(query.status!!)))
        }

        //approver
        if(!query.approver.isNullOrEmpty()) {
            builder.and(consultingReportEntity.approver.eq(query.approver))
        }

        //writer
        if(!query.writer.isNullOrEmpty()) {
            builder.and(consultingReportEntity.writer.eq(query.writer))
        }

        //자료기준 (begin - end)
        if(!query.beginPeriod.isNullOrEmpty() && !query.endPeriod.isNullOrEmpty()) {
            val beginMonth = query.beginPeriod
            val endMonth = query.endPeriod

            builder.and(
                Expressions.stringTemplate("cast({0} as int)", consultingReportEntity.period.begin)
                    .goe(beginMonth)
                    .and(
                        Expressions.stringTemplate("cast({0} as int)", consultingReportEntity.period.end)
                            .loe(endMonth)
                    )
            )

        }

        query.year?.let {
            builder.and(consultingReportEntity.year.eq(query.year))
        }

        query.seq?.let {
            builder.and(consultingReportEntity.seq.eq(query.seq))
        }

        return builder
    }

}
