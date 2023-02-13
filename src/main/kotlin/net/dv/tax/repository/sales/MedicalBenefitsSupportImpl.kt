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
    private val queryFactory: JPAQueryFactory,
) : CustomQuerydslRepositorySupport(MedicalBenefitsEntity::class.java), MedicalBenefitsSupport {
    override fun groupingList(hospitalId: String, dataPeriod: String): List<MedicalBenefitsListDto> {
        return queryFactory.select(
            Projections.bean(
                MedicalBenefitsListDto::class.java,
                medicalBenefitsEntity.medicalBenefitsId,
                medicalBenefitsEntity.actualPayment,
                medicalBenefitsEntity.amountReceived,
                medicalBenefitsEntity.corporationExpense,
                medicalBenefitsEntity.dataPeriod,
                medicalBenefitsEntity.ownExpense,
                medicalBenefitsEntity.ownExpense
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