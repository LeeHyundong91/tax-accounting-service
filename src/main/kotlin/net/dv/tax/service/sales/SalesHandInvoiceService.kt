package net.dv.tax.service.sales

import net.dv.tax.domain.sales.SalesHandInvoiceEntity
import net.dv.tax.dto.sales.SalesHandInvoiceListDto
import net.dv.tax.repository.sales.SalesHandInvoiceRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SalesHandInvoiceService(private val salesHandInvoiceRepository: SalesHandInvoiceRepository) {

    fun saveData(dataList: List<SalesHandInvoiceEntity>, hospitalId: String): ResponseEntity<HttpStatus> {
        dataList.forEach {
            it.hospitalId = hospitalId
            if (it.isDelete == true) {
                it.deletedAt = LocalDateTime.now()
            }
        }
        salesHandInvoiceRepository.saveAll(dataList)

        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun getList(hospitalId: String, year: String, page: Pageable): SalesHandInvoiceListDto {

        val dataList =
            salesHandInvoiceRepository.findAllByHospitalIdAndIssueDateStartingWithAndIsDeleteIsFalseOrderByIssueDateDesc(
                hospitalId,
                year,
                page
            )

        return SalesHandInvoiceListDto(
            dataList,
            totalList(dataList.content)
        )
    }

    fun totalList(dataList: List<SalesHandInvoiceEntity>): SalesHandInvoiceEntity {
        return SalesHandInvoiceEntity(
            supplyPrice = dataList.sumOf { it.supplyPrice ?: 0 },
            taxAmount = dataList.sumOf { it.taxAmount ?: 0 },
            totalAmount = dataList.sumOf { it.totalAmount ?: 0 }
        )
    }

}