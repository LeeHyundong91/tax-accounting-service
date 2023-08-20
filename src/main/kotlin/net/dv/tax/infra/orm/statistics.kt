package net.dv.tax.infra.orm

import com.querydsl.core.Tuple
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.AccountingItemCategory
import net.dv.tax.app.statistics.StatisticsRepository
import net.dv.tax.app.statistics.types.PurchaseAggregation
import net.dv.tax.app.statistics.types.SalesAggregation
import net.dv.tax.domain.sales.QCarInsuranceEntity.carInsuranceEntity
import net.dv.tax.domain.sales.QEmployeeIndustryEntity.employeeIndustryEntity
import net.dv.tax.domain.sales.QHealthCareEntity.healthCareEntity
import net.dv.tax.domain.sales.QHospitalChartEntity.hospitalChartEntity
import net.dv.tax.domain.sales.QMedicalBenefitsEntity.medicalBenefitsEntity
import net.dv.tax.domain.sales.QMedicalCareEntity.medicalCareEntity
import net.dv.tax.domain.sales.QSalesAgentEntity.salesAgentEntity
import net.dv.tax.domain.sales.QSalesCashReceiptEntity.salesCashReceiptEntity
import net.dv.tax.domain.sales.QSalesCreditCardEntity.salesCreditCardEntity
import net.dv.tax.domain.sales.QSalesOtherBenefitsEntity.salesOtherBenefitsEntity
import net.dv.tax.domain.sales.QSalesVaccineEntity.salesVaccineEntity
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class StatisticsRepositoryImpl(
    private val factory: JPAQueryFactory,
    private val jdbcTemplate: NamedParameterJdbcTemplate): StatisticsRepository {
    override fun monthlySalesStatistics(hospitalId: String, month: String): SalesAggregation {
        return SalesDto(
            hospitalId,
            month
        )
    }

    override fun annualSalesStatistics(hospitalId: String, year: String): SalesAggregation {
        val chart: Tuple? = factory
            .select(
                hospitalChartEntity.ownExpense.sum(),
                hospitalChartEntity.nonPayment.sum(),
            )
            .from(hospitalChartEntity)
            .where(
                hospitalChartEntity.hospitalId.eq(hospitalId),
                hospitalChartEntity.treatmentYearMonth.startsWith(year)
            )
            .fetchOne()

        val creditCard = factory
            .select(salesCreditCardEntity.totalSales.sum())
            .from(salesCreditCardEntity)
            .where(
                salesCreditCardEntity.hospitalId.eq(hospitalId),
                salesCreditCardEntity.approvalYearMonth.startsWith(year)
            )
            .fetchOne()

        val cashReceipt = factory
            .select(salesCashReceiptEntity.totalAmount.sum())
            .from(salesCashReceiptEntity)
            .where(
                salesCashReceiptEntity.hospitalId.eq(hospitalId),
                salesCashReceiptEntity.salesDate.startsWith(year)
            )
            .fetchOne()

        val salesAgent = factory
            .select(salesAgentEntity.totalSales.sum())
            .from(salesAgentEntity)
            .where(
                salesAgentEntity.hospitalId.eq(hospitalId),
                salesAgentEntity.approvalYearMonth.startsWith(year)
            )
            .fetchOne()

        val medicalCare = factory
            .select(medicalCareEntity.agencyDuesMB.sum())
            .from(medicalCareEntity)
            .where(
                medicalCareEntity.hospitalId.eq(hospitalId),
                medicalCareEntity.treatmentYearMonth.startsWith(year)
            )
            .fetchOne()

        val medicalBenefit = factory
            .select(medicalBenefitsEntity.corpPaymentDecision.sum())
            .from(medicalBenefitsEntity)
            .where(
                medicalBenefitsEntity.hospitalId.eq(hospitalId),
                medicalBenefitsEntity.treatmentYearMonth.startsWith(year),
            )
            .fetchOne()

        val carInsurance = factory
            .select(carInsuranceEntity.decisionTotalAmount.sum())
            .from(carInsuranceEntity)
            .where(
                carInsuranceEntity.hospitalId.eq(hospitalId),
                carInsuranceEntity.treatmentYearMonth.startsWith(year)
            )
            .fetchOne()

        val vaccine = factory
            .select(salesVaccineEntity.payAmount.sum())
            .from(salesVaccineEntity)
            .where(
                salesVaccineEntity.hospitalId.eq(hospitalId),
                salesVaccineEntity.paymentYearMonth.startsWith(year)
            )
            .fetchOne()

        val industry = factory
            .select(employeeIndustryEntity.actualPayment.sum())
            .from(employeeIndustryEntity)
            .where(
                employeeIndustryEntity.hospitalId.eq(hospitalId),
                employeeIndustryEntity.treatmentYearMonth.startsWith(year)
            )
            .fetchOne()

        val healthCare = factory
            .select(healthCareEntity.paidAmount.sum())
            .from(healthCareEntity)
            .where(
                healthCareEntity.hospitalId.eq(hospitalId),
                healthCareEntity.paidDate.startsWith(year)
            )
            .fetchOne()

        val others = factory
            .select(salesOtherBenefitsEntity.totalAmount.sum())
            .from(salesOtherBenefitsEntity)
            .where(
                salesOtherBenefitsEntity.hospitalId.eq(hospitalId),
                salesOtherBenefitsEntity.dataPeriod.startsWith(year)
            )
            .fetchOne()

        val own = chart?.get(0, Long::class.java) ?: 0
        val none = chart?.get(1, Long::class.java) ?: 0

        return SalesDto(
            hospitalId, year,
            creditCard = creditCard,
            cashReceipt = cashReceipt,
            salesAgent = salesAgent,
            netCash = (own + none) - ((creditCard ?: 0) + (cashReceipt ?: 0)),
            medicalCareBenefits = medicalCare,
            medicalBenefits = medicalBenefit,
            carInsurance = carInsurance,
            industry = industry,
            vaccine = vaccine,
            healthCare = healthCare,
            others = others,
        )
    }

    override fun purchaseStatistics(hospitalId: String, period: String): List<PurchaseAggregation> {
        val params = MapSqlParameterSource().apply {
            addValue("hospitalId", hospitalId)
            addValue("period", period)
        }
        val query =
            """
            SELECT U.HOSPITAL_ID
                  ,U.DEBTOR_ACCOUNT
                  ,SUM(AMOUNT) AS AMOUNT
            FROM (
                ${QS.CREDIT_CARD_BOOKS}    
                union all
                ${QS.CASH_RECEIPT_BOOKS}    
                union all
                ${QS.E_INVOICE_BOOKS}    
                union all
                ${QS.HANDWRITTEN_BOOKS} 
                union all
                ${QS.HANDWRITTEN_BOOKS} ) AS U
            GROUP BY U.HOSPITAL_ID, U.DEBTOR_ACCOUNT
            """.trimIndent()

        return jdbcTemplate.query(QS.CASH_RECEIPT_BOOKS, params) { rs, _ ->
            object: PurchaseAggregation {
                override val hospitalId: String = rs.getString("HOSPITAL_ID")
                override val period: String = period
                override val debitAccount: String = rs.getString("DEBTOR_ACCOUNT")
                override val category: String = AccountingItemCategory.category(debitAccount).name
                override val amount: Long = rs.getLong("AMOUNT")
            }
        }
    }

    data class SalesDto(
        override val hospitalId: String,
        override val period: String,
        override val creditCard: Long? = null,
        override val cashReceipt: Long? = null,
        override val salesAgent: Long? = null,
        override val netCash: Long? = null,

        override val medicalCareBenefits: Long? = null,
        override val medicalBenefits: Long? = null,
        override val industry: Long? = null,
        override val vaccine: Long? = null,
        override val carInsurance: Long? = null,
        override val healthCare: Long? = null,
        override val others: Long? = null,
    ): SalesAggregation
}

