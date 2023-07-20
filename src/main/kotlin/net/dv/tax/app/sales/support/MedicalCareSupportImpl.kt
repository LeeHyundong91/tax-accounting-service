package net.dv.tax.app.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.MedicalCareEntity
import net.dv.tax.domain.sales.QMedicalCareEntity.medicalCareEntity
import net.dv.tax.app.dto.sales.MedicalCareDto
import net.dv.tax.app.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MedicalCareSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(MedicalCareEntity::class.java), MedicalCareSupport {
    private fun baseQuery(hospitalId: String, yearMonth: String): JPAQuery<MedicalCareDto> {
        return query
            .select(
                Projections.bean(
                    MedicalCareDto::class.java,
                    medicalCareEntity.treatmentYearMonth.`as`("treatmentYearMonth"),
                    medicalCareEntity.treatmentYearMonth.count().`as`("totalCount"),
                    medicalCareEntity.incomeTax.sum().`as`("incomeTax"),
                    medicalCareEntity.residentTax.sum().`as`("residentTax"),
                    medicalCareEntity.taxAmount.sum().`as`("taxAmount"),
                    medicalCareEntity.redemptionAmountA.sum().`as`("redemptionAmountA"),
                    medicalCareEntity.redemptionAmountB.sum().`as`("redemptionAmountB"),
                    medicalCareEntity.redemptionAmountC.sum().`as`("redemptionAmountC"),
                    medicalCareEntity.deductionAmount.sum().`as`("deductionAmount"),
                    medicalCareEntity.patientRefundAmount.sum().`as`("patientRefundAmount"),
                    medicalCareEntity.agencyPayment.sum().`as`("agencyPayment"),
                    medicalCareEntity.roundingAmount.sum().`as`("roundingAmount"),
                    medicalCareEntity.actualPaymentA.sum().`as`("actualPaymentA"),
                    medicalCareEntity.previousPayableAmountA.sum().`as`("previousPayableAmountA"),
                    medicalCareEntity.payoutAmount.sum().`as`("payoutAmount"),
                    medicalCareEntity.totalAmountMB.sum().`as`("totalAmountMB"),
                    medicalCareEntity.countMB.sum().`as`("countMB"),
                    medicalCareEntity.ownChargeMB.sum().`as`("ownChargeMB"),
                    medicalCareEntity.disabledExpenseMB.sum().`as`("disabledExpenseMB"),
                    medicalCareEntity.agencyDuesMB.sum().`as`("agencyDuesMB"),
                    medicalCareEntity.roundingAmountMB.sum().`as`("roundingAmountMB"),
                    medicalCareEntity.fundDuesAD.sum().`as`("fundDuesAD"),
                    medicalCareEntity.alternativePayoutAD.sum().`as`("alternativePayoutAD"),
                    medicalCareEntity.patientRefundAmountAD.sum().`as`("patientRefundAmountAD"),
                    medicalCareEntity.agencyPaymentAD.sum().`as`("agencyPaymentAD"),
                    medicalCareEntity.lackDepositB.sum().`as`("lackDepositB"),
                    medicalCareEntity.previousPayableAmountB.sum().`as`("previousPayableAmountB"),
                    medicalCareEntity.actualPaymentB.sum().`as`("actualPaymentB"),
                    medicalCareEntity.nationalMedicalPayment.sum().`as`("nationalMedicalPayment"),
                )
            )
            .from(medicalCareEntity)
            .where(
                medicalCareEntity.hospitalId.eq(hospitalId),
                medicalCareEntity.treatmentYearMonth.startsWith(yearMonth)
            )
    }

    override fun dataList(hospitalId: String, yearMonth: String): List<MedicalCareDto> {
        return baseQuery(hospitalId, yearMonth)
            .groupBy(medicalCareEntity.treatmentYearMonth)
            .fetch()
    }

    override fun dataListTotal(hospitalId: String, yearMonth: String): MedicalCareDto? {
        return baseQuery(hospitalId, yearMonth).fetchOne()
    }

    override fun monthlyAgencySumAmount(hospitalId: String, yearMonth: String): Long? {
        return query
            .select(
                medicalCareEntity.agencyDuesMB.sum()
            )
            .from(medicalCareEntity)
            .where(
                medicalCareEntity.hospitalId.eq(hospitalId),
                medicalCareEntity.treatmentYearMonth.startsWith(yearMonth)
            )
            .fetchOne()
    }

    override fun monthlyOwnChargeSumAmount(hospitalId: String, yearMonth: String): Long? {
        return query
            .select(
                medicalCareEntity.ownChargeMB.sum()
            )
            .from(medicalCareEntity)
            .where(
                medicalCareEntity.hospitalId.eq(hospitalId),
                medicalCareEntity.treatmentYearMonth.startsWith(yearMonth)
            )
            .fetchOne()
    }

}
