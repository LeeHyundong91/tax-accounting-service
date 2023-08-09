package net.dv.tax.app.reportResponse

import net.dv.tax.domain.consulting.ConsultingReportEntity
import net.dv.tax.domain.consulting.ReportResponseEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReportResponseRepository: JpaRepository<ReportResponseEntity, Long> {

    fun findAllByReport(consultingReport: ConsultingReportEntity): List<ReportResponseEntity>

}
