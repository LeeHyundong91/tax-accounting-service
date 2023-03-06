package net.dv.tax.repository.purchase

import net.dv.tax.domain.purchase.PurchaseCreditCardEntity
import net.dv.tax.repository.purchase.suppoort.PurchaseCreditCardSupport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PurchaseCreditCardRepository: JpaRepository<PurchaseCreditCardEntity?, Int>,
    JpaSpecificationExecutor<PurchaseCreditCardEntity?>, PurchaseCreditCardSupport {
}