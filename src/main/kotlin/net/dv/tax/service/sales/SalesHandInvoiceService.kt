package net.dv.tax.service.sales

import net.dv.tax.domain.sales.SalesHandInvoiceEntity
import net.dv.tax.repository.sales.SalesHandInvoiceRepository
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

    fun getListData(hospitalId: String, year: String): List<SalesHandInvoiceEntity?>? {

        return salesHandInvoiceRepository.findAllByHospitalIdAndIssueDtStartingWithAndIsDeleteIsFalse(
            hospitalId,
            year
        )
    }


}