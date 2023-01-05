package net.dv.tax.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "vaccine_sales")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class VaccineSalesEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Comment("병원 아이디")
    val hospitalId: Int,

    @Comment("년도")
    val year: Int,

    @Comment("월")
    val month: Int? = null,

    @Comment("지급완료 건수")
    var payCount: Long?,

    @Comment("지급금액")
    var payAmount: Long?,

    @Comment("작성자")
    var writer: String,

    @CreatedDate
    @Column(updatable = false, name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedAt: LocalDateTime? = null


)
