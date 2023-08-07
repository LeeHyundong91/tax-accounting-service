package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "PURCHASE_CASH_RECEIPT")
@Comment("현금영수증매입관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
class PurchaseCashReceiptEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("업로드 파일 ID")
    @Column(name = "DATA_FILE_ID")
    var dataFileId: Long? = null,

    @Comment("일자")
    @Column(name = "BILLING_DATE")
    var billingDate: String? = null,

    @Comment("회계코드")
    @Column(name = "ACCOUNT_CODE")
    var accountCode: String? = null,

    @Comment("거래처")
    @Column(name = "FRANCHISEE_NAME")
    var franchiseeName: String? = null,

    @Comment("구분")
    @Column(name = "CORPORATION_TYPE")
    var corporationType: String? = null,

    @Column(name = "ITEM_NAME")
    @Comment("품명")
    var itemName: String? = null,

    @Column(name = "SUPPLY_PRICE")
    @Comment("공급가액")
    var supplyPrice: Long? = 0,

    @Column(name = "TAX_AMOUNT")
    @Comment("세액")
    var taxAmount: Long? = 0,

    @Column(name = "SERVICE_CHARGE")
    @Comment("봉사료")
    var serviceCharge: Long? = 0,

    @Column(name = "TOTAL_AMOUNT")
    @Comment("합계")
    var totalAmount: Long? = 0,

    @Column(name = "IS_DEDUCTION")
    @Comment("국세청(공제여부)")
    var isDeduction: Boolean? = null,

    @Column(name = "IS_RECOMMEND_DEDUCTION")
    @Comment("추천유형(불공제")
    var isRecommendDeduction: Boolean? = null,

    @Comment("전표유형 1")
    @Column(name = "STATEMENT_TYPE_1")
    var statementType1: String? = null,

    @Comment("전표유형 2")
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

    @Comment("부서")
    @Column(name = "DEPARTMENT")
    var department: String? = null,

    @Comment("전표상태")
    @Column(name = "STATEMENT_STATUS")
    var statementStatus: String? = null,

    @Comment("작성자")
    @Column(name = "WRITER")
    var writer: String? = null,
)