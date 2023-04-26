package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
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
    override fun dataList(hospitalId: String, treatmentYearMonth: String): List<MedicalBenefitsDto> {
        /*`as`*/
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
                medicalBenefitsEntity.treatmentYearMonth.startsWith(treatmentYearMonth)
            )
            .groupBy(medicalBenefitsEntity.treatmentYearMonth)
            .fetch()
    }

    override fun dataListTotal(hospitalId: String, treatmentYearMonth: String): MedicalBenefitsDto {
        return query.select(
            Projections.bean(
                MedicalBenefitsDto::class.java,
                medicalBenefitsEntity.treatmentYearMonth.count().`as`("totalCount"),
                medicalBenefitsEntity.treatmentYearMonth.substring(0, 4).`as`("treatmentYearMonth"),
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
                medicalBenefitsEntity.treatmentYearMonth.startsWith(treatmentYearMonth)
            )
            .fetchOne()!!
    }

}