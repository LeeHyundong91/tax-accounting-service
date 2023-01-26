package net.dv.tax.domain.common

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "accounting_data")
@Comment("매입 엑셀 업로드 데이터")
@Suppress("JpaAttributeTypeInspection")
@DynamicUpdate
data class AccountingDataEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var hospitalId: Int? = null,

    var dataCategory: String? = null,

    var isApply: Boolean? = false,

    var isDelete: Boolean? = false,

    var uploadFilePath: String? = null,

    var uploadFileName: String? = null,

    var writer: String? = null,

    @Comment("등록일(업로드일시")
    @CreatedDate
    @Column(name = "UPLOADED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val uploadedAt: LocalDateTime? = LocalDateTime.now(),

    )