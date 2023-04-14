package net.dv.tax.service.sales

import net.dv.tax.domain.sales.SalesAgentEntity
import net.dv.tax.repository.sales.SalesAgentRepository
import org.springframework.stereotype.Service

@Service
class SalesAgentService(private val salesAgentRepository: SalesAgentRepository) {

    fun getDataList(hospitalId: String, dataPeriod: String): List<SalesAgentEntity> {
        return salesAgentRepository.findAllByHospitalIdAndDataPeriod(hospitalId, dataPeriod)
    }

}