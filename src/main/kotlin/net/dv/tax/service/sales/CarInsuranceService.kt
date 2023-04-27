package net.dv.tax.service.sales

import net.dv.tax.domain.sales.CarInsuranceEntity
import net.dv.tax.dto.sales.CarInsuranceListDto
import net.dv.tax.repository.sales.CarInsuranceRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CarInsuranceService(private val carInsuranceRepository: CarInsuranceRepository) {

    fun getList(hospitalId: String, yearMonth: String): CarInsuranceListDto {
        return CarInsuranceListDto(
            carInsuranceRepository.dataList(hospitalId, yearMonth),
            carInsuranceRepository.dataListTotal(hospitalId, yearMonth)
        )
    }

    fun getDetailList(hospitalId: String, yearMonth: String, page: Pageable): Page<CarInsuranceEntity> {
        return carInsuranceRepository.findAllByHospitalIdAndTreatmentYearMonth(hospitalId, yearMonth, page)
    }
}