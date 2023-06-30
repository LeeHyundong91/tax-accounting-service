package net.dv.tax.repository.consulting

import net.dv.tax.domain.consulting.*
import net.dv.tax.dto.consulting.DirectorAndStake
import net.dv.tax.dto.consulting.PersonalSum
import net.dv.tax.dto.consulting.SgaExpense
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SalesTypeRepository : JpaRepository<SalesTypeEntity, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId: String, resultYearMonth: String): SalesTypeEntity?
}

interface InsuranceClaimRepository : JpaRepository<InsuranceClaimEntity?, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(
        hospitalId: String,
        resultYearMonth: String,
    ): InsuranceClaimEntity?
}

interface TaxExemptionRepository : JpaRepository<TaxExemptionEntity?, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(
        hospitalId: String,
        resultYearMonth: String,
    ): TaxExemptionEntity?
}

interface SalesPaymentMethodRepository : JpaRepository<SalesPaymentMethodEntity, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(
        hospitalId: String,
        resultYearMonth: String,
    ): SalesPaymentMethodEntity?
}

interface AdjustmentCostRepository : JpaRepository<AdjustmentCostEntity, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(
        hospitalId: String,
        resultYearMonth: String,
    ): AdjustmentCostEntity?
}

interface AdjustmentCostItemRepository : JpaRepository<AdjustmentCostItemEntity, Long>


interface PurchaseReportRepository : JpaRepository<PurchaseReportEntity, Long> {


    @Query(
        nativeQuery = true, value = "" +
                "select sum(TOTAL_AMOUNT) as valueAmount, debtor_account as itemName\n" +
                "from purchase_cash_receipt\n" +
                "where HOSPITAL_ID = ?1\n" +
                "  and BILLING_DATE like ?2% \n" +
                "  and DEBTOR_ACCOUNT like '(판)%'\n" +
                "  and STATEMENT_STATUS = '전송완료'\n" +
                "group by debtor_account"
    )
    fun cashReceiptSgaExpense(hospitalId: String, yearMonth: String): List<SgaExpense>

    @Query(
        nativeQuery = true, value = "" +
                "select sum(TOTAL_AMOUNT) as valueAmount, debtor_account as itemName\n" +
                "from purchase_credit_card\n" +
                "where HOSPITAL_ID = ?1\n" +
                "  and BILLING_DATE like ?2% \n" +
                "  and DEBTOR_ACCOUNT like '(판)%'\n" +
                "  and STATEMENT_STATUS = '전송완료'\n" +
                "group by debtor_account"
    )
    fun creditCardSgaExpense(hospitalId: String, yearMonth: String): List<SgaExpense>

    @Query(
        nativeQuery = true, value = "" +
                "select sum(TOTAL_AMOUNT) as valueAmount, debtor_account as itemName\n" +
                "from purchase_elec_invoice\n" +
                "where HOSPITAL_ID = ?1\n" +
                "  and ISSUE_DATE like ?2% \n" +
                "  and DEBTOR_ACCOUNT like '(판)%'\n" +
                "  and STATEMENT_STATUS = '전송완료'\n" +
                "group by debtor_account"
    )
    fun elecInvoiceSgaExpense(hospitalId: String, yearMonth: String): List<SgaExpense>

    fun findTopByHospitalIdAndResultYearMonthStartingWith(
        hospitalId: String,
        resultYearMonth: String,
    ): PurchaseReportEntity?

}

interface PurchaseReportItemRepository : JpaRepository<PurchaseReportItemEntity, Long>

interface IncomeStatementRepository : JpaRepository<IncomeStatementEntity, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId: String, yearMonth: String): IncomeStatementEntity?
}

interface IncomeStatementItemRepository : JpaRepository<IncomeStatementItemEntity, Long>


interface ExpectedIncomeRepository : JpaRepository<ExpectedIncomeEntity, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(
        hospitalId: String,
        resultYearMonth: String,
    ): ExpectedIncomeEntity?
}

interface ExpectedIncomeItemRepository : JpaRepository<ExpectedIncomeItemEntity, Long>
interface ExpectedIncomeMonthlyItemRepository : JpaRepository<ExpectedIncomeMonthlyItemEntity, Long>

interface TaxCreditRepository : JpaRepository<TaxCreditEntity, Long> {
    fun findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId: String, yearMonth: String): TaxCreditEntity?
}

interface TaxCreditItemRepository: JpaRepository<TaxCreditItemEntity, Long>

interface TaxCreditPersonalRepository : JpaRepository<TaxCreditPersonalEntity, Long> {

    @Query(
        nativeQuery = true, value = "" +
                "select ACCOUNT_NAME as director" +
                ", STAKE as stake\n" +
                ", ACCOUNT_NAME as directorId\n" +
                "from hospital_director\n" +
                "where COMPANY_ID = ?1 \n" +
                "  and STATUS = 'ACTIVE'\n" +
                "  and STAKE is not null"
    )
    fun directorAndStakeList(hospitalId: String): List<DirectorAndStake>

    fun findAllByHospitalIdAndDirectorAndDirectorId(hospitalId: String, director:String, directorId: String): TaxCreditPersonalEntity?

    fun findAllByHospitalIdAndResultYearMonthStartingWith(
        hospitalId: String,
        yearMonth: String,
    ): List<TaxCreditPersonalEntity>

    @Query(nativeQuery = true, value = "" +
            "select sum(LAST_YEAR_AMOUNT)       as lastYearAmount,\n" +
            "       sum(CURRENT_ACCRUALS)       as currentAccruals,\n" +
            "       sum(VANISHING_AMOUNT)       as vanishingAmount,\n" +
            "       sum(CURRENT_DEDUCTION)      as currentDeduction,\n" +
            "       sum(AMOUNT_CARRIED_FORWARD) as amountCarriedForward\n" +
            "from tax_credit_personal_item\n" +
            "  where TAX_CREDIT_PERSONAL_ID = ?1")
    fun personalTotalAmount(taxCreditPersonalId: Long): PersonalSum


}

interface TaxCreditPersonalItemRepository:JpaRepository<TaxCreditPersonalItemEntity, Long>

//interface EstimatedTaxRepository:JpaRepository<EstimatedTaxEntity, Long>
