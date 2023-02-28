package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "purchase_credit_card")
@Comment("신용카드매입관리")
@Suppress("JpaAttributeTypeInspection")
@DynamicUpdate
data class PurchaseCreditCardEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Comment("병원아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("업로드 파일 ID")
    var dataFileId: Long? = null,

    @Comment("일자")
    var billingDate: String? = null,

    @Comment("코드")
    var accountCode: String? = null,

    @Comment("거래처")
    var franchiseeName: String? = null,

    @Comment("구분")
    var corporationType: String? = null,

    @Comment("품명")
    var itemName: String? = null,

    @Comment("공급가액")
    var supplyPrice: Long? = null,

    @Comment("세액")
    var taxAmount: Long? = 0,

    @Comment("비과세")
    var nonTaxAmount: Long? = 0,

    @Comment("합계")
    var totalAmount: Long? = 0,

    @Comment("국세청(공제여부)")
    var isDeduction: Boolean? = false,

    @Comment("추천유형(불공제")
    var isRecommendDeduction: Boolean? = false,

    @Comment("전표유형")
    var statementType1: String? = null,

    @Comment("전표유형")
    var statementType2: String? = null,

    @Comment("차변계정")
    var debtorAccount: String? = null,

    @Comment("대변계정")
    var creditAccount: String? = null,

    @Comment("분개전송")
    var separateSend: String? = null,

    @Comment("전표상태")
    var statementStatus: String? = null,

    @Comment("작성자")
    var writer: String? = null,

    @Comment("삭제")
    var isDelete: Boolean? = false,

    @Comment("등록일(업로드일시")
    @CreatedDate
    @Column(name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
