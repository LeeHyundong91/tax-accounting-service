package net.dv.tax.dto.purchase

import com.github.javaxcel.core.annotation.ExcelColumn
import java.time.LocalDate

data class PurchaseCreditCardExcelDto(

    @ExcelColumn(name = "일자")
    var billingDate: LocalDate? = null,

    @ExcelColumn(name = "코드")
    var accountCode: String? = null,

    @ExcelColumn(name = "거래처")
    var franchiseeName: String? = null,

    @ExcelColumn(name = "구분")
    var corporationType: String? = null,

    @ExcelColumn(name = "품명")
    var itemName: String? = null,

    @ExcelColumn(name = "공급가액")
    var supplyPrice: String? = null,

    @ExcelColumn(name = "세액")
    var taxAmount: String? = null,

    @ExcelColumn(name = "비과세")
    var nonTaxAmount: String? = null,

    @ExcelColumn(name = "합계")
    var totalAmount: String? = null,

    @ExcelColumn(name = "국세청(공제여부)")
    var isDeduction: String? = null,

    @ExcelColumn(name = "추천유형(불공제")
    var isRecommendDeduction: String? = null,

    @ExcelColumn(name = "전표유형")
    var statementType1: String? = null,

    @ExcelColumn(name = "전표유형")
    var statementType2: String? = null,

    @ExcelColumn(name = "차변계정")
    var debtorAccount: String? = null,

    @ExcelColumn(name = "대변계정")
    var creditAccount: String? = null,

    @ExcelColumn(name = "분개전송")
    var separateSend: String? = null,

    @ExcelColumn(name = "전표상태")
    var statementStatus: String? = null,

    var emptyCell1: String? = null,
    var emptyCell2: String? = null,
    var emptyCell3: String? = null,
    var emptyCell4: String? = null,
    var emptyCell5: String? = null,
    var emptyCell6: String? = null,
    var emptyCell7: String? = null,
    var emptyCell8: String? = null,


    )
