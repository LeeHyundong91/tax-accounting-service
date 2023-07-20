package net.dv.tax.app.common

import net.dv.tax.domain.common.AccountingDataEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AccountingDataRepository : JpaRepository<AccountingDataEntity?, Long>,
    JpaSpecificationExecutor<AccountingDataEntity?> {

    fun findAllByHospitalIdAndDataCategoryAndIsDeleteFalse(
        hospitalId: String,
        dataCategory: String,
    ): List<AccountingDataEntity>
}