package net.dv.tax.service.sales

import net.dv.tax.dto.sales.SalesAgentDto
import net.dv.tax.dto.sales.SalesAgentListDto
import net.dv.tax.repository.sales.SalesAgentRepository
import org.springframework.stereotype.Service

@Service
class SalesAgentService(private val salesAgentRepository: SalesAgentRepository) {

    fun getList(hospitalId: String, year: String): SalesAgentListDto {
        val dataList = salesAgentRepository.dataList(hospitalId, year)
        return SalesAgentListDto(
            dataList,
            totalList(dataList)
        )
    }

    fun totalList(dataList: List<SalesAgentDto>): SalesAgentDto {
        return SalesAgentDto(
            salesCount = dataList.sumOf { it.salesCount ?: 0 },
            totalSales = dataList.sumOf { it.totalSales ?: 0 },
        )
    }


}