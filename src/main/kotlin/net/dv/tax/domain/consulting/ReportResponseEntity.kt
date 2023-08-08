package net.dv.tax.domain.consulting

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "REPORT_RESPONSE")
class ReportResponseEntity (

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_ID")
    var report: ConsultingReportEntity? = null,

    @Column(name = "APPROVER_ID")
    var approver: String? = null,

    @Column(name = "REASON")
    var reason: String? = null,

    @Column(name = "RESPONSE_AT")
    var responseAt: LocalDateTime? = LocalDateTime.now()

)
