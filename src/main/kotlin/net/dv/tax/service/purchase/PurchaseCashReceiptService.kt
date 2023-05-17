package net.dv.tax.service.purchase

import mu.KotlinLogging
import net.dv.tax.domain.purchase.PurchaseCashReceiptEntity
import net.dv.tax.dto.purchase.ExcelRequiredDto
import net.dv.tax.dto.purchase.PurchaseCashReceiptDto
import net.dv.tax.dto.purchase.PurchaseCashReceiptListDto
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.enums.purchase.getDeductionName
import net.dv.tax.enums.purchase.getRecommendDeductionName
import net.dv.tax.repository.purchase.PurchaseCashReceiptRepository
import net.dv.tax.service.common.SendQueueService
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Service

@Service
class PurchaseCashReceiptService(
    private val purchaseCashReceiptRepository: PurchaseCashReceiptRepository,
    private val sendQueueService: SendQueueService,
) {

    private val log = KotlinLogging.logger {}

    fun excelToEntitySave(requiredDto: ExcelRequiredDto, rows: MutableList<Row>) {

        val dataList = mutableListOf<PurchaseCashReceiptEntity>()

        /*Remove Title*/
        rows.removeFirst()

        /*Remove sum data 카드리스트는 하위 2개*/
        rows.removeLast()
        rows.removeLast()


        rows.forEach {

            val billingDate = requiredDto.year + "-" + it.getCell(0).rawValue

            var isDeduction = false
            var isRecommendDeduction = false

            /**
             * True : 공제
             * False : 불공제
             */
            if (it.getCell(9)?.rawValue?.length == 2) {
                isDeduction = true
            }

            /**
             * False : Empty
             * True : 불공제 추천
             */
            if (it.getCell(10).rawValue != null) {
                isRecommendDeduction = true
            }

            val data =
                PurchaseCashReceiptEntity(
                    hospitalId = requiredDto.hospitalId,
                    billingDate = billingDate,
                    dataFileId = requiredDto.fileDataId,
                    accountCode = it.getCell(1)?.rawValue,
                    franchiseeName = it.getCell(2)?.rawValue,
                    corporationType = it.getCell(3)?.rawValue,
                    itemName = it.getCell(4)?.rawValue,
                    supplyPrice = it.getCell(5)?.rawValue?.toDouble()?.toLong(),
                    taxAmount = it.getCell(6)?.rawValue?.toDouble()?.toLong(),
                    serviceCharge = it.getCell(7)?.rawValue?.toDouble()?.toLong(),
                    totalAmount = it.getCell(8)?.rawValue?.toDouble()?.toLong(),
                    isDeduction = isDeduction,
                    isRecommendDeduction = isRecommendDeduction,
                    statementType1 = it.getCell(11)?.rawValue,
                    statementType2 = it.getCell(12)?.rawValue,
                    debtorAccount = it.getCell(13)?.rawValue,
                    creditAccount = it.getCell(14)?.rawValue,
                    separateSend = it.getCell(15)?.rawValue,
                    department = it.getCell(16)?.rawValue,
                    statementStatus = it.getCell(17)?.rawValue,
                    writer = requiredDto.writer,
                )
            dataList.add(data)
        }
        log.error { dataList }
//        sendQueueService.sandMessage(SendQueueDto(MenuCategoryCode.CASH_RECEIPT.name, dataList))

//        purchaseCashReceiptRepository.saveAll(dataList)
    }

    fun getPurchaseCashReceipt(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCashReceiptListDto {

        var CashReceiptList = getPurchaseCashReceiptList(hospitalId, purchaseQueryDto, false)
        var totalCount = purchaseCashReceiptRepository.purchaseCashReceiptListCnt(hospitalId, purchaseQueryDto)
        var purchaseCashReceiptTotal =
            purchaseCashReceiptRepository.purchaseCashReceiptTotal(hospitalId, purchaseQueryDto)

        return PurchaseCashReceiptListDto(
            listPurchaseCashReceipt = CashReceiptList,
            purchaseCashReceiptTotal = purchaseCashReceiptTotal,
            totalCount = totalCount
        )
    }

    fun getPurchaseCashReceiptList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean,
    ): List<PurchaseCashReceiptDto> {
        return purchaseCashReceiptRepository.purchaseCashReceiptList(hospitalId, purchaseQueryDto, isExcel)
            .map { purchaseCashReceipt ->
                PurchaseCashReceiptDto(
                    id = purchaseCashReceipt.id,
                    hospitalId = purchaseCashReceipt.hospitalId,
                    dataFileId = purchaseCashReceipt.dataFileId,
                    billingDate = purchaseCashReceipt.billingDate,
                    accountCode = purchaseCashReceipt.accountCode,
                    franchiseeName = purchaseCashReceipt.franchiseeName,
                    corporationType = purchaseCashReceipt.corporationType,
                    itemName = purchaseCashReceipt.itemName,
                    supplyPrice = purchaseCashReceipt.supplyPrice,
                    taxAmount = purchaseCashReceipt.taxAmount,
                    totalAmount = purchaseCashReceipt.totalAmount,
                    deductionName = getDeductionName(purchaseCashReceipt.isDeduction!!),
                    isDeduction = purchaseCashReceipt.isDeduction!!,
                    recommendDeductionName = getRecommendDeductionName(purchaseCashReceipt.isRecommendDeduction!!),
                    isRecommendDeduction = purchaseCashReceipt.isRecommendDeduction!!,
                    statementType1 = purchaseCashReceipt.statementType1,
                    statementType2 = purchaseCashReceipt.statementType2,
                    debtorAccount = purchaseCashReceipt.debtorAccount,
                    creditAccount = purchaseCashReceipt.creditAccount,
                    separateSend = purchaseCashReceipt.separateSend,
                    statementStatus = purchaseCashReceipt.statementStatus,
                    writer = purchaseCashReceipt.writer,
                )
            }
    }

}