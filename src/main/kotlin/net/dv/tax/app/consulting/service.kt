package net.dv.tax.app.consulting

import net.dv.tax.app.employee.VHospitalMemberRepository
import net.dv.tax.domain.consulting.ConsultingReportEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ConsultingReportService(
    val repository: ConsultingReportRepository,
    val hospitalMemberRepository: VHospitalMemberRepository
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

    override fun search(options: ConsultingReport.() -> Unit): ConsultingReport {
        ConsultingReport().apply(options).let { consultingReport ->
            val report = repository.findById(consultingReport.id!!).orElseThrow { IllegalArgumentException("not found report") }
            return mapToValueObject(report)
        }
    }

    override fun fetch(options: ConsultingReport.() -> Unit): ConsultingReport {
        TODO("Not yet implemented")
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