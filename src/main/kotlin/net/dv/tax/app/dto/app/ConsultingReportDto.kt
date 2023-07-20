package net.dv.tax.app.dto.app

import net.dv.tax.domain.consulting.PurchaseReportEntity
import net.dv.tax.domain.consulting.SalesTypeEntity
import org.hibernate.annotations.Comment

data class ConsultingReportDto (

    var reportMain: ReportMainContents? = null,

    var salesTypeData: SalesTypeEntity? = null,

    var purchaseTypeData: PurchaseReportEntity? = null,

    )

data class ReportMainContents(

    @Comment("연간소득세")
    var incomeTax: Long? =0,

    @Comment("세액감면/공제")
    var reductionTax: Long? = 0,

    @Comment("기납부 세액")
    var taxAmount: Long? = 0,

    @Comment("연예상 매출")
    var expectSales: Long? = 0,

    @Comment("연예상 비용")
    var expectCost: Long? = 0,

    @Comment("연예상 소득")
    var expectIncome: Long? = 0

)