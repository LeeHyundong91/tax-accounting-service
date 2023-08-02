package net.dv.tax.infra.endpoint.purchase

import net.dv.tax.Application
import net.dv.tax.app.enums.purchase.PurchaseType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/${Application.VERSION}/purchase/confirmations")
class ConfirmationEndpoint {

    @GetMapping("/types")
    fun types(): ResponseEntity<List<Any>> = ResponseEntity.ok(
        PurchaseType.values().map { mapOf("code" to it.code, "label" to it.label) })

    @GetMapping("/")
    fun list(): ResponseEntity<Any> {

        return ResponseEntity.ok("")
    }

    @GetMapping("/{id}")
    fun view(@PathVariable id: String): ResponseEntity<Any> {
        return ResponseEntity.ok("")
    }

    @PutMapping("/{id}")
    fun checkAndRequest(@PathVariable id: String): ResponseEntity<Any> {
        return ResponseEntity.ok("")
    }
}