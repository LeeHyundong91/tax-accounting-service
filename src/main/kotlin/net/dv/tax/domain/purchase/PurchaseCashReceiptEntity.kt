package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "purchase_cash_receipt")
@Comment("현금영수증매입관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
class PurchaseCashReceiptEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    var hospitalId: String? = null,

    @Comment("일자")
    var billingDate: String? = null,

    @Comment("회계코드")
    var accountCode: String? = null,

    @Comment("거래처")
    var franchiseeName: String? = null,

    @Column(name = "CORPORATION_TYPE")
    var corporationType: String? = null,

    @Column(name = "ITEM_NAME")
    var itemName: String? = null,

    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Int? = null,

    @Column(name = "TAX_AMOUNT")
    var taxAmount: Int? = null,

    @Column(name = "SERVICE_CHARGE")
    var serviceCharge: Int? = null,

    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Int? = null,

    @Column(name = "IS_DEDUCTION")
    var isDeduction: Boolean? = null,

    @Column(name = "IS_RECOMMEND_DEDUCTION")
    var isRecommendDeduction: Boolean? = null,

    @Column(name = "STATEMENT_TYPE_1")
    var statementType1: String? = null,

    @Column(name = "STATEMENT_TYPE_2")
    var statementType2: String? = null,

    @Column(name = "DEBTOR_ACCOUNT")
    var debtorAccount: String? = null,

    @Column(name = "CREDIT_ACCOUNT")
    var creditAccount: String? = null,

    @Column(name = "SEPARATE_SEND")
    var separateSend: String? = null,

    @Column(name = "DEPARTMENT")
    var department: String? = null,

    @Column(name = "STATEMENT_STATUS")
    var statementStatus: String? = null,

    @Column(name = "WRITER")
    var writer: String? = null,

)