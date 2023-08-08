package net.dv.tax.infra.endpoint.consulting

import net.dv.tax.Application
import net.dv.tax.app.reportResponse.ReportResponse
import net.dv.tax.app.reportResponse.ReportResponseOperationCommand
import net.dv.tax.app.reportResponse.ReportResponseQueryCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/${Application.VERSION}/report/response")
class ReportResponseController(
    val operationCommand: ReportResponseOperationCommand,
    val queryCommand: ReportResponseQueryCommand
) {

    //응답하기
    @PostMapping
    fun response(@RequestBody request: ReportResponseDto): ResponseEntity<ReportResponse> {
        return ResponseEntity.ok(operationCommand.response {
            this.reportId = request.reportId
            this.approverId = request.approverId
            this.isApproved = request.isApproved
            this.reason = request.reason
        })
    }

    //리포트에 붙어있는 승인거절 이유 조회
    @GetMapping
    fun fetch(request: ReportResponseDto): ResponseEntity<List<ReportResponse>> {
        return ResponseEntity.ok(queryCommand.fetch {
            this.reportId = request.reportId
        })
    }

    @GetMapping("/{id}")
    fun search(@PathVariable ("id") id: Long): ResponseEntity<ReportResponse> {
        return ResponseEntity.ok(queryCommand.search(id))
    }

}

data class ReportResponseDto(
    val id: Long? = null,
    val reportId: Long? = null,
    val approverId: String? = null,
    val isApproved: Boolean? = null,
    val reason: String? = null
)