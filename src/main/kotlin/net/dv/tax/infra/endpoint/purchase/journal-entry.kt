package net.dv.tax.infra.endpoint.purchase

import net.dv.access.Jwt
import net.dv.tax.Application
import net.dv.tax.app.AbstractSearchQueryDto
import net.dv.tax.app.Period
import net.dv.tax.app.enums.purchase.PurchaseType
import net.dv.tax.app.purchase.*
import org.springframework.data.domain.Page
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

    @GetMapping("/{hospitalId}")
    fun list(@Jwt("sub") accountId: String?,
             @PathVariable hospitalId: String,
             query: QueryRequest): ResponseEntity<Page<JournalEntry>> {
        val res = command.expenseByHospital(hospitalId, query, query.pageable)
        return ResponseEntity.ok(res)
    }

    @GetMapping("/{type}/{id}")
    fun view(@PathVariable type: String,
             @PathVariable id: Long): ResponseEntity<JournalEntry> {
        val res = command.get(PurchaseBookDto(id, PurchaseType[type]))
        return ResponseEntity.ok(res)
    }

    @GetMapping("/{type}/{id}/history")
    fun history(@PathVariable type: String,
                @PathVariable id: Long): ResponseEntity<PurchaseBookOverview> {
        val res = command.history(PurchaseBookDto(id, PurchaseType[type]))
        return ResponseEntity.ok(res)
    }

    @PostMapping("/{type}/{id}")
    fun requestExpense(@PathVariable type: String,
                       @PathVariable id: Long,
                       @RequestBody data: JournalEntryReqDto,
                       @Jwt("sub") requester: String?): ResponseEntity<Any> {
        val res = command.request(PurchaseBookDto(id, PurchaseType[type]), data)
        return ResponseEntity.ok(res)
    }

    @PutMapping("/{type}/{id}")
    fun checkAndRequest(@PathVariable type: String,
                        @PathVariable id: Long,
                        @RequestBody data: JournalEntryReqDto): ResponseEntity<Any> {
        val res = command.confirm(PurchaseBookDto(id, PurchaseType[type]), data)
        return ResponseEntity.ok(res)
    }

    @GetMapping("/{hospitalId}/{type}/state")
    fun processingState(@PathVariable hospitalId: String,
                        @PathVariable type: String,
                        query: QueryRequest): ResponseEntity<Page<out JournalEntryStatus>> {
        val res = command.processingState(PurchaseType[type], hospitalId, query.pageable)

        return ResponseEntity.ok(res)
    }

    data class QueryRequest(
        override var category: String,
        override var period: Period,
        override var type: PurchaseType,
        var term: String,
    ): JournalEntryCommand.Query, AbstractSearchQueryDto()
}
