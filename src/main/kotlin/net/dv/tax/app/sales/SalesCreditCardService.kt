package net.dv.tax.app.sales

import net.dv.tax.app.dto.sales.SalesCreditCardListDto
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