package net.dv.tax.service.purchase

import net.dv.tax.domain.purchase.PurchaseHandwrittenEntity
import net.dv.tax.repository.purchase.PurchaseHandwrittenRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PurchaseHandwrittenService(private val purchaseHandwrittenRepository: PurchaseHandwrittenRepository) {

    fun dataSave(dataList: List<PurchaseHandwrittenEntity>): ResponseEntity<HttpStatus> {
        purchaseHandwrittenRepository.saveAll(dataList)

        return ResponseEntity(HttpStatus.OK)
    }

    fun dataList(hospitalId: String, issueDate: String, page: Pageable): List<PurchaseHandwrittenEntity> {

        return purchaseHandwrittenRepository.findAllByHospitalIdAndIssueDateStartingWith(hospitalId, issueDate, page)!!
    }


}