package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.SalesAgentDto

interface SalesAgentSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<SalesAgentDto>

}