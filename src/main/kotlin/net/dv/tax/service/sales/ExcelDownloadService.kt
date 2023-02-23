package net.dv.tax.service.sales

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.dto.MenuCategoryCode
import net.dv.tax.repository.sales.CarInsuranceRepository
import net.dv.tax.repository.sales.MedicalBenefitsRepository
import net.dv.tax.repository.sales.MedicalExamRepository
import net.dv.tax.repository.sales.SalesVaccineRepository
import net.dv.tax.utils.ExcelComponent
import org.springframework.stereotype.Component
import java.util.*

@Component
class ExcelDownloadService(
    private val excelComponent: ExcelComponent,
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val carInsuranceRepository: CarInsuranceRepository,
    private val vaccineRepository: SalesVaccineRepository,
    private val medicalExamRepository: MedicalExamRepository

) {

    private val log = KotlinLogging.logger {}

    fun makeExcel(
        year: String,
        hospitalId: String,
        categoryCode: String,
        response: HttpServletResponse,
    ) {

        var codeName: String? = null
        MenuCategoryCode.values().forEach { enums ->
            if (enums.code == categoryCode) {
                codeName = enums.name
            }
        }

        val menu = MenuCategoryCode.valueOf(codeName!!)

        excelComponent.downloadExcel(response, year + "_" + menu.fileName)
            .outputStream.use { os ->
                excelComponent.createXlsx(os, getListForExcel(year, hospitalId, menu))
            }
    }


    private fun getListForExcel(
        year: String,
        hospitalId: String,
        categoryCode: MenuCategoryCode,
    ): List<Map<String, Any>> {
        return when (categoryCode) {
            MenuCategoryCode.MEDICAL_BENEFITS -> benefits(hospitalId, year)
            MenuCategoryCode.CAR_INSURANCE -> carInsurance(hospitalId, year)
            MenuCategoryCode.VACCINE -> vaccine(hospitalId)
            MenuCategoryCode.MEDICAL_EXAM -> medicalExam(hospitalId, year)
            MenuCategoryCode.EMPLOYEE_INDUSTRY -> TODO()
            MenuCategoryCode.HOSPITAL_CHART -> TODO()
            MenuCategoryCode.CREDIT_CARD -> TODO()
            MenuCategoryCode.CASH_RECEIPT -> TODO()
            MenuCategoryCode.ELEC_INVOICE -> TODO()
            MenuCategoryCode.HAND_INVOICE -> TODO()
        }

    }

    /*http://localhost:8080/v1/sales/download/car-insurance/2022/cid01*/
    private fun medicalExam(hospitalId: String, year: String): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        medicalExamRepository.groupingList(hospitalId, year).forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["기간"] = it.dataPeriod!!
            tempMap["건수"] = it.benefitsCount!!
            tempMap["접수금액"] = it.receptionAmount!!
            tempMap["총의료급여비용"] = it.medicalBenefitsAmount!!
            tempMap["본인부담금"] = it.ownCharge!!
            tempMap["장애인의료비"] = it.disabledExpenses!!
            tempMap["기관부담금"] = it.agencyExpenses!!
            tempMap["절사금액"] = it.cutOffAmount!!
            tempMap["기금부담금"] = it.fundExpense!!
            tempMap["대불금"] = it.proxyPayment!!
            tempMap["본인부담환급금"] = it.refundPaid!!
            tempMap["검사기관지급액"] = it.agencyPayment!!
            tempMap["당차수 실지급액"] = it.actualPayment!!
            list.add(tempMap)
        }
        return list
    }

    private fun carInsurance(hospitalId: String, year: String): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        carInsuranceRepository.groupingList(hospitalId, year).forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["기간"] = it.dataPeriod!!
            tempMap["심사결정액"] = it.decisionAmount!!
            tempMap["청구액"] = it.billingAmount!!

            list.add(tempMap)
        }

        return list
    }

    private fun vaccine(hospitalId: String): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        vaccineRepository.findAllByHospitalIdOrderByMonthAscYearAsc(hospitalId)?.forEach {
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

    private fun benefits(hospitalId: String, year: String): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        medicalBenefitsRepository.groupingList(hospitalId, year).forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["기간"] = it.dataPeriod!!
            tempMap["요양급여합계"] = it.totalAmount!!
            tempMap["본인부담금"] = it.ownExpense!!
            tempMap["공단부담금"] = it.corporationExpense!!
            tempMap["접수액"] = it.amountReceived!!
            tempMap["실지급액"] = it.actualPayment!!

            list.add(tempMap)
        }
        return list
    }

}