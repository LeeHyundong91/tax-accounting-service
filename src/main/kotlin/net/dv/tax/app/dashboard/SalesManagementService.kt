package net.dv.tax.app.dashboard

import mu.KotlinLogging
import net.dv.tax.app.dto.app.MonthlyPayMethodReportDto
import net.dv.tax.app.dto.app.MonthlyReportDto
import net.dv.tax.app.dto.app.MonthlySalesTotalReportDto
import net.dv.tax.app.dto.app.MonthlySalesTypeReportDto
import net.dv.tax.app.consulting.SalesPaymentMethodService
import net.dv.tax.app.consulting.SalesTypeService
import org.springframework.stereotype.Service
import java.time.YearMonth

@Service
class SalesManagementService(
    private val salesPaymentMethodService: SalesPaymentMethodService,
    private val salesTypeService: SalesTypeService,
) {
    private val log = KotlinLogging.logger {}


    fun getSalesManagementList(hospitalId: String, yearMonth: String): MonthlyReportDto {


        log.error { "yearMonth $yearMonth" }

        val currentMonthData = salesPaymentMethodService.makeData(hospitalId, yearMonth.toString())

        val currentAmount = currentMonthData.totalAmount ?: 0
        val beforeAmount =
            salesPaymentMethodService.makeData(hospitalId, YearMonth.parse(yearMonth).minusMonths(1).toString()).totalAmount ?: 0
        val beforeYear =
            salesPaymentMethodService.makeData(hospitalId, YearMonth.parse(yearMonth).minusYears(1).toString()).totalAmount ?: 0


        val salesTotal = MonthlySalesTotalReportDto(
            currentMonthAmount = currentAmount,
            compareMonthAmount = currentAmount.minus(beforeAmount),
            compareMonthRatio = beforeAmount.toFloat().div(currentAmount) * 100,
            beforeMonthAmount = beforeAmount,
            beforeYearMonthAmount = beforeYear,
            compareYearMonthAmount = currentAmount.minus(beforeYear)
        )

        val creditCard = currentMonthData.creditCardAmount ?: 0
        val cashReceipt = currentMonthData.cashReceiptAmount ?: 0
        val actualCash = currentMonthData.actualCashAmount ?: 0
        val salesTypeTotalAmount = creditCard.plus(cashReceipt).plus(actualCash)

        val payMethod = MonthlyPayMethodReportDto(
            creditCardAmount = creditCard,
            creditCardRatio = creditCard.toFloat().div(salesTypeTotalAmount) * 100,

            cashReceiptAmount = cashReceipt,
            cashReceiptRatio = cashReceipt.toFloat().div(salesTypeTotalAmount) * 100,

            cashAmountAmount = actualCash,
            cashRatio = actualCash.toFloat().div(salesTypeTotalAmount) * 100,
        )

        val salesTypeData = salesTypeService.makeData(hospitalId, yearMonth.toString())

        val salesType = MonthlySalesTypeReportDto(
            itemList = salesTypeData.detailList
        )

        println("--> sales type data ")


        return MonthlyReportDto(
            totalAmountData = salesTotal,
            payMethodData = payMethod,
            salesTypeData = salesType

        )
    }


}