package net.dv.tax.service.purchase

import mu.KotlinLogging
import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.dto.purchase.ExcelRequiredDto
import net.dv.tax.dto.purchase.PurchaseCreditCardDto
import net.dv.tax.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.dto.purchase.PurchaseQueryDto
import net.dv.tax.enum.purchase.getDeductionName
import net.dv.tax.enum.purchase.getRecommendDeductionName
import net.dv.tax.repository.purchase.PurchaseCreditCardRepository
import org.dhatim.fastexcel.reader.Row
import org.springframework.stereotype.Service


@Service
class PurchaseCreditCardService(
    private val purchaseCreditCardRepository: PurchaseCreditCardRepository,
) {

    private val log = KotlinLogging.logger {}


    /**
     * TODO Only Use Single Upload Excel File
     * do not use xls
     * only accept xlsx
     */

    fun excelToEntitySave(requiredDto: ExcelRequiredDto, rows: MutableList<Row>) {

        val dataList = mutableListOf<PurchaseCreditCardEntity>()

        /*Remove Title*/
        rows.removeFirst()

        /*Remove sum data 카드리스트는 하위 3개*/
        rows.removeLast()
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
                PurchaseCreditCardEntity(
                    hospitalId = requiredDto.hospitalId,
                    billingDate = billingDate,
                    dataFileId = requiredDto.fileDataId,
                    accountCode = it.getCell(1)?.rawValue,
                    franchiseeName = it.getCell(2)?.rawValue,
                    corporationType = it.getCell(3)?.rawValue,
                    itemName = it.getCell(4)?.rawValue,
                    supplyPrice = it.getCell(5)?.rawValue?.toDouble()?.toLong(),
                    taxAmount = it.getCell(6)?.rawValue?.toDouble()?.toLong(),
                    nonTaxAmount = it.getCell(7)?.rawValue?.toDouble()?.toLong(),
                    totalAmount = it.getCell(8)?.rawValue?.toDouble()?.toLong(),
                    isDeduction = isDeduction,
                    isRecommendDeduction = isRecommendDeduction,
                    statementType1 = it.getCell(11)?.rawValue,
                    statementType2 = it.getCell(12)?.rawValue,
                    debtorAccount = it.getCell(13)?.rawValue,
                    creditAccount = it.getCell(14)?.rawValue,
                    separateSend = it.getCell(15)?.rawValue,
                    statementStatus = it.getCell(16)?.rawValue,
                    writer = requiredDto.writer,
                    isDelete = false
                )
            dataList.add(data)
        }

        log.error { dataList }

        purchaseCreditCardRepository.saveAll(dataList)
    }
    fun getPurchaseCreditCard( hospitalId: String, purchaseQueryDto: PurchaseQueryDto): PurchaseCreditCardListDto{

        var creditCardList = getPurchaseCreditCardList(hospitalId, purchaseQueryDto, false)
        var totalCount = purchaseCreditCardRepository.purchaseCreditCardListCnt(hospitalId, purchaseQueryDto)
        var purchaseCreditcardTotal =
            purchaseCreditCardRepository.purchaseCreditCardTotal(hospitalId, purchaseQueryDto)

        return PurchaseCreditCardListDto(
            listPurchaseCreditCard = creditCardList,
            purchaseCreditCardTotal = purchaseCreditcardTotal,
            totalCount = totalCount
        )
    }


    fun getPurchaseCreditCardList( hospitalId: String, purchaseQueryDto: PurchaseQueryDto, isExcel: Boolean): List<PurchaseCreditCardDto>{

        return purchaseCreditCardRepository.purchaseCreditCardList(hospitalId, purchaseQueryDto, isExcel)
            .map { purchaseCreditCard ->
                PurchaseCreditCardDto(
                    id = purchaseCreditCard.id,
                    hospitalId = purchaseCreditCard.hospitalId,
                    dataFileId = purchaseCreditCard.dataFileId,
                    billingDate = purchaseCreditCard.billingDate,
                    accountCode = purchaseCreditCard.accountCode,
                    franchiseeName = purchaseCreditCard.franchiseeName,
                    corporationType = purchaseCreditCard.corporationType,
                    itemName = purchaseCreditCard.itemName,
                    supplyPrice = purchaseCreditCard.supplyPrice,
                    taxAmount = purchaseCreditCard.taxAmount,
                    nonTaxAmount = purchaseCreditCard.nonTaxAmount,
                    totalAmount = purchaseCreditCard.totalAmount,
                    deductionName = getDeductionName(purchaseCreditCard.isDeduction!!),
                    recommendDeductionName = getRecommendDeductionName(purchaseCreditCard.isRecommendDeduction!!),
                    statementType1 = purchaseCreditCard.statementType1,
                    statementType2 = purchaseCreditCard.statementType2,
                    debtorAccount = purchaseCreditCard.debtorAccount,
                    creditAccount = purchaseCreditCard.creditAccount,
                    separateSend = purchaseCreditCard.separateSend,
                    statementStatus = purchaseCreditCard.statementStatus,
                    writer = purchaseCreditCard.writer,
                    isDelete = purchaseCreditCard.isDelete,
                    createdAt = purchaseCreditCard.createdAt,
                )
            }
    }


}