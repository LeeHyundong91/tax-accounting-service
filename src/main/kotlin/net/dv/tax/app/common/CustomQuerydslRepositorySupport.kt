package net.dv.tax.app.common

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
abstract class CustomQuerydslRepositorySupport (domainClass: Class<*>?) : QuerydslRepositorySupport(domainClass!!) {
    @PersistenceContext
    override fun setEntityManager(entityManager: EntityManager) {
        super.setEntityManager(entityManager)
    }
}