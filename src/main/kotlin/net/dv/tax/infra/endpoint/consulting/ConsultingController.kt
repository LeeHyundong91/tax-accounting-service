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
    fun fetch(query: ConsultingReportDto): ResponseEntity<ConsultingReports> {
        return ResponseEntity.ok(queryCommand.fetch {
            this.size = query.size
            this.offset = query.offset
            this.year = query.year
            this.hospitalId = query.hospitalId
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
        var size: Long? = 0,
        var offset: Long? = 30,
        var hospitalId: String? = null,
        var year: Int? = null,
        var seq: Int? = null,
        var beginPeriod: String? = null,
        var endPeriod: String? = null,
        var writer: String? = null,
        var approver: String? = null,
        var reason: String? = null,
        var status: String? = null,
        var submittedAt: LocalDateTime? = null,
        var responseAt: LocalDateTime? = null,
        var openingAt: LocalDateTime? = null,
        var visibleCount: Int? = null,
        var createdAt: LocalDateTime? = null,
    )
}