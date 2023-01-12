package net.dv.tax.service

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.domain.VaccineSalesEntity
import net.dv.tax.repository.VaccineSalesRepository
import net.dv.tax.utils.ExcelWriterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class VaccineSalesService(
    private val vaccineSalesRepository: VaccineSalesRepository,
    private val excelWriterService: ExcelWriterService
) {


    private val log = KotlinLogging.logger {}


    fun vaccineYearList(hospitalId: Int, year: String): List<VaccineSalesEntity>? {
        return vaccineSalesRepository.findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId, year)
    }

    @Transactional
    fun vaccineSave(hospitalId: Int, vaccineSalesEntity: List<VaccineSalesEntity>): ResponseEntity<Any> {
//        val localDate = LocalDate.parse("01-06-2022", DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        /*TODO Writer 계정에서 추가 해야됨*/
        vaccineSalesRepository.saveAll(vaccineSalesEntity)

        return ResponseEntity.ok(HttpStatus.OK.value())
    }

    fun vaccineListMakeExcel(hospitalId: Int, response: HttpServletResponse){
        excelWriterService.downloadExcel(response,"test", getListForFastExcel())
    }

    private fun getListForFastExcel(): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        vaccineSalesRepository.findAllByHospitalIdAndYearOrderByMonthAsc(1, "2022")?.forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["기간"] = it.year.toString() + " ." + it.month.toString()
            tempMap["지급완료 건수"] = it.payCount!!
            tempMap["지급금액"] = it.payAmount!!
            tempMap["작성자"] = it.writer
            tempMap["작성일"] = it.createdAt
            list.add(tempMap)
        }

        return list
    }

}