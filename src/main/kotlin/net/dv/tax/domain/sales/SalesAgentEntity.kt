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

    @Column(name = "RECEIVE_DATA_ID")
    var receiveDataId: Long,

    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("기간")
    @Column(name = "APPROVAL_YEAR_MONTH")
    val approvalYearMonth: String? = null,

    @Comment("건수")
    @Column(name = "SALES_COUNT")
    val salesCount: Long? = null,

    @Comment("총매출액")
    @Column(name = "TOTAL_SALES")
    val totalSales: Long? = null,

    @Comment("판매(결제)대행업체 상호")
    @Column(name = "AGENT_NAME")
    val agentName: String? = null,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
