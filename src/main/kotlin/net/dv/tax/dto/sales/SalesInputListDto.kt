package net.dv.tax.dto.sales

import net.dv.tax.domain.sales.HospitalChartEntity
import net.dv.tax.domain.sales.SalesOtherBenefitsEntity
import net.dv.tax.domain.sales.SalesVaccineEntity

data class HospitalChartListDto(
    var hospitalChartList: List<HospitalChartEntity>,
    var listTotal: HospitalChartEntity,
    )

data class SalesOtherBenefitsListDto(
    var otherBenefitsList: List<SalesOtherBenefitsEntity>,
    var listTotal: SalesOtherBenefitsEntity,
)
data class SalesVaccineListDto(
    val vaccineList: List<SalesVaccineEntity>,
    var listTotal: SalesVaccineEntity
)