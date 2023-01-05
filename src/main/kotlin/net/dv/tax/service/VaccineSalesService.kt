package net.dv.tax.service

import mu.KotlinLogging
import net.dv.tax.domain.VaccineSalesEntity
import net.dv.tax.repository.VaccineSalesRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VaccineSalesService(private val vaccineSalesRepository: VaccineSalesRepository) {


    private val log = KotlinLogging.logger {}


    fun vaccineYearList(hospitalId: Int, year: String): List<VaccineSalesEntity>? {
        return vaccineSalesRepository.findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId, year)
    }

    fun vaccineSave(hospitalId: Int, vaccineSalesEntity: List<VaccineSalesEntity>): ResponseEntity<Any> {

        vaccineSalesRepository.saveAll(vaccineSalesEntity)
        return ResponseEntity.ok(HttpStatus.OK)
    }

}