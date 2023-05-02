package net.dv.tax.repository.sales

import net.dv.tax.domain.sales.SalesAgentEntity
import net.dv.tax.repository.sales.support.SalesAgentSupport
import org.springframework.data.jpa.repository.JpaRepository

interface SalesAgentRepository : JpaRepository<SalesAgentEntity, Long>, SalesAgentSupport {


}