package net.dv.tax.app.common

import net.dv.tax.app.AccountingItem


interface AccountingItemCommand {
    fun search(): List<AccountingItem>
}