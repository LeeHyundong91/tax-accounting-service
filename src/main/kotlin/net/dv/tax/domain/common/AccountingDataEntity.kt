package net.dv.tax.domain.common

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "tax_and_employee_data")
@Comment("세무 노무 파일 관리")
@DynamicUpdate
data class AccountingDataEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var hospitalId: String? = null,

    @Comment("한글 메뉴명")
    var dataCategoryKor: String? = null,

    @Comment("영문 메뉴명")
    var dataCategory: String? = null,

    @Comment("기간 yyyy, yyyy-mm")
    var dataPeriod: String? = null,

    @Comment("세무, 노무")
    var dataType: String? = null,

    @Comment("병원명")
    var companyName: String? = null,

    var isApply: Boolean? = false,

    var isDelete: Boolean? = false,

    @Comment("S3 Path")
    var uploadFilePath: String? = null,

    @Comment("파일 이름")
    var uploadFileName: String? = null,

    var writer: String? = null,

    @Comment("등록일(업로드일시")
    @CreatedDate
    @Column(name = "uploaded_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val uploadedAt: LocalDateTime? = LocalDateTime.now(),

    )