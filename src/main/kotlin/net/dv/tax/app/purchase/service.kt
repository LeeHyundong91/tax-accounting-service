package net.dv.tax.app.purchase

import net.dv.tax.app.dto.purchase.PurchaseCreditCardDto
import net.dv.tax.app.dto.purchase.PurchaseCreditCardListDto
import net.dv.tax.app.dto.purchase.PurchaseQueryDto
import net.dv.tax.app.enums.purchase.getDeductionName
import net.dv.tax.app.enums.purchase.getRecommendDeductionName
import org.springframework.stereotype.Service


@Service
class PurchaseManagementService(
    private val creditCardRepository: PurchaseCreditCardRepository,
): PurchaseQueryCommand {
    override fun creditCard(hospitalId: String, query: PurchaseQueryDto): PurchaseCreditCardListDto {
        val creditCardList = getPurchaseCreditCardList(hospitalId, query, false)
        val totalCount = creditCardRepository.purchaseCreditCardListCnt(hospitalId, query)
        val purchaseCreditcardTotal =
            creditCardRepository.purchaseCreditCardTotal(hospitalId, query)

        return PurchaseCreditCardListDto(
            listPurchaseCreditCard = creditCardList,
            purchaseCreditCardTotal = purchaseCreditcardTotal,
            totalCount = totalCount
        )
    }

    private fun getPurchaseCreditCardList(
        hospitalId: String,
        purchaseQueryDto: PurchaseQueryDto,
        isExcel: Boolean,
    ): List<PurchaseCreditCardDto> {

        return creditCardRepository.purchaseCreditCardList(hospitalId, purchaseQueryDto, isExcel)
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
                    deductionName = getDeductionName(purchaseCreditCard.isDeduction),
                    recommendDeductionName = getRecommendDeductionName(purchaseCreditCard.isRecommendDeduction),
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