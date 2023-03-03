package net.dv.tax.service.purchase

import mu.KotlinLogging
import net.dv.tax.domain.purchase.PurchaseElecInvoiceEntity
import net.dv.tax.dto.purchase.ExcelRequiredDto
import net.dv.tax.repository.purchase.PurchaseElecInvoiceRepository
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PurchaseElecInvoiceService(
    private val purchaseElecInvoiceRepository: PurchaseElecInvoiceRepository,
) {
    private val log = KotlinLogging.logger {}

    fun saveElecInvoiceList(elecInvoiceList: List<PurchaseElecInvoiceEntity>) {
        purchaseElecInvoiceRepository.saveAll(elecInvoiceList)
    }

    /**
     * TODO Only Use Single Upload Excel File
     * do not use xls
     * only accept xlsx
     */

    fun excelToEntitySave(requiredDto: ExcelRequiredDto, rows: MutableList<Row>) {

        val elecInvoiceList = mutableListOf<PurchaseElecInvoiceEntity>()

        /*첫번째 행 삭제*/
        rows.removeFirst()

        /*하위 두개행 삭제 */
        rows.removeLast()
        rows.removeLast()

        rows.forEach {

            val issuDate = LocalDate.parse(it.getCell(1)?.rawValue)
            val sendDate = LocalDate.parse(it.getCell(2)?.rawValue)

            val useInForPurchaseElecInvoiceEntity =
                PurchaseElecInvoiceEntity(
                    hospitalId = requiredDto.hospitalId,
                    issueDate = issuDate,
                    sendDate = sendDate,
                    accountCode = it.getCell(3)?.rawValue,
                    franchiseeName = it.getCell(4)?.rawValue,
                    itemName = it.getCell(5)?.rawValue,
                    supplyPrice = it.getCell(6)?.rawValue?.toLong(),
                    taxAmount = it.getCell(7)?.rawValue?.toLong(),
                    totalAmount = it.getCell(8)?.rawValue?.toLong(),
                    isDeduction = it.getCell(9)?.rawValue,
                    debtorAccount = it.getCell(10)?.rawValue,
                    creditAccount = it.getCell(11)?.rawValue,
                    separateSend = it.getCell(12)?.rawValue,
                    statementStatus = it.getCell(13)?.rawValue,
                    taskType = it.getCell(14)?.rawValue,
                    approvalNo = it.getCell(15)?.rawValue,
                    invoiceType = it.getCell(16)?.rawValue,
                    billingType = it.getCell(16)?.rawValue,
                    issueType = it.getCell(16)?.rawValue,
                    writer = requiredDto.writer,
                )

            elecInvoiceList.add(useInForPurchaseElecInvoiceEntity)
        }
        log.error { elecInvoiceList }
        saveElecInvoiceList(elecInvoiceList)
    }
}