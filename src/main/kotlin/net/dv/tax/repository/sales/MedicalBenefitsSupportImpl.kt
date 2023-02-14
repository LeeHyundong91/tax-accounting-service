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
    override fun groupingList(hospitalId: String, dataPeriod: String): List<MedicalBenefitsListDto> {
        return query.select(
            Projections.fields(
                MedicalBenefitsListDto::class.java,
                medicalBenefitsEntity.actualPayment.sum().`as`("actualPayment"),
                medicalBenefitsEntity.amountReceived.sum().`as`("amountReceived"),
                medicalBenefitsEntity.corporationExpense.sum().`as`("corporationExpense"),
                medicalBenefitsEntity.dataPeriod,
                medicalBenefitsEntity.ownExpense.sum().`as`("ownExpense"),
            )
        )
            .from(medicalBenefitsEntity)
            .where(
                medicalBenefitsEntity.hospitalId.eq(hospitalId),
                medicalBenefitsEntity.dataPeriod.startsWith(dataPeriod)
            )
            .groupBy(medicalBenefitsEntity.dataPeriod)
            .fetch()
    }

}