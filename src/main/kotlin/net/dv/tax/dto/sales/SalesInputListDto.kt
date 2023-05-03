package net.dv.tax.dto.sales

import net.dv.tax.domain.sales.HospitalChartEntity
import net.dv.tax.domain.sales.SalesHandInvoiceEntity
import net.dv.tax.domain.sales.SalesOtherBenefitsEntity
import net.dv.tax.domain.sales.SalesVaccineEntity
import org.springframework.data.domain.Page

/**
 * 병원자료 관리 매출
 */
data class HospitalChartListDto(
    var hospitalChartList: List<HospitalChartEntity>,
    var listTotal: HospitalChartEntity? = null,
)

/**
 * 기타급여매출
 */
data class SalesOtherBenefitsListDto(
    var otherBenefitsList: List<SalesOtherBenefitsEntity>,
    var listTotal: SalesOtherBenefitsEntity? = null,
)

/**
 * 예방접종 매출
 */
data class SalesVaccineListDto(
    val vaccineList: List<SalesVaccineEntity>,
    var listTotal: SalesVaccineEntity? = null,
)

/**
 * 수기세금계산서 매출
 */
data class SalesHandInvoiceListDto(
    val invoiceList: Page<SalesHandInvoiceEntity>,
    var listTotal: SalesHandInvoiceEntity? = null,
)