package net.dv.tax.service.purchase

import net.dv.tax.repository.purchase.PurchasePassbookRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PurchasePassbookService(private val passbookRepository: PurchasePassbookRepository) {

    fun getPassbookList(hospitalId: String, transactionDate: String, page: Pageable) {

        passbookRepository.findAllByHospitalIdAndTransactionDateStartingWith(hospitalId, transactionDate, page)

    }

}