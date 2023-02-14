package net.dv.tax.repository.sales.support

import net.dv.tax.dto.sales.CarInsuranceListDto

interface CarInsuranceSupport {
    fun groupingList(hospitalId: String, dataPeriod: String): List<CarInsuranceListDto>
}