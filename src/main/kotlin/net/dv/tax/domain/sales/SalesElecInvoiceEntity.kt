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

@Entity
@Table(name = "sales_elec_invoice")
@Comment("전자계산서매출관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class SalesElecInvoiceEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Comment("병원 아이디")
    var hospitalId: String,

    var receiveDataId: Long? = 0,

    @Comment("작성일자 - wrtDt")
    val writingDate: String? = null,

    @Comment("발급일자 - isnDtm")
    val issuanceDate: String? = null,

    @Comment("전송일자 - tmsnDt")
    val transmissionDate: String? = null,

    @Comment("공급받는자 등록번호(종사업장번호) - dmnrTxprDscmNo")
    val recipientNumber: String? = null,

    @Comment("상호 - tnmNm")
    val businessName: String? = null,

    @Comment("대표자명 - rprsFnm")
    val representativeName: String? = null,

    @Comment("품목명 - alsatNm")
    val itemName: String? = null,

    @Comment("합계급액 - totaAmtStr")
    val totalAmount: Long? = null,

    @Comment("공급가액 - sumSplCftStr")
    val supplyAmount: Long? = null,

    @Comment("세액 - sumTxamtStr")
    val taxAmount: Long? = null,

    @Comment("승인번호 - etan")
    val approvalNumber: String? = null,

    @Comment("전자세금계산서 종류 - etxivKndCd")
    val taxInvoiceType: String? = null,

    @Comment("발급유형 - isnTypeCd")
    val issuanceType: String? = null,

    @Comment("비고 - etxivSq1RmrkCntn")
    val remark: String? = null,

    @Comment("영수/청구 구분 - recApeClCd")
    val receiptDivision: String? = null,

    @Comment("공급자 이메일 - mchrgEmlAdrSls")
    val supplierEmail: String? = null,

    @Comment("공급받는자 이메일1 - mchrgEmlAdrPrh")
    val recipientEmail1: String? = null,

    @Comment("공급받는자 이메일2 - schrgEmlAdrPrh")
    val recipientEmail2: String? = null,

    @Comment("전자세금계산서 : true / 전자계산서 : false")
    @Column(name = "is_tax")
    val tax: Boolean? = false,

    @NotNull
    @CreatedDate
    @JsonIgnore
    @Column(updatable = false, name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),


    )

