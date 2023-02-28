package net.dv.tax.repository.common

import net.dv.tax.domain.common.AccountingDataEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AccountingDataRepository : JpaRepository<AccountingDataEntity?, Int>,
    JpaSpecificationExecutor<AccountingDataEntity?> {

    fun findAllByHospitalIdAndAndDataCategory(
        hospitalId: String,
        dataCategory: String,
    ): List<AccountingDataEntity>
}