private object QS {
    const val CREDIT_CARD_BOOKS = """
       SELECT C.HOSPITAL_ID
             ,C.DEBTOR_ACCOUNT
             ,C.TOTAL_AMOUNT AS AMOUNT
       FROM PURCHASE_CREDIT_CARD C  
       WHERE C.HOSPITAL_ID = :hospitalId
         AND C.BILLING_DATE LIKE CONCAT(:period, '%')
    """

    const val CASH_RECEIPT_BOOKS = """
       SELECT R.HOSPITAL_ID
             ,R.DEBTOR_ACCOUNT
             ,R.TOTAL_AMOUNT AS AMOUNT
       FROM PURCHASE_CASH_RECEIPT R  
       WHERE R.HOSPITAL_ID = :hospitalId
         AND R.BILLING_DATE LIKE CONCAT(:period, '%')
    """

    const val E_INVOICE_BOOKS = """
       SELECT I.HOSPITAL_ID
             ,I.DEBTOR_ACCOUNT
             ,I.TOTAL_AMOUNT AS AMOUNT
       FROM PURCHASE_ELEC_INVOICE I
       WHERE I.HOSPITAL_ID = :hospitalId
         AND I.SEND_DATE LIKE CONCAT(:period, '%')
    """

    const val HANDWRITTEN_BOOKS = """
       SELECT H.HOSPITAL_ID
             ,H.DEBTOR_ACCOUNT
             ,H.TOTAL_AMOUNT AS AMOUNT
       FROM PURCHASE_HANDWRITTEN H
       WHERE H.HOSPITAL_ID = :hospitalId
         AND H.SEND_DATE LIKE CONCAT(:period, '%')
    """

    const val SALARY_BOOKS = """
       SELECT S.HOSPITAL_ID 
             ,'인건비' AS DEBTOR_ACCOUNT
             ,S.ACTUAL_PAYMENT AS AMOUNT
       FROM EMPLOYEE_SALARY S
       WHERE S.HOSPITAL_ID = :hospitalId
         AND S.PAYMENTS_AT LIKE CONCAT(:period, '%')
    """
}