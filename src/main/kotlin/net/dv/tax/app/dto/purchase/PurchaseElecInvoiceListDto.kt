package net.dv.tax.app.dto.purchase

import net.dv.tax.app.purchase.JournalEntryStatus
import org.hibernate.annotations.Comment
import java.time.LocalDateTime


//합계 항목
data class PurchaseElecInvoiceTotal(
    val totalAmount: Long? = 0,
    val totalSupplyPrice: Long? = 0,
    val totalTaxAmount: Long? = 0,
)

data class PurchaseElecInvoiceDto (

    val id: Long? = null,

    @Comment("병원 아이디")
    var hospitalId: String? = null,

    @Comment("발급 일자")
    var issueDate: String? = null,

    @Comment("전송 일자")
    var sendDate: String? = null,

    @Comment("코드")
    var accountCode: String? = null,

    @Comment("거래처")
    var franchiseeName: String? = null,

    @Comment("품명")
    var itemName: String? = null,

    @Comment("공급가액")
    var supplyPrice: Long? = null,

    @Comment("세액")
    var taxAmount: Long? = null,

    @Comment("합계")
    var totalAmount: Long? = null,

    @Comment("유형(공제여부)")
    var isDeduction: String? = null,

    @Comment("차변계정")
    var debtorAccount: String? = null,

    @Comment("대변계정")
    var creditAccount: String? = null,

    @Comment("분개전송")
    var separateSend: String? = null,

    @Comment("전표상태")
    var statementStatus: String? = null,

    @Comment("작업상태")
    var taskType: String? = null,

    @Comment("승인번호")
    var approvalNo: String? = null,

    @Comment("종류(계산서)")
    var invoiceType: String? = null,

    @Comment("구분(청구)")
    var billingType: String? = null,

    @Comment("발급유형")
    var issueType: String? = null,

    @Comment("작성자")
    var writer: String? = null,
): JournalEntryStatus {
    override var status: String? = null
    override var requestedAt: LocalDateTime? = null
    override var committedAt: LocalDateTime? = null
}