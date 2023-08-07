package net.dv.tax.infra.endpoint.purchase

import net.dv.tax.Application
import net.dv.tax.app.enums.purchase.PurchaseType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/${Application.VERSION}/purchase/journal-entry")
class ConfirmationEndpoint {

    @GetMapping("/types")
    fun types(): ResponseEntity<List<Any>> = ResponseEntity.ok(
        PurchaseType.values().map { mapOf("code" to it.code, "label" to it.label) })

    @GetMapping("/")
    fun list(): ResponseEntity<Any> {
        return ResponseEntity.ok("")
    }

    @GetMapping("/{type}/{id}")
    fun view(@PathVariable type: String,
             @PathVariable id: String): ResponseEntity<Any> {
        return ResponseEntity.ok("")
    }

    @PostMapping("/{type}/{id}")
    fun requestExpense(@PathVariable type: String,
                       @PathVariable id: String): ResponseEntity<Any> {
        return ResponseEntity.ok("")
    }

    @PutMapping("/{type}/{id}")
    fun checkAndRequest(@PathVariable type: String,
                        @PathVariable id: String): ResponseEntity<Any> {
        return ResponseEntity.ok("")
    }
}