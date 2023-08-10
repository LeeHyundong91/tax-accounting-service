package net.dv.tax.app.consulting

import net.dv.tax.app.employee.VHospitalMemberRepository
import net.dv.tax.domain.consulting.ConsultingReportEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ConsultingReportService(
    val repository: ConsultingReportRepository,
    val hospitalMemberRepository: VHospitalMemberRepository,
    val queryRepository: ConsultingReportQueryRepository
): ConsultingReportOperationCommand, ConsultingReportQueryCommand {

    override fun write(options: ConsultingReport.() -> Unit): ConsultingReport {
        ConsultingReport().apply(options).let { consultingReport ->
            val writer = hospitalMemberRepository.findByAccountId(consultingReport.writer!!)
            val approver = hospitalMemberRepository.findByAccountId(consultingReport.approver!!)

            val reportEntity = ConsultingReportEntity(
                hospitalId = consultingReport.hospitalId,
                year = consultingReport.year,
                seq = consultingReport.seq,
                period = consultingReport.beginPeriod?.let {begin ->
                    consultingReport.endPeriod?.let {end ->
                        ConsultingReportEntity.Period(
                            begin = begin,
                            end = end
                        )
                    }
                },
                writer = writer.name,
                approver = approver.name,
                openingAt = consultingReport.openingAt,
                visibleCount = consultingReport.visibleCount,
                createdAt = LocalDateTime.now()
            )

            println(reportEntity.status)

            repository.save(reportEntity)

            return mapToValueObject(reportEntity)
        }
    }

    override fun delete(options: ConsultingReport.() -> Unit): ConsultingReport {
        ConsultingReport().apply(options).let { consultingReport ->
            val report = repository.findById(consultingReport.id!!).orElseThrow { IllegalArgumentException("not found report") }

            if(report.status != ConsultingReportEntity.Status.WRITING)
                throw IllegalArgumentException("작성중이 아닌 리포트는 삭제 불가합니다.")

            repository.delete(report)

            return mapToValueObject(report)
        }
    }

    override fun update(options: ConsultingReport.() -> Unit): ConsultingReport {
        return ConsultingReport().apply(options).let { consultingReport ->
            val report = repository.findById(consultingReport.id!!).orElseThrow { IllegalArgumentException("not found report") }

            // Early exit for pending and approved statuses
            report.status?.let { validateReportStatus(it) }

            when (report.status) {
                ConsultingReportEntity.Status.REJECTED -> handleRejectedStatus(consultingReport, report)
                ConsultingReportEntity.Status.WRITING -> handleWritingStatus(consultingReport, report)
                else -> ConsultingReport()
            }
        }
    }

    private fun validateReportStatus(status: ConsultingReportEntity.Status) {
        when (status) {
            ConsultingReportEntity.Status.PENDING -> throw IllegalArgumentException("승인 대기중인 작성건은 수정이 불가합니다!")
            ConsultingReportEntity.Status.APPROVED -> throw IllegalArgumentException("이미 승인이 완료된 리포트는 수정이 불가합니다!")
            else -> { /* No action needed */ }
        }
    }

    private fun handleRejectedStatus(consultingReport: ConsultingReport, report: ConsultingReportEntity): ConsultingReport {
        var changed = false
        if (consultingReport.beginPeriod != null && report.period?.begin != consultingReport.beginPeriod) {
            changed = true
            report.period?.begin = consultingReport.beginPeriod!!
        }
        if (consultingReport.endPeriod != null && report.period?.end != consultingReport.endPeriod) {
            changed = true
            report.period?.end = consultingReport.endPeriod!!
        }
        if (consultingReport.openingAt != null && report.openingAt != consultingReport.openingAt) {
            changed = true
            report.openingAt = consultingReport.openingAt
        }
        if (consultingReport.visibleCount != null && report.visibleCount != consultingReport.visibleCount) {
            changed = true
            report.visibleCount = consultingReport.visibleCount
        }

        if (changed) {
            report.submittedAt = LocalDateTime.now()
            report.status = ConsultingReportEntity.Status.PENDING
            repository.save(report)
            return mapToValueObject(report)
        }

        return ConsultingReport()
    }

    private fun handleWritingStatus(consultingReport: ConsultingReport, report: ConsultingReportEntity): ConsultingReport {
        report.approver = consultingReport.approver
        report.status = ConsultingReportEntity.Status.PENDING
        report.submittedAt = LocalDateTime.now()
        repository.save(report)
        return mapToValueObject(report)
    }


    override fun search(options: ConsultingReport.() -> Unit): ConsultingReport {
        ConsultingReport().apply(options).let { consultingReport ->
            val report = repository.findById(consultingReport.id!!).orElseThrow { IllegalArgumentException("not found report") }
            countVisibleDays(report)
            return mapToValueObject(report)
        }
    }

    override fun fetch(options: ConsultingReport.() -> Unit): ConsultingReports {
        return ConsultingReport().apply(options).let {query ->
            val reportList = queryRepository.fetch(query).map { mapToValueObject(it) }

            ConsultingReports(
                reportList = reportList,
                reportCount = queryRepository.getCount(query)
            )
        }
    }

    //스케쥴링 필요
    private fun countVisibleDays(report: ConsultingReportEntity) {
        if (report.status == ConsultingReportEntity.Status.APPROVED) {
            report.visibleCount?.minus(1)
            repository.save(report)
        }
    }

    private fun mapToValueObject(entity: ConsultingReportEntity): ConsultingReport {
        return ConsultingReport(
            id = entity.id,
            hospitalId = entity.hospitalId,
            year = entity.year,
            seq = entity.seq,
            beginPeriod = entity.period?.begin,
            endPeriod = entity.period?.end,
            writer = entity.writer,
            approver = entity.approver,
            status = entity.status?.name,
            submittedAt = entity.submittedAt,
            responseAt = entity.responseAt,
            openingAt = entity.openingAt,
            visibleCount = entity.visibleCount,
            createdAt = entity.createdAt
        )
    }

}