package net.dv.tax.service.sales

import mu.KotlinLogging
import net.dv.tax.domain.sales.HospitalChartEntity
import net.dv.tax.dto.sales.HospitalChartListDto
import net.dv.tax.repository.sales.HospitalChartRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class HospitalChartService(private val hospitalChartRepository: HospitalChartRepository) {

    private val log = KotlinLogging.logger {}


    fun getList(hospitalId: String, year: String): HospitalChartListDto {

        val chart =
            hospitalChartRepository.findAllByHospitalIdAndTreatmentYearMonthStartingWithOrderByTreatmentYearMonth(
                hospitalId,
                year
            )

        return HospitalChartListDto(
            chart,
            totalList(chart)
        )
    }

    fun totalList(dataList: List<HospitalChartEntity>): HospitalChartEntity {

        return HospitalChartEntity(
            billingAmount = dataList.sumOf { it.billingAmount ?: 0 },
            medicalReceipts = dataList.sumOf { it.medicalReceipts ?: 0 },
            ownExpense = dataList.sumOf { it.ownExpense ?: 0 },
            nonPayment = dataList.sumOf { it.nonPayment ?: 0 },
            etcAmount = dataList.sumOf { it.etcAmount },
            ownExpenseAmount = dataList.sumOf { it.ownExpenseAmount ?: 0 },
            totalSalary = dataList.sumOf { it.totalSalary ?: 0 }
        )
    }


    fun hospitalChartSave(hospitalChartList: List<HospitalChartEntity>): ResponseEntity<Any> {
        log.info { hospitalChartList }
        hospitalChartRepository.saveAll(hospitalChartList)
        return ResponseEntity.ok(HttpStatus.OK.value())
    }


}