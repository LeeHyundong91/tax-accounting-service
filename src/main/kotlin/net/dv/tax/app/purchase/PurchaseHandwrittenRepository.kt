package net.dv.tax.app.purchase

import net.dv.tax.domain.purchase.PurchaseHandwrittenEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PurchaseHandwrittenRepository : JpaRepository<PurchaseHandwrittenEntity, Long>,
    JpaSpecificationExecutor<PurchaseHandwrittenEntity> {

    fun findAllByHospitalIdAndIssueDateStartingWith(
        hospitalId: String,
        issueDate: String,
        pageable: Pageable,
    ): List<PurchaseHandwrittenEntity>?


}