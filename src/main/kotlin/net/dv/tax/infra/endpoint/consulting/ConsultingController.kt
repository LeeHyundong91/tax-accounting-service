package net.dv.tax.infra.endpoint.consulting

import net.dv.tax.Application
import net.dv.tax.app.consulting.ConsultingReport
import net.dv.tax.app.consulting.ConsultingReportOperationCommand
import net.dv.tax.app.consulting.ConsultingReportQueryCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    //세무담당자 목록 조회하기

    //세무담당자 작성중인 리포트 삭제하기
    @DeleteMapping
    fun delete(request: ConsultingReport): ResponseEntity<ConsultingReport> {
        return ResponseEntity.ok(operationCommand.delete {
            this.id = request.id
        })
    }
}