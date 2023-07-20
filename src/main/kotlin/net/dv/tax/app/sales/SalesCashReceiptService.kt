package net.dv.tax.app.sales

import net.dv.tax.domain.sales.SalesCashReceiptEntity
import net.dv.tax.app.dto.sales.SalesCashReceiptListDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SalesCashReceiptService(private val salesCashReceiptRepository: SalesCashReceiptRepository) {

    fun getList(hospitalId: String, year: String): SalesCashReceiptListDto {
        return SalesCashReceiptListDto(
            salesCashReceiptRepository.dataList(hospitalId, year),
            salesCashReceiptRepository.dataListTotal(hospitalId, year)
        )
    }

    fun getDetailList(hospitalId: String, year: String, page: Pageable): Page<SalesCashReceiptEntity> {
        return salesCashReceiptRepository.findAllByHospitalIdAndSalesDateStartingWith(hospitalId, year, page)
    }

}