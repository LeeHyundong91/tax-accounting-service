package net.dv.tax.service.sales

import com.github.javaxcel.core.Javaxcel
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.domain.sales.SalesVaccineEntity
import net.dv.tax.dto.sales.SalesVaccineExcelDto
import net.dv.tax.repository.sales.SalesVaccineRepository
import net.dv.tax.utils.ExcelComponent
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.OutputStream
import java.util.*

@Service
class SalesVaccineService(
    private val vaccineSalesRepository: SalesVaccineRepository,
    private val excelWriterService: ExcelComponent,
) {


    private val log = KotlinLogging.logger {}


    fun vaccineYearList(hospitalId: Int, year: Int): List<SalesVaccineEntity>? {
        return vaccineSalesRepository.findAllByHospitalIdAndYearOrderByMonthAsc(hospitalId, year)
    }

    @Transactional
    fun vaccineSave(hospitalId: Int, salesVaccineEntity: List<SalesVaccineEntity>): ResponseEntity<Any> {
//        val localDate = LocalDate.parse("01-06-2022", DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        /*TODO Writer 계정에서 추가 해야됨*/
        vaccineSalesRepository.saveAll(salesVaccineEntity)

        return ResponseEntity.ok(HttpStatus.OK.value())
    }

    fun vaccineListMakeExcel(hospitalId: Int, response: HttpServletResponse) {
        excelWriterService.downloadExcel(response, "test")
            .outputStream.use { os ->
                getListForExcel(os, hospitalId)
            }
    }

    /**
     * TEST #58 인지하냐
     */
    private fun getListForExcel(os: OutputStream?, hospitalId: Int) {

        val excelList: MutableList<SalesVaccineExcelDto> = LinkedList()
        val workbook: Workbook = HSSFWorkbook()

        vaccineSalesRepository.findAllByHospitalIdOrderByMonthAscYearAsc(hospitalId)?.forEach {
            val excelDto = SalesVaccineExcelDto(
                it.year.toString() + " ." + it.month.toString(),
                it.payCount!!,
                it.payAmount!!,
                it.writer!!,
                it.createdAt
            )
            excelList.add(excelDto)
        }

        Javaxcel.newInstance()
            .writer(workbook, SalesVaccineExcelDto::class.java)
            .write(os, excelList)

    }

}