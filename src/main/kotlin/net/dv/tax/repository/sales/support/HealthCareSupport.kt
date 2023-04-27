package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.HealthCareDto

interface HealthCareSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<HealthCareDto>

    fun dataListTotal(hospitalId: String, yearMonth: String) : HealthCareDto

}