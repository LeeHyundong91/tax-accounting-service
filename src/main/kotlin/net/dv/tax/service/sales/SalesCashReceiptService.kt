package net.dv.tax.service.sales

import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.dto.sales.SalesCashReceiptListDto
import net.dv.tax.repository.sales.SalesCashReceiptRepository
import org.springframework.stereotype.Service

@Service
class SalesCashReceiptService(private val salesCashReceiptRepository: SalesCashReceiptRepository) {

    fun getListData(hospitalId: String, year: String): List<SalesCashReceiptListDto> {
        return salesCashReceiptRepository.groupingList(hospitalId, year)
    }

    fun getDetailData(hospitalId: String, yearMonth: String): List<SalesCashReceiptEntity> {
        return salesCashReceiptRepository.findAllByHospitalIdAndDataPeriodStartingWith(hospitalId, yearMonth)
    }

}