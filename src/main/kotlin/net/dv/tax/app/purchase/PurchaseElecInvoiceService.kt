package net.dv.tax.app.purchase

import mu.KotlinLogging
import net.dv.tax.app.dto.purchase.ExcelRequiredDto
import net.dv.tax.app.dto.purchase.PurchaseElecInvoiceDto
import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PurchaseElecInvoiceService(
    private val purchaseElecInvoiceRepository: PurchaseElecInvoiceRepository,
) {
    private val log = KotlinLogging.logger {}

//    TODO(자료정리 필요)
//    fun saveElecInvoiceList(elecInvoiceList: List<PurchaseElecInvoiceEntity>) {
//        purchaseElecInvoiceRepository.saveAll(elecInvoiceList)
//    }

    /**
     * TODO Only Use Single Upload Excel File
     * do not use xls
     * only accept xlsx
     */
    fun excelToEntitySave(requiredDto: ExcelRequiredDto, rows: MutableList<Row>) {

        val dataList = mutableListOf<PurchaseElecInvoiceEntity>()

        /*첫번째 행 삭제*/
        rows.removeFirst()

        /*하위 두개행 삭제 */
        rows.removeLast()
        rows.removeLast()

        rows.forEach {

            val issueDate = LocalDate.parse(it.getCell(1)?.rawValue)
            val sendDate = LocalDate.parse(it.getCell(2)?.rawValue)

            val useInForPurchaseElecInvoiceEntity =
                PurchaseElecInvoiceEntity(
                    hospitalId = requiredDto.hospitalId,
                    dataFileId = requiredDto.fileDataId,
                    issueDate = it.getCell(1)?.rawValue,
                    sendDate = it.getCell(2)?.rawValue,
                    accountCode = it.getCell(3)?.rawValue,
                    franchiseeName = it.getCell(4)?.rawValue,
                    itemName = it.getCell(5)?.rawValue,
                    supplyPrice = it.getCell(6)?.rawValue?.toDouble()?.toLong(),
                    taxAmount = it.getCell(7)?.rawValue?.toDouble()?.toLong(),
                    totalAmount = it.getCell(8)?.rawValue?.toDouble()?.toLong(),
                    isDeduction = it.getCell(9)?.rawValue,
                    debtorAccount = it.getCell(10)?.rawValue,
                    creditAccount = it.getCell(11)?.rawValue,
                    separateSend = it.getCell(12)?.rawValue,
                    statementStatus = it.getCell(13)?.rawValue,
                    taskType = it.getCell(14)?.rawValue,
                    approvalNo = it.getCell(15)?.rawValue,
                    invoiceType = it.getCell(16)?.rawValue,
                    billingType = it.getCell(17)?.rawValue,
                    issueType = it.getCell(18)?.rawValue,
                    writer = requiredDto.writer,
                    tax = requiredDto.isTax
                )
            dataList.add(useInForPurchaseElecInvoiceEntity)
        }
//        sendQueueService.sandMessage(SendQueueDto(MenuCategoryCode.ELEC_TAX_INVOICE.name, dataList))

    }

//    TODO(정리작업 필요)
//    fun getPurchaseElecInvoice(hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseElecInvoiceListDto {
//
//        var elecInvoiceList = getPurchaseElecInvoiceList(hospitalId, purchaseQueryDto, false)
//        var totalCount = purchaseElecInvoiceRepository.purchaseElecInvoiceListCnt(hospitalId, purchaseQueryDto)
//        var purchaseElecInvoiceTotal =
//            purchaseElecInvoiceRepository.purchaseElecInvoiceTotal(hospitalId, purchaseQueryDto)
//
//        return PurchaseElecInvoiceListDto(
//            listPurchaseElecInvoice = elecInvoiceList,
//            totalCount = totalCount,
//            purchaseElecInvoiceTotal = purchaseElecInvoiceTotal
//        )
//    }

    fun getPurchaseElecInvoiceList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean,
    ): List<PurchaseElecInvoiceDto> {

        var elecInvoiceList =
            purchaseElecInvoiceRepository.purchaseElecInvoiceList(hospitalId, purchaseQueryDto, isExcel)
                .map { purchaseElecInvoice ->
                    PurchaseElecInvoiceDto(
                        id = purchaseElecInvoice.id,
                        issueDate = purchaseElecInvoice.issueDate,
                        sendDate = purchaseElecInvoice.sendDate,
                        accountCode = purchaseElecInvoice.accountCode,
                        franchiseeName = purchaseElecInvoice.franchiseeName,
                        itemName = purchaseElecInvoice.itemName,
                        supplyPrice = purchaseElecInvoice.supplyPrice,
                        taxAmount = purchaseElecInvoice.taxAmount,
                        totalAmount = purchaseElecInvoice.totalAmount,
                        isDeduction = purchaseElecInvoice.isDeduction,
                        debtorAccount = purchaseElecInvoice.debtorAccount,
                        creditAccount = purchaseElecInvoice.creditAccount,
                        separateSend = purchaseElecInvoice.separateSend,
                        statementStatus = purchaseElecInvoice.statementStatus,
                        taskType = purchaseElecInvoice.taskType,
                        approvalNo = purchaseElecInvoice.approvalNo,
                        invoiceType = purchaseElecInvoice.invoiceType,
                        billingType = purchaseElecInvoice.billingType,
                        issueType = purchaseElecInvoice.issueType,
                        writer = purchaseElecInvoice.writer,
                    )
                }

        return elecInvoiceList

    }
}