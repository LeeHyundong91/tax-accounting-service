package net.dv.tax.service.consulting

import mu.KotlinLogging
import net.dv.tax.domain.consulting.TaxExemptionEntity
import net.dv.tax.domain.consulting.TaxExemptionItemEntity
import net.dv.tax.enums.consulting.TaxExemptionCategory
import net.dv.tax.enums.consulting.TaxExemptionItem
import net.dv.tax.repository.consulting.TaxExemptionRepository
import net.dv.tax.repository.sales.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaxExemptionService(
    private val salesCreditCardRepository: SalesCreditCardRepository,
    private val salesCashReceiptRepository: SalesCashReceiptRepository,
    private val medicalBenefitsRepository: MedicalBenefitsRepository,
    private val medicalCareRepository: MedicalCareRepository,
    private val carInsuranceRepository: CarInsuranceRepository,
    private val healthCareRepository: HealthCareRepository,
    private val vaccineRepository: SalesVaccineRepository,
    private val hospitalChartRepository: HospitalChartRepository,
    private val taxExemptionRepository: TaxExemptionRepository,
) {
    private val log = KotlinLogging.logger {}

    @Transactional
    fun getData(
        hospitalId: String,
        year: String,
    ): TaxExemptionEntity? {
        return taxExemptionRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
    }

    @Transactional
    fun updateRatioData(
        hospitalId: String,
        year: String,
        itemList: MutableList<TaxExemptionItemEntity>,
    ): TaxExemptionEntity? {

        var data = taxExemptionRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
        var totalAmount: Long = 0

        data.also {
            val innerItem = it?.detailList!!

            log.error { innerItem }

            innerItem.forEach { originData ->
                itemList.forEach { updateDate ->
                    if (originData.id!! == updateDate.id) {
                        originData.costOfSupplyRatio = updateDate.costOfSupplyRatio
                    }
                }
            }

            /*카드 소계*/
            val cardSmallSumValue =
                calculateSumValue(innerItem, TaxExemptionItem.SMALL_TOTAL.value, TaxExemptionCategory.CARD.code)
            /*현영 소계*/
            val cashReceiptSmallSumValue =
                calculateSumValue(innerItem, TaxExemptionItem.SMALL_TOTAL.value, TaxExemptionCategory.CASH_RECEIPT.code)
            /*현금 소계*/
            val cashSmallSumValue =
                calculateSumValue(innerItem, TaxExemptionItem.SMALL_TOTAL.value, TaxExemptionCategory.CASH.code)

            updateTaxUseRatioData(innerItem, cardSmallSumValue, TaxExemptionItem.TAX_CARD.value)
            updateTaxUseRatioData(innerItem, cashReceiptSmallSumValue, TaxExemptionItem.TAX_CASH_RECEIPT.value)
            updateTaxUseRatioData(innerItem, cashSmallSumValue, TaxExemptionItem.TAX_CASH.value)

            val taxCard = calculateSumValue(innerItem, TaxExemptionItem.TAX_CARD.value)
            val taxCashReceipt = calculateSumValue(innerItem, TaxExemptionItem.TAX_CASH_RECEIPT.value)
            val taxCash = calculateSumValue(innerItem, TaxExemptionItem.TAX_CASH.value)

            updateTaxFreeUseValueData(innerItem, cardSmallSumValue, taxCard, TaxExemptionItem.TAX_FREE_CARD.value)

            updateTaxFreeUseValueData(
                innerItem, cashReceiptSmallSumValue, taxCashReceipt, TaxExemptionItem.TAX_FREE_CASH_RECEIPT.value
            )

            updateTaxFreeUseValueData(innerItem, cashSmallSumValue, taxCash, TaxExemptionItem.TAX_FREE_CASH.value)

            updateRatioValues(innerItem, cardSmallSumValue, TaxExemptionItem.TAX_CARD.value)
            updateRatioValues(innerItem, cashReceiptSmallSumValue, TaxExemptionItem.TAX_CASH_RECEIPT.value)
            updateRatioValues(innerItem, cashSmallSumValue, TaxExemptionItem.TAX_CASH.value)
            updateRatioValues(innerItem, cashSmallSumValue, TaxExemptionItem.TAX_FREE_COMPLEX.value)

            totalAmount = cardSmallSumValue.plus(cashReceiptSmallSumValue).plus(cashSmallSumValue)

            var exemptionAmount: Long = getExemptionAmount(innerItem)

            it.exemptionAmount = exemptionAmount
            it.taxAmount = totalAmount.minus(exemptionAmount)
            it.totalAmountRatio = 100.0.toFloat()

            val exemptionAmountRatio: Float = exemptionAmount.toFloat().div(totalAmount) * 100
            log.error { "exemptionAmountRatio$exemptionAmountRatio" }
            it.exemptionAmountRatio = exemptionAmountRatio
            it.taxAmountRatio = 100.0.toFloat() - exemptionAmountRatio

            log.error { "show me the money $it" }

        }


        return data
    }


    @Transactional
    fun updateCashData(
        hospitalId: String,
        year: String,
        itemList: MutableList<TaxExemptionItemEntity>,
    ): TaxExemptionEntity? {

        var data = taxExemptionRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)

        data.also {
            val innerItem = it?.detailList!!

            log.error { innerItem }

            innerItem.forEach { originData ->
                itemList.forEach { updateDate ->
                    if (originData.id!! == updateDate.id) {
                        originData.costOfSupply = updateDate.costOfSupply
                        originData.supplyValue = updateDate.costOfSupply!!.div(1.1).toLong()
                    }
                }
            }

            /*카드 소계*/
            val cardSmallSumValue =
                calculateSumValue(innerItem, TaxExemptionItem.SMALL_TOTAL.value, TaxExemptionCategory.CARD.code)
            /*과세 카드*/
            val taxCard = calculateSumValue(innerItem, TaxExemptionItem.TAX_CARD.value)
            /*면세 카드*/
            updateTaxFreeUseValueData(innerItem, cardSmallSumValue, taxCard, TaxExemptionItem.TAX_FREE_CARD.value)

            /*현영 소계*/
            val cashReceiptSmallSumValue =
                calculateSumValue(innerItem, TaxExemptionItem.SMALL_TOTAL.value, TaxExemptionCategory.CASH_RECEIPT.code)
            /*과세 현영*/
            val taxCashReceipt = calculateSumValue(innerItem, TaxExemptionItem.TAX_CASH_RECEIPT.value)
            /*면세 현영*/
            updateTaxFreeUseValueData(
                innerItem, cashReceiptSmallSumValue, taxCashReceipt, TaxExemptionItem.TAX_FREE_CASH_RECEIPT.value
            )

            /*현금 소계*/
            val cashSmallSumValue =
                calculateSumValue(innerItem, TaxExemptionItem.SMALL_TOTAL.value, TaxExemptionCategory.CASH.code)
            /*과세 현금*/
            val taxCash = calculateSumValue(innerItem, TaxExemptionItem.TAX_CASH.value)
            /*면세 현금*/
            updateTaxFreeUseValueData(innerItem, cashSmallSumValue, taxCash, TaxExemptionItem.TAX_FREE_CASH.value)


            updateRatioValues(innerItem, cardSmallSumValue, TaxExemptionItem.TAX_CARD.value)
            updateRatioValues(innerItem, cashReceiptSmallSumValue, TaxExemptionItem.TAX_CASH_RECEIPT.value)
            updateRatioValues(innerItem, cashSmallSumValue, TaxExemptionItem.TAX_CASH.value)
            updateRatioValues(innerItem, cashSmallSumValue, TaxExemptionItem.TAX_FREE_COMPLEX.value)

            log.error { "cardSmallSumValue: $cardSmallSumValue" }
            log.error { "cashReceiptSmallSumValue: $cashReceiptSmallSumValue" }
            log.error { "cashSmallSumValue: $cashSmallSumValue" }

            val exemptionAmount: Long = getExemptionAmount(innerItem)

            val totalAmount = cardSmallSumValue.plus(cashReceiptSmallSumValue).plus(cashSmallSumValue)
            log.error { totalAmount }
            log.error { "exemptionAmount $exemptionAmount" }
            it.exemptionAmount = exemptionAmount
            it.taxAmount = totalAmount.minus(exemptionAmount)
            it.totalAmountRatio = 100.0.toFloat()

            val exemptionAmountRatio: Float = exemptionAmount.toFloat().div(totalAmount) * 100
            it.exemptionAmountRatio = exemptionAmountRatio
            it.taxAmountRatio = 100.0.toFloat() - exemptionAmountRatio

            taxExemptionRepository.save(it)

        }

        return data

    }


    fun makeData(hospitalId: String, year: String): TaxExemptionEntity? {

        val defaultCondition = TaxExemptionEntity()
        defaultCondition.hospitalId = hospitalId
        defaultCondition.resultYearMonth = year

        val data =
            taxExemptionRepository.findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId, year)
                ?: taxExemptionRepository.save(defaultCondition)

        data.also {

            val itemList = mutableListOf<TaxExemptionItemEntity>()
            /*카드 소계*/
            val creditCard = salesCreditCardRepository.monthlySumAmount(hospitalId, year) ?: 0
            itemList.add(
                setItem(TaxExemptionCategory.CARD.code, TaxExemptionItem.SMALL_TOTAL.value, creditCard, true)
            )
            itemList.add(
                setItem(TaxExemptionCategory.CARD.code, TaxExemptionItem.TAX_CARD.value, 0, true)
            )
            itemList.add(
                setItem(TaxExemptionCategory.CARD.code, TaxExemptionItem.TAX_FREE_CARD.value, 0, false)
            )

            /*현금영수증 소계*/
            val cashReceipt = salesCashReceiptRepository.monthlySumAmount(hospitalId, year) ?: 0
            itemList.add(
                setItem(TaxExemptionCategory.CASH_RECEIPT.code, TaxExemptionItem.SMALL_TOTAL.value, cashReceipt, true)
            )
            itemList.add(
                setItem(
                    TaxExemptionCategory.CASH_RECEIPT.code,
                    TaxExemptionItem.TAX_CASH_RECEIPT.value,
                    0,
                    true
                )
            )
            itemList.add(
                setItem(
                    TaxExemptionCategory.CASH_RECEIPT.code,
                    TaxExemptionItem.TAX_FREE_CASH_RECEIPT.value,
                    0,
                    false
                )
            )


            /*현금 - 면세공단*/
            val taxFreeCorpAmount = taxFreeCorpAmount(hospitalId, year)

            /*현금 (순현금) - 면세현금*/
            val actualCash = hospitalChartRepository.findByActualCash(hospitalId, "$year%")


            val cashSmallAmount = actualCash.plus(taxFreeCorpAmount)
            itemList.add(
                setItem(TaxExemptionCategory.CASH.code, TaxExemptionItem.SMALL_TOTAL.value, cashSmallAmount, false)
            )
            itemList.add(
                setItem(TaxExemptionCategory.CASH.code, TaxExemptionItem.TAX_CASH.value, 0, false)
            )
            itemList.add(
                setItem(
                    TaxExemptionCategory.CASH.code,
                    TaxExemptionItem.TAX_FREE_CASH.value,
                    actualCash,
                    false
                )
            )
            itemList.add(
                setItem(
                    TaxExemptionCategory.CASH.code,
                    TaxExemptionItem.TAX_FREE_COMPLEX.value,
                    taxFreeCorpAmount, false
                )
            )


            /*공급대가 합계*/
            val costOfSupplyAmount =
                calculateItemAmount(itemList, TaxExemptionItem.SMALL_TOTAL.value) { item -> item.costOfSupply }

            /*공급가액 합계*/
            val supplyValueAmount =
                calculateItemAmount(itemList, TaxExemptionItem.SMALL_TOTAL.value) { item -> item.supplyValue }

            var taxAmount: Long = 0


            itemList.forEach { item ->
                if (item.itemName == TaxExemptionItem.SMALL_TOTAL.value) {
                    item.costOfSupplyRatio = item.costOfSupply!!.toFloat().div(costOfSupplyAmount) * 100
                    item.supplyValueRatio = item.supplyValue!!.toFloat().div(supplyValueAmount) * 100
                }
            }

            val exemptionAmount: Long = getExemptionAmount(itemList)

            it.totalAmount = supplyValueAmount
            it.exemptionAmount = exemptionAmount
            it.taxAmount = supplyValueAmount.minus(exemptionAmount)
            it.totalAmountRatio = 100.0.toFloat()

            log.error { supplyValueAmount }

            val exemptionAmountRatio: Float = exemptionAmount.toFloat().div(supplyValueAmount) * 100
            it.exemptionAmountRatio = exemptionAmountRatio
            it.taxAmountRatio = 100.0.toFloat() - exemptionAmountRatio


            taxExemptionRepository.save(it)

        }

        return data

    }

    private fun getExemptionAmount(itemList: List<TaxExemptionItemEntity>): Long {
        var exemptionAmount: Long = 0

        itemList.forEach { item ->
            when (item.itemName) {
                TaxExemptionItem.TAX_FREE_CASH.value -> {
                    exemptionAmount += item.supplyValue!!
                    log.error { exemptionAmount }
                }

                TaxExemptionItem.TAX_FREE_CARD.value -> {
                    exemptionAmount += item.supplyValue!!
                }

                TaxExemptionItem.TAX_FREE_CASH_RECEIPT.value -> {
                    exemptionAmount += item.supplyValue!!
                }

                TaxExemptionItem.TAX_FREE_COMPLEX.value -> {
                    exemptionAmount += item.supplyValue!!
                }
            }
        }
        return exemptionAmount
    }


    fun taxFreeCorpAmount(hospitalId: String, year: String): Long {
        return medicalBenefitsRepository.monthlyCorpSumAmount(hospitalId, year) ?: 0
            .plus(medicalCareRepository.monthlyAgencySumAmount(hospitalId, year) ?: 0)
            .plus(carInsuranceRepository.monthlySumAmount(hospitalId, year) ?: 0)
            .plus(healthCareRepository.monthlySumAmount(hospitalId, year) ?: 0)
            .plus(vaccineRepository.monthlySumAmount(hospitalId, year) ?: 0)
    }

    private fun setItem(
        category: String,
        itemName: String,
        costOfSupply: Long,
        isTax: Boolean,
    ): TaxExemptionItemEntity {
        var supplyValue = costOfSupply
        if (isTax) {
            supplyValue = costOfSupply.div(1.1).toLong()
        }
        return TaxExemptionItemEntity(
            itemName = itemName,
            category = category,
            costOfSupply = costOfSupply,
            supplyValue = supplyValue
        )
    }

    fun calculateItemAmount(
        itemList: MutableList<TaxExemptionItemEntity>,
        itemName: String,
        amountFieldSelector: (TaxExemptionItemEntity) -> Long?,
    ): Long {
        return itemList.sumOf { item ->
            if (item.itemName == itemName) {
                amountFieldSelector(item) ?: 0
            } else {
                0
            }
        }
    }


    fun updateRatioValues(
        innerItem: MutableList<TaxExemptionItemEntity>,
        sumValue: Long,
        itemName: String,
    ) {
        innerItem.filter { item -> item.itemName == itemName }.forEach { item ->
            item.costOfSupplyRatio = ratioReturn(item.costOfSupply!!, sumValue)
            item.supplyValueRatio = ratioReturn(item.supplyValue!!, sumValue.div(1.1).toLong())
        }
    }

    fun updateTaxUseRatioData(
        innerItem: MutableList<TaxExemptionItemEntity>,
        sumValue: Long,
        itemName: String,
    ) {
        innerItem.filter { item -> item.itemName == itemName }.forEach { item ->
            val supplyCost = sumValue.times(item.costOfSupplyRatio!! / 100).toLong()
            val supplyValue = supplyCost.div(1.1).toLong()
            item.costOfSupply = supplyCost
            item.supplyValue = supplyValue
            item.supplyValueRatio = ratioReturn(supplyValue, sumValue)
        }
    }

    fun updateTaxFreeUseValueData(
        innerItem: MutableList<TaxExemptionItemEntity>,
        sumValue: Long,
        taxValue: Long,
        itemName: String,
    ) {
        innerItem.filter { item -> item.itemName == itemName }.forEach { item ->
            val supplyCost = sumValue - taxValue
            val supplyValue = supplyCost.div(1.1).toLong()
            item.costOfSupply = supplyCost
            item.supplyValue = supplyValue
            item.costOfSupplyRatio = ratioReturn(supplyCost, sumValue)
            item.supplyValueRatio = ratioReturn(supplyValue, sumValue)
        }
    }

    fun ratioReturn(targetAmount: Long, sumAmount: Long): Float {
        log.error { "targetAmount $targetAmount" }
        log.error { "sumAmount $sumAmount" }
        return targetAmount.toFloat().div(sumAmount) * 100
    }


    fun calculateSumValue(
        innerItem: MutableList<TaxExemptionItemEntity>,
        itemName: String,
        category: String? = null,
    ): Long {
        return innerItem.find { item ->
            item.itemName == itemName && (category == null || item.category == category)
        }?.costOfSupply ?: 0
    }
}