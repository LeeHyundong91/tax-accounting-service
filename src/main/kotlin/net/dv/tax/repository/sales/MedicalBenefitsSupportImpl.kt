package net.dv.tax.repository.sales

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import net.dv.tax.domain.sales.MedicalBenefitsEntity
import net.dv.tax.domain.sales.QMedicalBenefitsEntity.medicalBenefitsEntity
import net.dv.tax.dto.sales.MedicalBenefitsListDto
import net.dv.tax.service.common.CustomQuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MedicalBenefitsSupportImpl(
    private val query: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(MedicalBenefitsEntity::class.java), MedicalBenefitsSupport {
    override fun groupingList(hospitalId: String, year: String): List<MedicalBenefitsListDto> {
        return query.select(
            Projections.bean(
                MedicalBenefitsListDto::class.java,
                medicalBenefitsEntity.actualPayment.sum().`as`("actualPayment"),
                medicalBenefitsEntity.amountReceived.sum().`as`("amountReceived"),
                medicalBenefitsEntity.corporationExpense.sum().`as`("corporationExpense"),
                medicalBenefitsEntity.dataPeriod,
                medicalBenefitsEntity.ownExpense.sum().`as`("ownExpense"),
                medicalBenefitsEntity.ownExpense.add(medicalBenefitsEntity.corporationExpense).sum()
                    .`as`("totalAmount")
            )
        )
            .from(medicalBenefitsEntity)
            .where(
                medicalBenefitsEntity.hospitalId.eq(hospitalId),
                medicalBenefitsEntity.dataPeriod.startsWith(year)
            )
            .groupBy(medicalBenefitsEntity.dataPeriod)
            .fetch()
    }

}