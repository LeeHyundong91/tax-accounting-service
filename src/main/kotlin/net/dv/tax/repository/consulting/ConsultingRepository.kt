package net.dv.tax.repository.consulting

import net.dv.tax.domain.consulting.*
import net.dv.tax.dto.consulting.SgaExpenseDto
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
    fun findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId: String, resultYearMonth: String): SalesPaymentMethodEntity?
}

interface AdjustmentCostRepository: JpaRepository<AdjustmentCostEntity, Long>{
    fun findTopByHospitalIdAndResultYearMonthStartingWith(hospitalId: String, resultYearMonth: String): AdjustmentCostEntity?
}

interface AdjustmentCostItemRepository: JpaRepository<AdjustmentCostItemEntity, Long>


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
    fun cashReceiptSgaExpense(hospitalId: String, yearMonth: String): List<SgaExpenseDto>

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
    fun creditCardSgaExpense(hospitalId: String, yearMonth: String): List<SgaExpenseDto>

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
    fun elecInvoiceSgaExpense(hospitalId: String, yearMonth: String): List<SgaExpenseDto>

    fun findTopByHospitalIdAndResultYearMonthStartingWith(
        hospitalId: String,
        resultYearMonth: String,
    ): PurchaseReportEntity?

}

interface PurchaseReportItemRepository : JpaRepository<PurchaseReportItemEntity, Long>


