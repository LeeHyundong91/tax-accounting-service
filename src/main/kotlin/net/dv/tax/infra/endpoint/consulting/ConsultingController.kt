package net.dv.tax.infra.endpoint.consulting

import net.dv.tax.Application
import net.dv.tax.app.consulting.ConsultingReport
import net.dv.tax.app.consulting.ConsultingReportOperationCommand
import net.dv.tax.app.consulting.ConsultingReportQueryCommand
import net.dv.tax.app.consulting.ConsultingReports
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/${Application.VERSION}/consulting/report")
class ConsultingController(
    val operationCommand: ConsultingReportOperationCommand,
    val queryCommand: ConsultingReportQueryCommand
) {

    //세무담당자 컨설팅리포트 등록하기
    @PostMapping
    fun write(@RequestBody request: ConsultingReport): ResponseEntity<ConsultingReport> {
        return ResponseEntity.ok(operationCommand.write {
            this.hospitalId = request.hospitalId
            this.year = request.year
            this.beginPeriod = request.beginPeriod
            this.endPeriod = request.endPeriod
            this.writer = request.writer
            this.approver = request.approver
            this.openingAt = request.openingAt
            this.visibleCount = request.visibleCount
        })
    }

    /**
     * 응답대기에서 수정불가!
     * 작성중일때 수정가능!
     * 작성중 -> 승인요청 + 승인관리자 선택하기
     * */
    @PatchMapping
    fun update(@RequestBody request: ConsultingReportDto): ResponseEntity<ConsultingReport> {
        return ResponseEntity.ok(operationCommand.update {
            this.id = request.id
            this.year = request.year
            this.beginPeriod = request.beginPeriod
            this.endPeriod = request.endPeriod
            this.approver = request.approver
            this.openingAt = request.openingAt
            this.visibleCount = request.visibleCount
        })
    }

    //세무담당자 목록 조회하기
    @GetMapping("/fetch")
    fun fetch(query: ConsultingReport): ResponseEntity<ConsultingReports> {
        return ResponseEntity.ok(queryCommand.fetch {
            this.size = query.size
            this.offset = query.offset
            this.year = query.year
            this.seq = query.seq
            this.beginPeriod = query.beginPeriod
            this.endPeriod = query.endPeriod
            this.writer = query.writer
            this.approver = query.approver
            this.status = query.status
            this.submittedAt = query.submittedAt
            this.responseAt = query.responseAt
            this.openingAt = query.openingAt
            this.visibleCount = query.visibleCount
            this.createdAt = query.createdAt
        })
    }

    //세무담당자 작성중인 리포트 삭제하기
    @DeleteMapping
    fun delete(request: ConsultingReport): ResponseEntity<ConsultingReport> {
        return ResponseEntity.ok(operationCommand.delete {
            this.id = request.id
        })
    }

    data class ConsultingReportDto(
        val id: Long? = 0,
        val size: Long? = 0,
        val offset: Long? = 30,
        val year: Int? = null,
        val seq: Int? = null,
        val beginPeriod: String? = null,
        val endPeriod: String? = null,
        val writer: String? = null,
        val approver: String? = null,
        val reason: String? = null,
        val status: String? = null,
        val submittedAt: LocalDateTime? = null,
        val responseAt: LocalDateTime? = null,
        val openingAt: LocalDateTime? = null,
        val visibleCount: Int? = null,
        val createdAt: LocalDateTime? = null,
    )
}