package net.dv.tax.app.reportResponse

import java.time.LocalDateTime

interface ReportResponseOperationCommand {

    fun response(options: ReportResponse.() -> Unit): ReportResponse

}

interface ReportResponseQueryCommand {

    fun fetch(options: ReportResponse.() -> Unit): List<ReportResponse>

    fun search(id: Long): ReportResponse
}

data class ReportResponse(
    var id: Long? = null,
    var reportId: Long? = null,
    var approverId: String? = null,
    var reason: String? = null,
    var isApproved: Boolean? = null,
    var responseAt: LocalDateTime? = null
)