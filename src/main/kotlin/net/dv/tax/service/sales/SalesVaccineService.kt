package net.dv.tax.service.sales

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.domain.sales.SalesVaccineEntity
import net.dv.tax.repository.sales.SalesVaccineRepository
import net.dv.tax.utils.ExcelComponent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SalesVaccineService(
    private val vaccineSalesRepository: SalesVaccineRepository,
    private val excelWriterService: ExcelComponent,
) {


    private val log = KotlinLogging.logger {}


    fun vaccineYearList(hospitalId: String, year: Int): List<SalesVaccineEntity>? {
        return vaccineSalesRepository.findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId, year)
    }

    @Transactional
    fun vaccineSave(hospitalId: String, salesVaccineEntity: List<SalesVaccineEntity>): ResponseEntity<Any> {
//        val localDate = LocalDate.parse("01-06-2022", DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        /*TODO Writer 계정에서 추가 해야됨*/
        vaccineSalesRepository.saveAll(salesVaccineEntity)

        return ResponseEntity.ok(HttpStatus.OK.value())
    }

    fun vaccineListMakeExcel(hospitalId: String, response: HttpServletResponse) {
        excelWriterService.downloadExcel(response, "test")
            .outputStream.use { os ->
                excelWriterService.createXlsx(os, getListForFastExcel(hospitalId))
            }
    }

    /**
     * TEST #58 인지하냐
     */
        private fun getListForFastExcel(hospitalId: String): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        vaccineSalesRepository.findAllByHospitalIdOrderByMonthAscYearAsc(hospitalId)?.forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["기간"] = it.year.toString() + " ." + it.month.toString()
            tempMap["지급완료 건수"] = it.payCount!!
            tempMap["지급금액"] = it.payAmount!!
            tempMap["작성자"] = it.writer!!
            tempMap["작성일"] = it.createdAt
            list.add(tempMap)
        }

        return list
    }

}