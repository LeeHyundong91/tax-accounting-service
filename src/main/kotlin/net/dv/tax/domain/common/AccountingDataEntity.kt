package net.dv.tax.domain.common

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "TAX_AND_EMPLOYEE_DATA")
@Comment("세무 노무 파일 관리")
@DynamicUpdate
data class AccountingDataEntity(

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("한글 메뉴명")
    @Column(name = "DATA_CATEGORY_KOR")
    var dataCategoryKor: String? = null,

    @Comment("영문 메뉴명")
    @Column(name = "DATA_CATEGORY")
    var dataCategory: String? = null,

    @Comment("기간 yyyy, yyyy-mm")
    @Column(name = "DATA_PREIOD")
    var dataPeriod: String? = null,

    @Comment("세무, 노무")
    @Column(name = "DATA_TYPE")
    var dataType: String? = null,

    @Comment("병원명")
    @Column(name = "COMPANY_NAME")
    var companyName: String? = null,

    @Column(name = "IS_APPLY")
    var isApply: Boolean? = false,

    @Column(name = "IS_DELETE")
    var isDelete: Boolean? = false,

    @Comment("S3 Path")
    @Column(name = "UPLOAD_FILE_PATH")
    var uploadFilePath: String? = null,

    @Comment("파일 이름")
    @Column(name = "UPLOAD_FILE_NAME")
    var uploadFileName: String? = null,

    @Column(name = "WRITER")
    var writer: String? = null,

    @Comment("등록일(업로드일시")
    @CreatedDate
    @Column(name = "UPLOADED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val uploadedAt: LocalDateTime? = LocalDateTime.now(),

  )