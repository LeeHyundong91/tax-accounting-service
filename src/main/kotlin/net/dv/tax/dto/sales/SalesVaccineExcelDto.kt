package net.dv.tax.dto.sales

import com.github.javaxcel.core.annotation.ExcelColumn
import java.time.LocalDateTime


data class SalesVaccineExcelDto(

    @ExcelColumn(name = "기간")
    var dataPeriod: String? = null,

    @ExcelColumn(name="지급완료 건수")
    var payCount: Long? = 0,

    @ExcelColumn(name="지급금액")
    var payAmount: Long? = 0,

    @ExcelColumn(name="작성자")
    var writer: String? = null,

    @ExcelColumn(name="작성일")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    )
