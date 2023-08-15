package net.dv.tax.app.common

import net.dv.tax.app.AccountingItem
import org.springframework.stereotype.Service


@Service
class GenericAccountingService(private val accountingItemRepository: AccountingItemRepository): AccountingItemCommand {
    override fun search(): List<AccountingItem> {
        val entities = accountingItemRepository.findAll()
        return entities.map {
            AccountingItem(it.code, it.name)
        }
    }

}