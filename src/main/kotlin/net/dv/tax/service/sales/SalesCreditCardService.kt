package net.dv.tax.service.sales

import net.dv.tax.dto.sales.SalesCreditCardListDto
import net.dv.tax.repository.sales.SalesCreditCardRepository
import org.springframework.stereotype.Service

@Service
class SalesCreditCardService(private val salesCreditCardRepository: SalesCreditCardRepository) {

    fun getListData(hospitalId: String, year: String): List<SalesCreditCardListDto> {
        return salesCreditCardRepository.groupingList(hospitalId, year)
    }

}