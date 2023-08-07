package net.dv.tax.app.common


interface AccountingItemCommand {
    fun search(): List<AccountingItem>
}