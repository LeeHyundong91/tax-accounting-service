package net.dv.tax.service.sales

import net.dv.tax.domain.sales.SalesElecInvoiceEntity
import net.dv.tax.dto.sales.SalesElecInvoiceListDto
import net.dv.tax.repository.sales.SalesElecInvoiceRepository
import org.springframework.stereotype.Service

@Service
class SalesElecInvoiceService(private val salesElecInvoiceRepository: SalesElecInvoiceRepository) {

    fun getListData(hospitalId: String, year: String): List<SalesElecInvoiceListDto> {
        return salesElecInvoiceRepository.groupingList(hospitalId, year)
    }

    fun getDetailData(hospitalId: String, yearMonth: String): List<SalesElecInvoiceEntity> {
        return salesElecInvoiceRepository.findAllByHospitalIdAndDataPeriodStartingWith(hospitalId, yearMonth)
    }

}