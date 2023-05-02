package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Comment("판매대행 매출")
@Suppress("JpaAttributeTypeInspection")
@Entity
@Table(name = "sales_agent")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesAgentEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id: Long? = null,

    var receiveDataId: Long,

    var hospitalId: String? = null,

    @Comment("기간")
    val approvalYearMonth: String? = null,

    @Comment("건수")
    val salesCount: Long? = null,

    @Comment("총매출액")
    val totalSales: Long? = null,

    @Comment("판매(결제)대행업체 상호")
    val agentName: String? = null,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),


    )
