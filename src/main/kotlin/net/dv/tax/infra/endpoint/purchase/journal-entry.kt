package net.dv.tax.infra.endpoint.purchase

import net.dv.access.Jwt
import net.dv.tax.Application
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/${Application.VERSION}/purchase/journal-entry")
class JournalEntryEndpoint(
    private val command: JournalEntryCommand,
) {

    @GetMapping("/types")
    fun types(): ResponseEntity<List<Any>> = ResponseEntity.ok(
        PurchaseType.values().map { mapOf("code" to it.code, "label" to it.label) })

    @GetMapping("")
    fun list(@Jwt("sub") accountId: String?): ResponseEntity<Any> {
        return ResponseEntity.ok("")
    }

    @GetMapping("/{type}/{id}")
    fun view(@PathVariable type: PurchaseType,
             @PathVariable id: Long): ResponseEntity<JournalEntry> {
        val res = command.get(PurchaseBookDto(id, type))
        return ResponseEntity.ok(res)
    }

    @GetMapping("/{type}/{id}/history")
    fun history(@PathVariable type: PurchaseType,
                @PathVariable id: Long): ResponseEntity<List<JournalEntryHistoryDto>> {
        val res = command.history(PurchaseBookDto(id, type))
        return ResponseEntity.ok(res)
    }

    @PostMapping("/{type}/{id}")
    fun requestExpense(@PathVariable type: PurchaseType,
                       @PathVariable id: Long,
                       @RequestBody data: JournalEntryReqDto,
                       @Jwt("sub") requester: String?): ResponseEntity<Any> {
        val res = command.request(PurchaseBookDto(id, type), data)
        return ResponseEntity.ok(res)
    }

    @PutMapping("/{type}/{id}")
    fun checkAndRequest(@PathVariable type: PurchaseType,
                        @PathVariable id: Long,
                        @RequestBody data: JournalEntryReqDto): ResponseEntity<Any> {
        val res = command.confirm(PurchaseBookDto(id, type), data)
        return ResponseEntity.ok(res)
    }
}