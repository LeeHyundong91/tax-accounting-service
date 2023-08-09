package net.dv.tax.infra.orm

import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.consulting.ConsultingReport
import net.dv.tax.app.consulting.ConsultingReportQueryRepository
import net.dv.tax.domain.consulting.ConsultingReportEntity
import org.springframework.stereotype.Component

@Component
class ConsultingReportRepositoryImpl(private val factory: JPAQueryFactory): ConsultingReportQueryRepository {

    override fun fetch(query: ConsultingReport): List<ConsultingReportEntity> {
        TODO("Not yet implemented")
    }

    override fun getCount(query: ConsultingReport): Long {
        TODO("Not yet implemented")
    }

}
