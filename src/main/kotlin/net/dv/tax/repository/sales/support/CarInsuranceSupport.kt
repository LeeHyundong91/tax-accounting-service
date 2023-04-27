package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.CarInsuranceDto

interface CarInsuranceSupport {

    fun dataList(hospitalId: String, yearMonth: String) : List<CarInsuranceDto>

    fun dataListTotal(hospitalId: String, yearMonth: String) : CarInsuranceDto

}