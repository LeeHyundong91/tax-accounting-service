package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.MedicalBenefitsEntity
import net.dv.tax.domain.sales.QMedicalBenefitsEntity.medicalBenefitsEntity
import net.dv.tax.dto.sales.MedicalBenefitsDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MedicalBenefitsSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(MedicalBenefitsEntity::class.java), MedicalBenefitsSupport {
    private fun baseQuery(hospitalId: String, yearMonth: String): JPAQuery<MedicalBenefitsDto> {
        return query.select(
            Projections.bean(
                MedicalBenefitsDto::class.java,
                medicalBenefitsEntity.treatmentYearMonth.count().`as`("totalCount"),
                medicalBenefitsEntity.treatmentYearMonth,
                medicalBenefitsEntity.decisionADTotalAmount.sum().`as`("decisionADTotalAmount"),
                medicalBenefitsEntity.corpPaymentDecision.sum().`as`("corpPaymentDecision"),
                medicalBenefitsEntity.withholdingTaxAmount.sum().`as`("withholdingTaxAmount"),
                medicalBenefitsEntity.incomeTaxAmount.sum().`as`("incomeTaxAmount"),
                medicalBenefitsEntity.residentTaxAmount.sum().`as`("residentTaxAmount"),
                medicalBenefitsEntity.totalWithholdingTaxAmount.sum().`as`("totalWithholdingTaxAmount"),
                medicalBenefitsEntity.refundAmountA.sum().`as`("refundAmountA"),
                medicalBenefitsEntity.refundAmountB.sum().`as`("refundAmountB"),
                medicalBenefitsEntity.refundAmountC.sum().`as`("refundAmountC"),
                medicalBenefitsEntity.examPaymentFee.sum().`as`("examPaymentFee"),
                medicalBenefitsEntity.deductionProcessing.sum().`as`("deductionProcessing"),
                medicalBenefitsEntity.replacementPayment.sum().`as`("replacementPayment"),
                medicalBenefitsEntity.divideAmount.sum().`as`("divideAmount"),
                medicalBenefitsEntity.roundingAmount.sum().`as`("roundingAmount"),
                medicalBenefitsEntity.variationAmount.sum().`as`("variationAmount"),
                medicalBenefitsEntity.actualPaymentAmount.sum().`as`("actualPaymentAmount"),
            )
        )
            .from(medicalBenefitsEntity)
            .where(
                medicalBenefitsEntity.hospitalId.eq(hospitalId),
                medicalBenefitsEntity.treatmentYearMonth.startsWith(yearMonth)
            )
    }

    override fun dataList(hospitalId: String, yearMonth: String): List<MedicalBenefitsDto> {
        /*`as`*/
        return baseQuery(hospitalId, yearMonth)
            .groupBy(medicalBenefitsEntity.treatmentYearMonth)
            .fetch()
    }

    override fun dataListTotal(hospitalId: String, yearMonth: String): MedicalBenefitsDto? {
        return baseQuery(hospitalId, yearMonth)
            .fetchOne()
    }

    override fun monthlySumAmount(hospitalId: String, yearMonth: String): Long? {
        return query.select(
            medicalBenefitsEntity.corpPaymentDecision.sum()
        ).from(medicalBenefitsEntity)
            .where(
                medicalBenefitsEntity.hospitalId.eq(hospitalId),
                medicalBenefitsEntity.treatmentYearMonth.startsWith(yearMonth)
            )
            .fetchOne()
    }

}