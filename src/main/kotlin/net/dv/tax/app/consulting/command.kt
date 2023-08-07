package net.dv.tax.app.consulting

import java.time.LocalDateTime

interface ConsultingReportOperationCommand {

    fun write(options: ConsultingReport.() -> Unit): ConsultingReport

    fun update(options: ConsultingReport.() -> Unit): ConsultingReport

    fun delete(options: ConsultingReport.() -> Unit): ConsultingReport



}

interface ConsultingReportQueryCommand {


    fun search(options: ConsultingReport.() -> Unit): ConsultingReport

    fun fetch(options: ConsultingReport.() -> Unit): ConsultingReport

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

    var size: Long? = 0,
    var offset: Long? = 30
)

data class ConsultingReports(
    var reportCount: Long? = null,
    var reportList: List<ConsultingReport>? = null
)