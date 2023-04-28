package net.dv.tax.dto.sales

import net.dv.tax.domain.sales.HospitalChartEntity

data class HospitalChartListDto(

    var hospitalChartList: List<HospitalChartEntity>,

    var listTotal: HospitalChartEntity,


    )