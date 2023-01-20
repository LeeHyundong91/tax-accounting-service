package net.dv.tax.domain.purchase

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@Table(name = "PURCHASE_ELEC_INVOICE")
@Comment("전자세금계산서매입관리")
@Suppress("JpaAttributeTypeInspection")
@EntityListeners(AuditingEntityListener::class)
@DynamicUpdate
data class PurchaseElecInvoiceEntity (

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "HOSPITAL_ID")
    var hospitalId: Int? = null,

    @Column(name = "ISSUE_DATE")
    var issueDate: LocalDate? = null,

    @Column(name = "SEND_DATE")
    var sendDate: LocalDate? = null,

    @Column(name = "ACCOUNT_CODE")
    var accountCode: String? = null,

    @Column(name = "FRANCHISEE_NAME")
    var franchiseeName: String? = null,

    @Column(name = "ITEM_NAME")
    var itemName: String? = null,

    @Column(name = "SUPPLY_PRICE")
    var supplyPrice: Int? = null,

    @Column(name = "TAX_AMOUNT")
    var taxAmount: Int? = null,

    @Column(name = "TOTAL_AMOUNT")
    var totalAmount: Int? = null,

    @Column(name = "IS_DEDUCTION")
    var isDeduction: Byte? = null,

    @Column(name = "DEBTOR_ACCOUNT")
    var debtorAccount: String? = null,

    @Column(name = "CREDIT_ACCOUNT")
    var creditAccount: String? = null,

    @Column(name = "SEPARATE_SEND")
    var separateSend: String? = null,

    @Column(name = "STATEMENT_STATUS")
    var statementStatus: String? = null,

    @Column(name = "TASK_TYPE")
    var taskType: String? = null,

    @Column(name = "APPROVAL_NO")
    var approvalNo: String? = null,

    @Column(name = "INVOICE_TYPE")
    var invoiceType: String? = null,

    @Column(name = "BILLING_TYPE")
    var billingType: String? = null,

    @Column(name = "ISSUE_TYPE")
    var issueType: String? = null,

    @Column(name = "WRITER")
    var writer: String? = null,
)