package net.dv.tax.service.sales

import net.dv.tax.dto.sales.SalesCreditCardListDto
import net.dv.tax.repository.sales.SalesCreditCardRepository
import org.springframework.stereotype.Service

@Service
class SalesCreditCardService(private val salesCreditCardRepository: SalesCreditCardRepository) {

    fun getList(hospitalId: String, year: String): SalesCreditCardListDto {
        return SalesCreditCardListDto(
            salesCreditCardRepository.dataList(hospitalId, year),
            salesCreditCardRepository.dataListTotal(hospitalId, year)
        )
    }


}