package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@Table(name = "purchase_elec_invoice")
@Comment("전자세금계산서매입관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class PurchaseElecInvoiceEntity(

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Comment("병원 아이디")
    @Column(name = "HOSPITAL_ID")
    var hospitalId: String? = null,

    @Comment("발급 일자")
    @Column(name = "ISSUE_DATE")
    var issueDate: LocalDate? = null,

    @Comment("전송 일자")
    @Column(name = "SEND_DATE")
    var sendDate: LocalDate? = null,

    @Comment("코드")
    @Column(name = "ACCOUNT_CODE")
    var accountCode: String? = null,

    @Comment("거래처")
    @Column(name = "FRANCHISEE_NAME")
    var franchiseeName: String? = null,

    @Comment("품명")
    @Column(name = "ITEM_NAME")
    var itemName: String? = null,

    @Comment("공급가액")
    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Long? = null,

    @Comment("세액")
    @Column(name = "TAX_AMOUNT")
    var taxAmount: Long? = null,

    @Comment("합계")
    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Long? = null,

    @Comment("유형(공제여부)")
    @Column(name = "IS_DEDUCTION")
    var isDeduction: String? = null,

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

    @Comment("작업상태")
    @Column(name = "TASK_TYPE")
    var taskType: String? = null,

    @Comment("승인번호")
    @Column(name = "APPROVAL_NO")
    var approvalNo: String? = null,

    @Comment("종류(계산서)")
    @Column(name = "INVOICE_TYPE")
    var invoiceType: String? = null,

    @Comment("구분(청구)")
    @Column(name = "BILLING_TYPE")
    var billingType: String? = null,

    @Comment("발급유형")
    @Column(name = "ISSUE_TYPE")
    var issueType: String? = null,

    @Comment("작성자")
    @Column(name = "WRITER")
    var writer: String? = null,

    @Comment("전자세금계산서 : true / 전자계산서 : false")
    @Column(name = "IS_TAX")
    val tax: Boolean? = false
)