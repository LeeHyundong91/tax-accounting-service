package net.dv.tax.domain.consulting

import jakarta.persistence.*
import net.dv.tax.domain.AbstractCodeConverter
import net.dv.tax.domain.Code
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


@Comment("컨설팅 리포트 Master")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "CONSULTING_REPORT")
@EntityListeners(AuditingEntityListener::class)
data class ConsultingReportEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "HOSPITAL_ID")
    val hospitalId: String? = null,

    @Column(name = "YEAR")
    @Comment("리포트 연도")
    val year: Int? = null,

    @Column(name = "CONSULT_SEQ")
    @Comment("리포트 차수")
    val seq: Int? = null,

    @Embedded
    val period: Period? = null,

    @Column(name = "WRITER")
    @Comment("작성자")
    val writer: String? = null,

    @Column(name = "APPROVER")
    @Comment("승인자")
    val approver: String? = null,

    @Column(name = "STATUS")
    @Comment("리포트 상황")
    val status: Status = Status.WRITING,

    @Column(name = "SUBMITTED_AT")
    @Comment("승인신청일")
    val submittedAt: LocalDateTime? = null,

    @Column(name = "RESPONSE_AT")
    @Comment("승인/거절일")
    val responseAt: LocalDateTime? = null,

    @Column(name = "OPENING_AT")
    @Comment("공개시작일")
    val openingAt: LocalDateTime? = null,

    @Column(name = "VISIBLE_COUNT")
    @Comment("공개일수")
    val visibleCount: Int? = 0,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime? = null,
) {
    @Embeddable
    class Period(
        @Column(name = "PERIOD_BEGIN")
        val begin: String,

        @Column(name = "PERIOD_END")
        val end: String,
    )

    enum class Status(override val code: String, override val label: String) : Code {
        WRITING("W", "작성중"),
        PENDING("P", "승인대기"),
        APPROVED("A", "승인완료"),
        REJECTED("R", "승인거부");

        companion object {
            fun code(code: String) = Status.values().first { it.code == code}
        }
    }

    @Converter(autoApply = true)
    class StatusConverter: AbstractCodeConverter<Status>({Status.code(it)})
}
