package net.dv.tax.service.purchase

import mu.KotlinLogging
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.utils.ExcelComponent
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Service

@Service
class PurchaseCreditCardService(private val excelComponent: ExcelComponent) {

    private val log = KotlinLogging.logger {}


    fun rowDataMapper(rows: MutableList<Row>): List<PurchaseCreditCardEntity> {

        var creditCardList = mutableListOf<PurchaseCreditCardEntity>()

        /*Remove Title*/
        rows.removeFirst()

        /*Remove sum data*/
        rows.removeLast()
        rows.removeLast()
        rows.removeLast()

        rows.forEach {


            val formatDate = "2022-"+it.getCell(0).rawValue

            val purchaseCreditCardEntity =
                PurchaseCreditCardEntity(
                    id = null,
                    hospitalId = null,
                    billingDate = formatDate,
                    accountCode = it.getCell(1).rawValue,
                    franchiseeName = it.getCell(2).rawValue,
                    corporationType = it.getCell(3).rawValue,
                    itemName = it.getCell(4).rawValue,
                    supplyPrice = it.getCell(5).rawValue,
                    taxAmount = it.getCell(6).rawValue,
                    nonTaxAmount = it.getCell(7).rawValue,
                    totalAmount = it.getCell(8).rawValue,
                    isDeduction = it.getCell(9).rawValue,
                    isNonDeduction = it.getCell(10).rawValue,
                    statementType1 = it.getCell(11).rawValue,
                    statementType2 = it.getCell(12).rawValue,
                    debtorAccount = it.getCell(13).rawValue,
                    creditAccount = it.getCell(14).rawValue,
                    separateSend = it.getCell(15).rawValue,
                    statementStatus = it.getCell(16).rawValue,
                )
            creditCardList.add(purchaseCreditCardEntity)
        }

        log.error { creditCardList }

        return creditCardList
    }

}