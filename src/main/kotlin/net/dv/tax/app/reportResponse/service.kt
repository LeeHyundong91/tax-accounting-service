package net.dv.tax.app.reportResponse

import net.dv.tax.app.common.VHospitalMemberRepository
import net.dv.tax.app.consulting.ConsultingReportRepository
import net.dv.tax.domain.consulting.ConsultingReportEntity
import net.dv.tax.domain.consulting.ReportResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReportResponseService(
    val repository: ReportResponseRepository,
    val consultingReportRepository: ConsultingReportRepository,
    val hospitalMemberRepository: VHospitalMemberRepository
): ReportResponseQueryCommand, ReportResponseOperationCommand {

    override fun response(options: ReportResponse.() -> Unit): ReportResponse {
        ReportResponse().apply(options).let { reportResponse ->
            val consultingReport = consultingReportRepository.findById(reportResponse.reportId!!).orElseThrow { IllegalArgumentException("not found report") }
            val approver = hospitalMemberRepository.findByAccountIdAndRole(reportResponse.approverId!!, "tax.ACC")

            if(consultingReport.status != ConsultingReportEntity.Status.PENDING) throw IllegalArgumentException("승인 대기 중이 아닌 리포트에 대해 응답할 수 없습니다!")

            if(reportResponse.isApproved == false) {
                consultingReport.status = ConsultingReportEntity.Status.REJECTED
                consultingReport.responseAt = LocalDateTime.now()

                val responseEntity = ReportResponseEntity(
                    report = consultingReport,
                    approver = approver.name,
                    reason = reportResponse.reason,
                    responseAt = LocalDateTime.now()
                )

                consultingReportRepository.save(consultingReport)
                repository.save(responseEntity)

                return mapToValueObject(responseEntity)

            } else {
                consultingReport.status = ConsultingReportEntity.Status.APPROVED
                consultingReport.responseAt = LocalDateTime.now()

                consultingReportRepository.save(consultingReport)

                return ReportResponse()
            }
        }
    }

    override fun fetch(options: ReportResponse.() -> Unit): List<ReportResponse> {
        ReportResponse().apply(options).let {reportResponse ->
            val consultingReport = consultingReportRepository.findById(reportResponse.reportId!!).orElseThrow { IllegalArgumentException("not found report") }
            val responseList = repository.findAllByReport(consultingReport)

            return responseList
                .asSequence()
                .map { responses -> mapToValueObject(responses) }
                .toList()
        }
    }

    override fun search(id: Long): ReportResponse {
        val reportResponse = repository.findById(id).orElseThrow { IllegalArgumentException("not found response") }
        return mapToValueObject(reportResponse)
    }

    private fun mapToValueObject(entity: ReportResponseEntity): ReportResponse {
        return ReportResponse(
            id = entity.id,
            reportId = entity.report?.id,
            approverId = entity.approver,
            reason = entity.reason,
            responseAt = entity.responseAt
        )
    }


}