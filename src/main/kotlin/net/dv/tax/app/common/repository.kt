package net.dv.tax.app.common

import net.dv.tax.domain.view.VAccount
import net.dv.tax.domain.common.AccountingDataEntity
import net.dv.tax.domain.common.AccountingItemEntity
import net.dv.tax.domain.view.VHospital
import net.dv.tax.domain.view.VHospitalMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AccountingDataRepository : JpaRepository<AccountingDataEntity?, Long>,
    JpaSpecificationExecutor<AccountingDataEntity?> {

    fun findAllByHospitalIdAndDataCategoryAndIsDeleteFalse(
        hospitalId: String,
        dataCategory: String,
    ): List<AccountingDataEntity>
}

interface AccountingItemRepository: JpaRepository<AccountingItemEntity, String>


interface VHospitalMemberRepository: JpaRepository<VHospitalMember, String> {

    fun findByAccountId(accountId: String): VHospitalMember

    fun findByAccountIdAndRole(id: String, role: String): VHospitalMember

}


interface VHospitalRepository: JpaRepository<VHospital, String> {
}

interface VAccountRepository: JpaRepository<VAccount, Int> {
    fun findOneByAccountId(accountId: String): VAccount?

}
