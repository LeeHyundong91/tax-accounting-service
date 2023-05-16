package net.dv.tax.service.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.SalesPaymentMethodEntity
import net.dv.tax.repository.consulting.SalesPaymentMethodRepository
import net.dv.tax.repository.sales.*
import net.dv.tax.service.sales.HospitalChartService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SalesPaymentMethodService(
    private val salesCreditCardRepository: SalesCreditCardRepository,
    private val salesCashReceiptRepository: SalesCashReceiptRepository,
    private val salesAgentRepository: SalesAgentRepository,
    private val medicalCareRepository: MedicalCareRepository,
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val carInsuranceRepository: CarInsuranceRepository,
    private val hospitalChartService: HospitalChartService,
    private val salesPaymentMethodRepository: SalesPaymentMethodRepository,

    ) {

    private val log = KotlinLogging.logger {}


    fun saveData(data: SalesPaymentMethodEntity): ResponseEntity<Any> {

        salesPaymentMethodRepository.findById(data.id!!).get().also {
            log.error { "ORIGIN_DATA" }
            log.error { it }
            log.error { "END_OF_ORIGIN" }
            /**
             * 실제 받아야 할 데이터
             */
            /*수정신고 금액*/
            var revisedAmount = data.revisedAmount ?: 0
            /*진료비 할인 금액*/
            var discountAmount = data.discountAmount ?: 0
            /*미수금*/
            var receivableAmount = data.receivableAmount ?: 0

            /*백분율 계산을 위한 Total 데이터가 바뀌어서 또 합처야함 ㅅㅂ*/
            var totalAmount =
                it.totalAmount!!.plus(data.revisedAmount!!).plus(data.discountAmount!!).plus(data.receivableAmount!!)
            /*수정신고 때문에 소계값도 바뀜 */
            var smallSumAmount = it.smallSumAmount!!.plus(data.revisedAmount!!)


            it.creditCardRatio = it.creditCardAmount!!.toFloat().div(totalAmount) * 100
            it.cashReceiptRatio = it.cashReceiptAmount!!.toFloat().div(totalAmount) * 100
            it.salesAgentRatio = it.salesAgentAmount!!.toFloat().div(totalAmount) * 100

            it.corpPayRatio = it.corpPayAmount!!.toFloat().div(totalAmount) * 100
            it.insPayRatio = it.insPayAmount!!.toFloat().div(totalAmount) * 100
            it.actualCashRatio = it.actualCashAmount!!.toFloat().div(totalAmount) * 100
            it.revisedAmount = revisedAmount
            it.revisedRatio = revisedAmount.toFloat().div(totalAmount) * 100

            it.smallSumAmount = smallSumAmount
            it.smallSumRatio = smallSumAmount.toFloat().div(totalAmount) * 100

            it.discountAmount = discountAmount
            it.discountRatio = discountAmount.toFloat().div(totalAmount) * 100

            it.receivableAmount = receivableAmount
            it.receivableRatio = receivableAmount.toFloat().div(totalAmount) * 100

            it.totalAmount = totalAmount

            log.error { "LAST_DATA" }
            log.error { it }
            salesPaymentMethodRepository.save(it)
        }

        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun getData(hospitalId: String, year: String): SalesPaymentMethodEntity {
        return salesPaymentMethodRepository.findTopByHospitalIdAndResultYearMonth(hospitalId, year)
    }

    fun makeData(hospitalId: String, year: String) {

        var hospitalChart = hospitalChartService.getList(hospitalId, year).listTotal

        var creditCardAmount = salesCreditCardRepository.monthlySumAmount(hospitalId, year) ?: 0
        var cashReceiptAmount = salesCashReceiptRepository.monthlySumAmount(hospitalId, year) ?: 0
        var salesAgentAmount = salesAgentRepository.monthlySumAmount(hospitalId, year) ?: 0
        var medicalCareAgencyDues = medicalCareRepository.monthlySumAmount(hospitalId, year) ?: 0
        var medicalBenefitAgencyDues = medicalBenefitsRepository.monthlySumAmount(hospitalId, year) ?: 0
        var carInsuranceAmount = carInsuranceRepository.monthlySumAmount(hospitalId, year) ?: 0

        /**
         * for Calc
         */
        /*본인부담금*/
        var ownExpense = hospitalChart?.ownExpense ?: 0
        /*비급여*/
        var nonPayment = hospitalChart?.nonPayment ?: 0
        /*본인부담금 + 비급여*/
        var actualCashFirstCondition = ownExpense.plus(nonPayment)
        /*카드 + 현금영수증*/
        var actualCashSecondCondition = creditCardAmount.plus(cashReceiptAmount)
        /*실제순현금 = (본인부담금 + 비급여) - (카드 + 현금영수증)*/
        var actualCash = actualCashFirstCondition.minus(actualCashSecondCondition)
        /*공단부담분 = 의료급여(기관부담금) + 요양급여(공단부담금)*/
        var corpPayAmount = medicalCareAgencyDues.plus(medicalBenefitAgencyDues)
        /*소계*/
        var cashAmount = corpPayAmount.plus(carInsuranceAmount).plus(actualCash)

        var totalAmount = creditCardAmount.plus(cashReceiptAmount).plus(salesAgentAmount).plus(cashAmount)


        val data =
            SalesPaymentMethodEntity(
                hospitalId = hospitalId,
                resultYearMonth = year,
                creditCardAmount = creditCardAmount,
                creditCardRatio = creditCardAmount.toFloat().div(totalAmount) * 100,
                cashReceiptAmount = cashReceiptAmount,
                cashReceiptRatio = cashReceiptAmount.toFloat().div(totalAmount) * 100,
                salesAgentAmount = salesAgentAmount,
                salesAgentRatio = salesAgentAmount.toFloat().div(totalAmount) * 100,
                corpPayAmount = corpPayAmount,
                corpPayRatio = corpPayAmount.toFloat().div(totalAmount) * 100,
                insPayAmount = carInsuranceAmount,
                insPayRatio = carInsuranceAmount.toFloat().div(totalAmount) * 100,
                actualCashAmount = actualCash,
                actualCashRatio = actualCash.toFloat().div(totalAmount) * 100,
                smallSumAmount = cashAmount,
                smallSumRatio = cashAmount.toFloat().div(totalAmount) * 100,
                totalAmount = totalAmount,
                totalRatio = totalAmount.toFloat().div(totalAmount) * 100,
            )

        salesPaymentMethodRepository.save(data)

    }


}