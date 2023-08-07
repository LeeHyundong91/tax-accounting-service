package net.dv.tax.infra.endpoint

import net.dv.tax.Application
import net.dv.tax.app.common.AccountingItem
import net.dv.tax.app.common.AccountingItemCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/${Application.VERSION}")
class AccountingItemsEndpoint(private val accountingItemCommand: AccountingItemCommand) {
    @GetMapping("/accounting-items")
    fun accountItems(): ResponseEntity<List<AccountingItem>>{
        val res = accountingItemCommand.search()
        return ResponseEntity.ok(res)
    }
}
