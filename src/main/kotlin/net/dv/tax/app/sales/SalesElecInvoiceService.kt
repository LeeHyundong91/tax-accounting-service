package net.dv.tax.app.sales

import net.dv.tax.domain.sales.SalesElecInvoiceEntity
import net.dv.tax.app.dto.sales.SalesElecInvoiceListDto
import net.dv.tax.app.dto.sales.SalesElecTaxInvoiceListDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SalesElecInvoiceService(private val salesElecInvoiceRepository: SalesElecInvoiceRepository) {

    fun getList(hospitalId: String, year: String): SalesElecInvoiceListDto {
        return SalesElecInvoiceListDto(
            salesElecInvoiceRepository.dataList(hospitalId, year),
            salesElecInvoiceRepository.dataListTotal(hospitalId, year)
        )
    }

    fun getDetailList(hospitalId: String, yearMonth: String, page: Pageable): Page<SalesElecInvoiceEntity> {
        return salesElecInvoiceRepository.findAllByHospitalIdAndTaxIsFalseAndWritingDateStartingWithOrderByWritingDateDesc(
            hospitalId,
            yearMonth,
            page
        )
    }

    fun getTaxList(hospitalId: String, year: String): SalesElecTaxInvoiceListDto {
        return SalesElecTaxInvoiceListDto(
            salesElecInvoiceRepository.taxDataList(hospitalId, year),
            salesElecInvoiceRepository.taxDataListTotal(hospitalId, year)
        )
    }

    fun getTaxDetailList(hospitalId: String, yearMonth: String, page: Pageable): Page<SalesElecInvoiceEntity> {
        return salesElecInvoiceRepository.findAllByHospitalIdAndTaxIsTrueAndWritingDateStartingWithOrderByWritingDateDesc(
            hospitalId,
            yearMonth,
            page
        )
    }

}