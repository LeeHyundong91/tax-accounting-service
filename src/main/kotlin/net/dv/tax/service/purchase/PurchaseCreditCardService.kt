package net.dv.tax.service.purchase

import mu.KotlinLogging
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.utils.ExcelComponent
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class PurchaseCreditCardService(private val excelComponent: ExcelComponent) {

    private val log = KotlinLogging.logger {}

    /**
     * TODO Only Use Single Upload Excel File
     * do not use xls
     * only accept xlsx
     */
    fun cellToEntity(rows: MutableList<Row>, cardEntity: PurchaseCreditCardEntity): List<PurchaseCreditCardEntity> {

        var creditCardList = mutableListOf<PurchaseCreditCardEntity>()

        /*Remove Title*/
        rows.removeFirst()

        /*Remove sum data*/
        rows.removeLast()
        rows.removeLast()
        rows.removeLast()


        rows.forEach {


            val with2022Temp = "2022-" + it.getCell(0).rawValue
            val billingDate = LocalDate.parse(with2022Temp, DateTimeFormatter.ISO_DATE)

            var isDeduction = false
            var isRecommendDeduction = false

            /**
             * True : 공제
             * False : 불공제
             */
            if (it.getCell(9).rawValue.length == 2) {
                isDeduction = true
            }

            /**
             * False : Empty
             * True : 불공제 추천
             */
            if (it.getCell(10).rawValue != null) {
                isRecommendDeduction = true
            }


            val useInForCreditCardEntity =
                PurchaseCreditCardEntity(
                    hospitalId = cardEntity.hospitalId,
                    billingDate = billingDate,
                    accountCode = it.getCell(1).rawValue,
                    franchiseeName = it.getCell(2).rawValue,
                    corporationType = it.getCell(3).rawValue,
                    itemName = it.getCell(4).rawValue,
                    supplyPrice = it.getCell(5).rawValue.toLong(),
                    taxAmount = it.getCell(6).rawValue.toLong(),
                    nonTaxAmount = it.getCell(7).rawValue.toLong(),
                    totalAmount = it.getCell(8).rawValue.toLong(),
                    isDeduction = isDeduction,
                    isRecommendDeduction = isRecommendDeduction,
                    statementType1 = it.getCell(11).rawValue,
                    statementType2 = it.getCell(12).rawValue,
                    debtorAccount = it.getCell(13).rawValue,
                    creditAccount = it.getCell(14).rawValue,
                    separateSend = it.getCell(15).rawValue,
                    statementStatus = it.getCell(16).rawValue,
                    writer = cardEntity.writer,
                    isDelete = true
                )

            creditCardList.add(useInForCreditCardEntity)
        }

        log.error { creditCardList }

        return creditCardList
    }


}