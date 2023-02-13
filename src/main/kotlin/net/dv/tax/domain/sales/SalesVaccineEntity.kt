package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "sales_vaccine")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesVaccineEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Comment("병원 아이디")
    var hospitalId: String? = null,

    @Comment("년도")
    val year: Int? = null,

    @Comment("월")
    val month: Int? = null,

    @Comment("지급완료 건수")
    var payCount: Long? = 0,

    @Comment("지급금액")
    var payAmount: Long? = 0,

    @Comment("작성자")
    var writer: String? = null,

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    val dataPeriod: String? = year.toString() + "-" + month.toString() + "-01",

    @CreatedDate
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )
