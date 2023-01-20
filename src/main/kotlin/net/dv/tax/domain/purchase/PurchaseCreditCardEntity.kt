package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "PURCHASE_CREDIT_CARD")
@Comment("신용카드매입관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class PurchaseCreditCardEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Comment("병원아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: Long? = null,

    @Comment("일자")
    @Column(name = "BILLING_DATE")
    var billingDate: String? = null,

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
    var supplyPrice: String? = null,

    @Comment("세액")
    @Column(name = "TAX_AMOUNT")
    var taxAmount: String? = null,

    @Comment("비과세")
    @Column(name = "NON_TAX_AMOUNT")
    var nonTaxAmount: String? = null,

    @Comment("합계")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: String? = null,

    @Comment("국세청(공제여부)")
    @Column(name = "IS_DEDUCTION")
    var isDeduction: String? = null,

    @Comment("추천유형(불공제")
    @Column(name = "IS_NON_DEDUCTION")
    var isNonDeduction: String? = null,

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
)
