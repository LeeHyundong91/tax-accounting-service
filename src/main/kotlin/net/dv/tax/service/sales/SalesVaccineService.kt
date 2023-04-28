package net.dv.tax.service.sales

import mu.KotlinLogging
import net.dv.tax.domain.sales.SalesVaccineEntity
import net.dv.tax.dto.sales.SalesVaccineListDto
import net.dv.tax.repository.sales.SalesVaccineRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SalesVaccineService(
    private val vaccineSalesRepository: SalesVaccineRepository,
) {

    private val log = KotlinLogging.logger {}


    fun getList(hospitalId: String, year: String): SalesVaccineListDto {
        val dataList = vaccineSalesRepository.findAllByHospitalIdAndPaymentMonthYearStartingWithOrderByPaymentMonthYear(
            hospitalId,
            year
        )
        return SalesVaccineListDto(
            dataList,
            totalList(dataList)
        )
    }

    fun totalList(dataList: List<SalesVaccineEntity>): SalesVaccineEntity {
        return SalesVaccineEntity(
            payCount = dataList.sumOf { it.payCount ?: 0 },
            payAmount = dataList.sumOf { it.payAmount ?: 0 },
        )
    }

    @Transactional
    fun vaccineSave(hospitalId: String, salesVaccineEntity: List<SalesVaccineEntity>): ResponseEntity<Any> {
        /*TODO Writer 계정에서 추가 해야됨*/
        vaccineSalesRepository.saveAll(salesVaccineEntity)

        return ResponseEntity.ok(HttpStatus.OK.value())
    }


//    @Comment("지급완료 건수")
//    var payCount: Long? = 0,
//
//    @Comment("지급금액")
//    var payAmount: Long? = 0,


}