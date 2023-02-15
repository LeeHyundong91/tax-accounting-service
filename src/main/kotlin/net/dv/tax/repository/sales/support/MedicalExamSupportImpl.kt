package net.dv.tax.repository.sales.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.MedicalExamEntity
import net.dv.tax.domain.sales.QMedicalExamEntity.medicalExamEntity
import net.dv.tax.dto.sales.MedicalExamListDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MedicalExamSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(MedicalExamEntity::class.java), MedicalExamSupport {
    override fun groupingList(hospitalId: String, year: String): List<MedicalExamListDto> {
        return query.select(
            Projections.bean(
                MedicalExamListDto::class.java,
                medicalExamEntity.receptionAmount.sum().`as`("receptionAmount"),
                medicalExamEntity.medicalBenefitsAmount.sum().`as`("medicalBenefitsAmount"),
                medicalExamEntity.benefitsCount.sum().`as`("benefitsCount"),
                medicalExamEntity.ownCharge.sum().`as`("ownCharge"),
                medicalExamEntity.disabledExpenses.sum().`as`("disabledExpenses"),
                medicalExamEntity.agencyExpenses.sum().`as`("agencyExpenses"),
                medicalExamEntity.cutOffAmount.sum().`as`("cutOffAmount"),
                medicalExamEntity.fundExpense.sum().`as`("fundExpense"),
                medicalExamEntity.proxyPayment.sum().`as`("proxyPayment"),
                medicalExamEntity.refundPaid.sum().`as`("refundPaid"),
                medicalExamEntity.agencyPayment.sum().`as`("agencyPayment"),
                medicalExamEntity.actualPayment.sum().`as`("actualPayment"),
                medicalExamEntity.dataPeriod.substring(0,7).`as`("dataPeriod")
            )
        )
            .from(medicalExamEntity)
            .where(
                medicalExamEntity.hospitalId.eq(hospitalId),
                medicalExamEntity.dataPeriod.startsWith(year)
            )
            .groupBy(medicalExamEntity.dataPeriod.substring(0,7))
            .fetch()
    }

}