package net.dv.tax.app.dto.purchase
import org.hibernate.annotations.Comment

data class PurchaseCashReceiptListDto (
    
    //list 데이터
    var listPurchaseCashReceipt: List<PurchaseCashReceiptDto>,

    //합계 정보
    var purchaseCashReceiptTotal: PurchaseCashReceiptTotal,

    //총게시물 수
    var totalCount: Long? = null
)

//합계 항목
data class PurchaseCashReceiptTotal(

    //공제
    val totalSupplyPrice: Long? = 0,
    val totalTaxAmount: Long? = 0,
    val totalServiceCharge: Long? = 0,
    val totalAmount: Long? = 0,

    //불공제
    val totalNonSupplyPrice: Long? = 0,
    val totalNonTaxAmount: Long? = 0,
    val totalNonServiceCharge: Long? = 0,
    val totalNonAmount: Long? = 0,
)

data class PurchaseCashReceiptTotalSearch(
    val totalSupplyPrice: Long? = 0,
    val totalTaxAmount: Long? = 0,
    val totalServiceCharge: Long? = 0,
    val totalAmount: Long? = 0,
)

data class PurchaseCashReceiptDto (


    val id: Long? = null,

    var hospitalId: String? = null,

    @Comment("업로드 파일 ID")
    var dataFileId: Long? = null,

    @Comment("일자")
    var billingDate: String? = null,

    @Comment("회계코드")
    var accountCode: String? = null,

    @Comment("거래처")
    var franchiseeName: String? = null,

    @Comment("구분")
    var corporationType: String? = null,

    @Comment("품명")
    var itemName: String? = null,

    @Comment("공급가액")
    var supplyPrice: Long? = 0,

    @Comment("세액")
    var taxAmount: Long? = 0,

    @Comment("봉사료")
    var serviceCharge: Long? = 0,

    @Comment("합계")
    var totalAmount: Long? = 0,

    @Comment("국세청(공제여부)")
    var isDeduction: Boolean? = null,

    @Comment("국세청(공제여부)명")
    var deductionName: String? = null,

    @Comment("추천유형(불공제)")
    var isRecommendDeduction: Boolean? = null,

    @Comment("추천유형(불공제)명")
    var recommendDeductionName: String? = null,

    @Comment("전표유형 1")
    var statementType1: String? = null,

    @Comment("전표유형 2")
    var statementType2: String? = null,

    @Comment("차변계정")
    var debtorAccount: String? = null,

    @Comment("대변계정")
    var creditAccount: String? = null,

    @Comment("분개전송")
    var separateSend: String? = null,

    @Comment("부서")
    var department: String? = null,

    @Comment("전표상태")
    var statementStatus: String? = null,

    @Comment("작성자")
    var writer: String? = null,

)