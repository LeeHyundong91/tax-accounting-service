package net.dv.tax.repository

import net.dv.tax.domain.sales.MedicalExamEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface MedicalExamRepository: JpaRepository<MedicalExamEntity?, Int>,
    JpaSpecificationExecutor<MedicalExamEntity?> {
}