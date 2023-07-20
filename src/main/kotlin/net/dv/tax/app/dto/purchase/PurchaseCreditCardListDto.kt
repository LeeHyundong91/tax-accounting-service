package net.dv.tax.app.dto.purchase
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

data class PurchaseCreditCardListDto (
    
    //list 데이터
    var listPurchaseCreditCard: List<PurchaseCreditCardDto>,

    //합계 정보
    var purchaseCreditCardTotal: PurchaseCreditCardTotal,

    //총게시물 수
    var totalCount: Long? = null
)

//합계 항목
data class PurchaseCreditCardTotal(

    //공제
    val totalSupplyPrice: Long? = 0,
    val totalTaxAmount: Long? = 0,
    val totalAmount: Long? = 0,

    //불공제
    val totalNonSupplyPrice: Long? = 0,
    val totalNonTaxAmount: Long? = 0,
    val totalNonAmount: Long? = 0,
)

data class PurchaseCreditCardTotalSearch(
    val totalSupplyPrice: Long? = 0,
    val totalTaxAmount: Long? = 0,
    val totalAmount: Long? = 0,
)

data class PurchaseCreditCardDto (

    val id: Int? = null,

    @Comment("병원아이디")
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

    @Comment("국세청(공제여부)이름")
    var deductionName: String? = null,

    @Comment("추천유형(불공제)")
    var isRecommendDeduction: Boolean? = false,

    @Comment("추천유형(불공제)명")
    var recommendDeductionName: String? = null,

    @Comment("전표유형1")
    var statementType1: String? = null,

    @Comment("전표유형2")
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
    val createdAt: LocalDateTime? = null,
)