package net.dv.tax.domain.sales

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class SalesOtherBenefitsEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id: Long?,

    @Comment("병원 아이디")
    var hospitalId: String,

    @Comment("기간 yyyy-MM")
    var dataPeriod: String,

    @Comment("항목명")
    var itemName: String,

    @Comment("본인부담금")
    var ownCharge: Long? = 0,

    @Comment("기관부담금")
    val agencyExpenses: Long? = 0,

    @Comment("합계")
    val totalAmount: Long? = 0,

    @NotNull
    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    )