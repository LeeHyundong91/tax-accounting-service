package net.dv.tax.service.purchase

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import net.dv.tax.dto.MenuCategoryCode
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.enum.purchase.getDeductionName
import net.dv.tax.enum.purchase.getRecommendDeductionName
import net.dv.tax.repository.sales.*
import net.dv.tax.utils.ExcelComponent
import org.springframework.stereotype.Component
import java.util.*
import kotlin.reflect.full.memberProperties

@Component
class PurchaseExcelDownloadService(
    private val excelComponent: ExcelComponent,
    private val purchaseElecInvoiceService: PurchaseElecInvoiceService,
    private val purchaseCreditCardService: PurchaseCreditCardService,
    private val purchaseCashReceiptService: PurchaseCashReceiptService,
    ) {

    private val log = KotlinLogging.logger {}

    fun makeExcel(
        purchaseQueryDto: PurchaseQueryDto,
        hospitalId: String,
        categoryCode: String,
        response: HttpServletResponse,
        ) {

        val menu = MenuCategoryCode.valueOf(MenuCategoryCode.convert(categoryCode))

        excelComponent.downloadExcel(response, menu.purchaseFileName)
            .outputStream.use { os ->
                excelComponent.createXlsx(os, getListForExcel(purchaseQueryDto, hospitalId, menu))
            }
    }

    private fun getListForExcel(
        purchaseQueryDto: PurchaseQueryDto,
        hospitalId: String,
        categoryCode: MenuCategoryCode,
    ): List<Map<String, Any>> {

        return when (categoryCode){
            MenuCategoryCode.ELEC_INVOICE -> purchaseElecInvoice(hospitalId, purchaseQueryDto);
            MenuCategoryCode.CREDIT_CARD -> purchaseCreditCard(hospitalId, purchaseQueryDto);
            MenuCategoryCode.CASH_RECEIPT -> purchaseCashReceipt(hospitalId, purchaseQueryDto);
            else -> mutableListOf()
        }
    }

    private fun purchaseElecInvoice(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        purchaseElecInvoiceService.getPurchaseElecInvoiceList(hospitalId, purchaseQueryDto, true).forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["발급 일자"] = it.issueDate!!
            tempMap["전송 일자"] = it.sendDate!!
            tempMap["코드"] = it.accountCode!!
            tempMap["거래처"] = it.franchiseeName!!
            tempMap["품명"] = it.itemName!!
            tempMap["공급가액"] = it.supplyPrice!!
            tempMap["세액"] = it.taxAmount!!
            tempMap["합계"] = it.totalAmount!!
            tempMap["유형(공제여부)"] = it.isDeduction!!
            tempMap["차변계정"] = it.debtorAccount!!
            tempMap["대변계정"] = it.creditAccount!!
            tempMap["분개전송"] = it.separateSend?: ""
            tempMap["전표상태"] = it.statementStatus!!
            tempMap["작업상태"] = it.taskType?: ""
            tempMap["승인번호"] = it.approvalNo!!
            tempMap["종류(계산서)"] = it.invoiceType!!
            tempMap["구분(청구)"] = it.billingType!!
//             tempMap["발급유형"] = String!!
            tempMap["작성자"] = it.writer!!

            list.add(tempMap)
        }
        return list
    }
    private fun purchaseCreditCard(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        purchaseCreditCardService.getPurchaseCreditCardList(hospitalId, purchaseQueryDto, true).forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["일자"] = it.billingDate!!
            tempMap["코드"] = it.accountCode?:""
            tempMap["거래처"] = it.franchiseeName!!
            tempMap["구분"] = it.corporationType!!
            tempMap["품명"] = it.itemName?:""
            tempMap["공급가액"] = it.supplyPrice!!
            tempMap["세액"] = it.taxAmount?:""
            tempMap["비과세"] = it.nonTaxAmount?:""
            tempMap["합계"] = it.totalAmount!!
            tempMap["국세청(공제여부)"] = getDeductionName(it.isDeduction!!)
            tempMap["추천유형"] = getRecommendDeductionName(it.isRecommendDeduction!!)
            tempMap["전표유형1"] = it.statementType1!!
            tempMap["전표유형2"] = it.statementType2?:""
            tempMap["차변계정"] = it.debtorAccount!!
            tempMap["대변계정"] = it.creditAccount!!
            tempMap["분개전송"] = it.separateSend?:""
            tempMap["전표상태"] = it.statementStatus!!
            tempMap["작성자"] = it.writer!!
            list.add(tempMap)
        }
        return list
    }

    private fun purchaseCashReceipt(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): List<Map<String, Any>> {
        val list: MutableList<Map<String, Any>> = LinkedList()

        purchaseCashReceiptService.getPurchaseCashReceiptList(hospitalId, purchaseQueryDto, true).forEach {
            val tempMap: MutableMap<String, Any> = LinkedHashMap()
            tempMap["일자"] = it.billingDate!!
            tempMap["코드"] = it.accountCode?: ""
            tempMap["거래처"] = it.franchiseeName!!
            tempMap["구분"] = it.corporationType!!
            tempMap["품명"] = it.itemName?: ""
            tempMap["공급가액"] = it.supplyPrice!!
            tempMap["세액"] = it.taxAmount?: ""
            tempMap["봉사료"] = it.serviceCharge?: ""
            tempMap["합계"] = it.totalAmount!!
            tempMap["국세청(공제여부)"] = it.deductionName?: ""
            tempMap["추천유형"] = it.recommendDeductionName?: ""
            tempMap["전표유형1"] = it.statementType1!!
            tempMap["전표유형2"] = it.statementType2?: ""
            tempMap["차변계정"] = it.debtorAccount!!
            tempMap["대변계정"] = it.creditAccount!!
            tempMap["분개전송"] = it.separateSend?: ""
            tempMap["전표상태"] = it.statementStatus!!
            tempMap["작성자"] = it.writer!!
            list.add(tempMap)
        }
        return list
    }




}