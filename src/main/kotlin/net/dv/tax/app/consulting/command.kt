package net.dv.tax.app.consulting

import net.dv.tax.infra.endpoint.consulting.ConsultingController
import java.time.LocalDateTime

interface ConsultingReportOperationCommand {

    fun write(options: ConsultingReport.() -> Unit): ConsultingReport

    fun delete(options: ConsultingReport.() -> Unit): ConsultingReport

    fun update(options: ConsultingReport.() -> Unit): ConsultingReport

}

interface ConsultingReportQueryCommand {


    fun search(options: ConsultingReport.() -> Unit): ConsultingReport

    fun fetch(options: ConsultingController.ConsultingReportDto.() -> Unit): ConsultingReports

}

data class ConsultingReport(
    var id: Long? = null,
    var hospitalId: String? = null,
    var year: Int? = null,
    var seq: Int? = null,
    var beginPeriod: String? = null,
    var endPeriod: String? = null,
    var writer: String? = null,
    var approver: String? = null,
    var status: String? = null,
    var submittedAt: LocalDateTime? = null,
    var responseAt: LocalDateTime? = null,
    var openingAt: LocalDateTime? = null,
    var visibleCount: Int? = null,
    var createdAt: LocalDateTime? = null,
)

data class ConsultingReports(
    var reportCount: Long? = null,
    var reportList: List<ConsultingReport>? = null
)