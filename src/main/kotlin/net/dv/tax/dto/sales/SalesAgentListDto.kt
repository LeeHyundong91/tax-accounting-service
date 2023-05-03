package net.dv.tax.dto.sales

data class SalesAgentListDto(

    val salesAgentList: List<SalesAgentDto>,
    val totalList: SalesAgentDto? = null,

    )

data class SalesAgentDto(

    var approvalYearMonth: String? = null,

    var salesCount: Long? = 0,

    var totalSales: Long? = 0,

    )