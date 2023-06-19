package net.dv.tax.domain.consulting

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("매출명세서 환자결제분")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "consulting_report")
@EntityListeners(AuditingEntityListener::class)
data class ConsultingReportEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val hospitalId: String? = null,

    val resultYearMonth: String? = null,

    @Comment("확정자")
    val confirmUser: String? = null,

    @Comment("승인자")
    val acceptUser: String? = null,

    @Comment("공개 여부")
    @Column(name = "IS_VISIBLE")
    val isVisible: Boolean? = false,

    @Comment("공개일자")
    val visibleCount: Int? = 0,

    @Comment("삭제여부")
    @Column(name = "IS_DELETE")
    val isDelete: Boolean? = false,

    @Comment("작성자")
    val writer: String? = null,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),



)
