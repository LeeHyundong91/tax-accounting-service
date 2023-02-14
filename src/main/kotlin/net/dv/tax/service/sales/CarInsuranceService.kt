package net.dv.tax.service.sales

import net.dv.tax.dto.sales.CarInsuranceListDto
import net.dv.tax.repository.sales.CarInsuranceRepository
import org.springframework.stereotype.Service

@Service
class CarInsuranceService(private val carInsuranceRepository: CarInsuranceRepository) {

    fun getCarInsuranceList(hospitalId: String, year: String): List<CarInsuranceListDto> {
        return carInsuranceRepository.groupingList(hospitalId, year)
    }

}