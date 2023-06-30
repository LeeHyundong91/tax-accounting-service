package net.dv.tax.service.app

import net.dv.tax.domain.consulting.SalesTypeEntity
import net.dv.tax.dto.app.ConsultingReportDto
import net.dv.tax.dto.app.ReportMainContents
import net.dv.tax.repository.consulting.PurchaseReportRepository
import net.dv.tax.repository.consulting.SalesTypeRepository
import org.springframework.stereotype.Service

@Service
class ConsultingReportService(
    private val purchaseReportRepository: PurchaseReportRepository,
    private val salesTypeRepository: SalesTypeRepository,
) {


    fun mainReport(hospitalId: String, yearMonth: String): ConsultingReportDto {

        val data = ConsultingReportDto()
        data.also {
            val reportMainData = ReportMainContents(
                incomeTax = 62458319,
                reductionTax = 47051180,
                taxAmount = 5015012,
                expectSales = 3868645057,
                expectCost = 2719613011,
                expectIncome = 1149032046
            )
            it.reportMain = reportMainData
        }



        data.salesTypeData =
            salesTypeRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, yearMonth)
                ?: SalesTypeEntity()

        data.purchaseTypeData =
            purchaseReportRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, yearMonth)
        return data

    }


}