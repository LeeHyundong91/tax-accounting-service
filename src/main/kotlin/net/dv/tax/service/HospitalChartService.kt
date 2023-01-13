package net.dv.tax.service

import mu.KotlinLogging
import net.dv.tax.domain.sales.HospitalChartEntity
import net.dv.tax.repository.HospitalChartRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class HospitalChartService(private val hospitalChartRepository: HospitalChartRepository) {

    private val log = KotlinLogging.logger {}


    fun hospitalChartYearList(hospitalId: Int, year: Int): List<HospitalChartEntity>? {
        log.info { "hospitalId : $hospitalId , year: $year" }
        return hospitalChartRepository.findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId, year)
    }

    fun hospitalChartSave(hospitalChartList: List<HospitalChartEntity>): ResponseEntity<Any>{
        log.info { hospitalChartList }
        hospitalChartRepository.saveAll(hospitalChartList)
        return ResponseEntity.ok(HttpStatus.OK.value())
    }


}