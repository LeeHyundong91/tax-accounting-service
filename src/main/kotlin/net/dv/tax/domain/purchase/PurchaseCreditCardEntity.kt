package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
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
    var hospitalId: Int? = null,

    @Comment("일자")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "BILLING_DATE")
    var billingDate: LocalDate? = null,

    @Comment("코드")
    @Column(name = "ACCOUNT_CODE")
    var accountCode: String? = null,

    @Comment("거래처")
    @Column(name = "FRANCHISEE_NAME")
    var franchiseeName: String? = null,

    @Comment("구분")
    @Column(name = "CORPORATION_TYPE")
    var corporationType: String? = null,

    @Comment("품명")
    @Column(name = "ITEM_NAME")
    var itemName: String? = null,

    @Comment("공급가액")
    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Long? = null,

    @Comment("세액")
    @Column(name = "TAX_AMOUNT")
    var taxAmount: Long? = 0,

    @Comment("비과세")
    @Column(name = "NON_TAX_AMOUNT")
    var nonTaxAmount: Long? = 0,

    @Comment("합계")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Long? = 0,

    @Comment("국세청(공제여부)")
    @Column(name = "IS_DEDUCTION")
    var isDeduction: Boolean? = false,

    @Comment("추천유형(불공제")
    @Column(name = "IS_RECOMMEND_DEDUCTION")
    var isRecommendDeduction: Boolean? = false,

    @Comment("전표유형")
    @Column(name = "STATEMENT_TYPE_1")
    var statementType1: String? = null,

    @Comment("전표유형")
    @Column(name = "STATEMENT_TYPE_2")
    var statementType2: String? = null,

    @Comment("차변계정")
    @Column(name = "DEBTOR_ACCOUNT")
    var debtorAccount: String? = null,

    @Comment("대변계정")
    @Column(name = "CREDIT_ACCOUNT")
    var creditAccount: String? = null,

    @Comment("분개전송")
    @Column(name = "SEPARATE_SEND")
    var separateSend: String? = null,

    @Comment("전표상태")
    @Column(name = "STATEMENT_STATUS")
    var statementStatus: String? = null,

    @Comment("작성자")
    @Column(name = "WRITER")
    var writer: String? = null,

    @Comment("삭제")
    @Column(name = "IS_DELETE")
    var isDelete: Boolean? = false,

    @Comment("등록일(업로드일시")
    @CreatedDate
    @Column(name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
