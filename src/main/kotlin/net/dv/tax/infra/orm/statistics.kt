package net.dv.tax.infra.orm

import com.querydsl.core.Tuple
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.app.statistics.StatisticsRepository
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
import org.springframework.stereotype.Component


@Component
class StatisticsRepositoryImpl(private val factory: JPAQueryFactory): StatisticsRepository {
    override fun monthlySalesStatistics(hospitalId: String, month: String): SalesAggregation {
        return Dto(
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

        return Dto(
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

    data class Dto(
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